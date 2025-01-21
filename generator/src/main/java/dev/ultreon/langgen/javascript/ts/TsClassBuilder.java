package dev.ultreon.langgen.javascript.ts;

import dev.ultreon.langgen.Main;
import dev.ultreon.langgen.api.ClassCompat;
import dev.ultreon.langgen.api.Converters;
import dev.ultreon.langgen.api.PackageExclusions;
import dev.ultreon.langgen.javascript.api.AnyJsClassBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TsClassBuilder extends AnyJsClassBuilder {
    protected static final Queue<Class<?>> queue = new ArrayDeque<>();

    protected final Class<?> clazz;
    protected final LinkedHashSet<String> imports = new LinkedHashSet<>();

    private final boolean isInterface;
    protected String name;
    @Nullable
    private Type forcedSuperclass = null;
    private boolean writingInterfaceMethod;

    public TsClassBuilder(Class<?> clazz) {
        this.clazz = clazz;

        String className = clazz.getName();
        String[] classNameSplit = className.split("\\.");
        name = classNameSplit[classNameSplit.length - 1];

        isInterface = clazz.isInterface() && !ClassCompat.isForcedAbstract(clazz);
    }

    public enum VisibilityLevel {
        PRIVATE("private"),
        PACKAGE(""),
        PROTECTED("protected"),
        PUBLIC("public");

        private final String displayName;

        VisibilityLevel(String displayName) {
            this.displayName = displayName;
        }

        public static VisibilityLevel of(Member member) {
            int modifiers = member.getModifiers();
            if (Modifier.isPublic(modifiers)) return PUBLIC;
            else if (Modifier.isProtected(modifiers)) return PROTECTED;
            else if (Modifier.isPrivate(modifiers)) return PRIVATE;
            else return PACKAGE;
        }

        public String displayName() {
            return displayName;
        }

        public boolean isAtLeast(VisibilityLevel visibilityLevel) {
            return ordinal() >= visibilityLevel.ordinal();
        }

        public boolean isHigherThan(VisibilityLevel level) {
            return ordinal() > level.ordinal();
        }
    }

    @Override
    public @Nullable List<String> build(StringBuilder result, Path output) {
        if (this.isInterface && this.clazz.isAnnotationPresent(FunctionalInterface.class) && isFunctionalInterface(this.clazz)) {
            result.append(writeFunctionalInterfaceType(this.clazz));
            result.insert(0, String.join("\n", imports) + "\n\n");
            result.append("export default ").append(name).append(";");

            return List.of();
        }

        // Handle interfaces
        AtomicReference<Type> forcedSuperclass = new AtomicReference<>(null);
        Type[] interfaces = Arrays.stream(clazz.getGenericInterfaces()).filter(type -> {
            Class<?> classFromType = getClassFromType(type);
            if (classFromType != null) {
                if (ClassCompat.isForcedAbstract(classFromType)) {
                    if (forcedSuperclass.get() != null) {
                        Main.getLogger().error("Superclass cannot be forced while already having a forced superclass! (forced %s and %s in %s)"
                            .formatted(type, forcedSuperclass.get(), clazz));
                        throw new Error("Superclass cannot be forced while already having a forced superclass!");
                    }
                    forcedSuperclass.set(type);
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        }).toArray(Type[]::new);

        this.forcedSuperclass = forcedSuperclass.get();
        if (this.forcedSuperclass != null) {
            if (this.forcedSuperclass.equals(Object.class)) {
                this.forcedSuperclass = null;
            } else {
                this.forcedSuperclass = getClassFromType(this.forcedSuperclass);
                if (this.forcedSuperclass == null)
                    throw new Error("Forced superclass isn't defined as class! (forced %s in %s)".formatted(this.forcedSuperclass, clazz));
                if (this.forcedSuperclass == Object.class)
                    throw new Error("Forced superclass is Object! (forced %s in %s)".formatted(this.forcedSuperclass, clazz));
                if (this.forcedSuperclass == clazz)
                    throw new Error("Forced superclass is same class! (forced %s in %s)".formatted(this.forcedSuperclass, clazz));
            }

            Main.getLogger().warn("Forced superclass: " + this.forcedSuperclass + " in " + clazz);
        }

        if (interfaces.length > 0) {
            result.append("type $");

            writeTypeParams(result);

            result.append(" = ");
            for (int i = 0; i < interfaces.length; i++) {
                Type anInterface = interfaces[i];
                result.append(typeToString(anInterface, null));

                if (i < interfaces.length - 1) {
                    result.append(" & ");
                }
            }
            result.append(";\n");
        }

        result.append("export default ");

        if (Modifier.isAbstract(clazz.getModifiers()) && !isInterface) result.append("abstract ");

        result.append(isInterface ? "interface " : "class ").append(name).append(" ");

        writeTypeParams(result);

        // Handle inheritance
        Type superclass = clazz.getGenericSuperclass();
        Class<?> classFromType = getClassFromType(superclass);
        if (classFromType == null || PackageExclusions.isExcluded(classFromType)) {
            superclass = null;
        }
        if (superclass != null && !superclass.equals(Object.class)) {
            if (this.forcedSuperclass != null)
                throw new Error("Superclass cannot be forced while already having a generic superclass! (forced %s in %s)"
                        .formatted(this.forcedSuperclass, clazz));

            String str = typeToString(superclass, null);
            if (!str.equals("any")) {
                result.append(" extends ").append(str);
            }
        } else if (this.forcedSuperclass != null) {
            Main.getLogger().warn("Forcing superclass: " + this.forcedSuperclass + " in " + clazz);
            String str = typeToString(this.forcedSuperclass, null);
            if (!str.equals("any")) {
                result.append(" extends ").append(str);
            }
        }

        // Handle interfaces
        if (interfaces.length > 0) {
            result.append(isInterface ? " extends " : " implements ")
                    .append("$");

            writeTypeParams(result, true);
        }

        result.append(" {\n");

        StringBuilder contentBuilder = new StringBuilder();
        writeMembers(contentBuilder);
        String content = contentBuilder.toString().indent(4);

        result.append(content);
        result.append("}\n");

        writeInterfaceConstants(result);

        writeInterfaceStatics(result);

        result.insert(0, String.join("\n", imports) + "\n\n");

        return List.of();
    }

    @Nullable
    private Class<?> getClassFromType(Type type) {
        if (type == null) return null;
        if (type instanceof ParameterizedType parameterizedType) {
            return (Class<?>) parameterizedType.getRawType();
        } else if (type instanceof Class<?> aClass) {
            return aClass;
        }
        return null;
    }

    private void writeTypeParams(StringBuilder result) {
        writeTypeParams(result, false);
    }

    private void writeTypeParams(StringBuilder result, boolean ignoreBounds) {
        TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
        writeTypeParams(result, ignoreBounds, typeParameters);
    }

    private void writeTypeParams(StringBuilder result, boolean ignoreBounds, TypeVariable<?>[] typeParameters) {
        if (typeParameters.length > 0) {
            result.append("<");
            for (int i = 0; i < typeParameters.length; i++) {
                TypeVariable<?> typeVariable = typeParameters[i];
                result.append(typeVariable.getName());

                // Handle bounds (extends)
                if (!ignoreBounds) {
                    Type[] bounds = typeVariable.getBounds();
                    if (bounds.length > 0 && !bounds[0].equals(Object.class)) {
                        result.append(" extends ");
                        for (int j = 0; j < bounds.length; j++) {
                            Type bound = bounds[j];
                            result.append(typeToString(bound, null));
                            if (j < bounds.length - 1) {
                                result.append(" & ");
                            }
                        }
                    }
                }

                if (i < typeParameters.length - 1) {
                    result.append(", ");
                }
            }
            result.append(">");
        }
    }

    private boolean isFunctionalInterface(Class<?> clazz) {
//        Method[] methods = clazz.getMethods();
//        long abstractMethodCount = Arrays.stream(methods)
//                .filter(method -> Modifier.isAbstract(method.getModifiers()))
//                .count();
//        return abstractMethodCount == 1;
        return false;
    }

    private String writeFunctionalInterfaceType(Class<?> clazz) {
        Method method = Arrays.stream(clazz.getMethods())
                .filter(m -> Modifier.isAbstract(m.getModifiers()))
                .findFirst()
                .orElseThrow(() -> new Error("No abstract method found in functional interface"));

        StringBuilder result = new StringBuilder();
        result.append("type ").append(name);
        writeTypeParams(result);
        result.append(" = (");
        writeTsFuncParams(method, result);
        result.append(") => ").append(typeToString(method.getGenericReturnType(), method)).append(";\n\n");

        return result.toString();
    }

    private void writeInterfaceConstants(StringBuilder result) {
        if (!isInterface) return;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                result.append("export const ").append(field.getName()).append(": ").append(typeToString(field.getGenericType(), null)).append(" = ").append("undefined as any").append(";\n");
            }
        }
    }

    private void writeInterfaceStatics(StringBuilder result) {
        if (!isInterface) return;
        Set<Method> allMethods = new LinkedHashSet<>();
        getAllMethods(clazz, allMethods);

        LinkedHashMap<String, List<Method>> staticMethodByName = new LinkedHashMap<>();
        for (Method curMethod : allMethods) {
            if (Modifier.isStatic(curMethod.getModifiers()) && !curMethod.isSynthetic()) {
                staticMethodByName.computeIfAbsent(curMethod.getName(), key -> new ArrayList<>()).add(curMethod);
            }
        }

        this.writingInterfaceMethod = true;
        for (var entry : staticMethodByName.entrySet()) {
            String name = entry.getKey();
            List<Method> methods = entry.getValue();

            VisibilityLevel level = VisibilityLevel.PRIVATE;
            for (Method method : methods) {
                String str = writeTsFunction(name, method);
                if (str == null) continue;
                VisibilityLevel newLevel = VisibilityLevel.of(method);
                if (newLevel.isHigherThan(level)) level = newLevel;
                if (!str.contains("*/ public static ")) {
                    result.append("function ").append(str).append("\n");
                } else {
                    result.append(str.replace("*/ public static ", "*/ function ")).append('\n');
                }
            }

            if (level.isAtLeast(VisibilityLevel.PROTECTED)) {
                result.append(writeTsImpl(name, level, true).replace("*/ public static ", "*/ function ")).append('\n');
            }
        }

        this.writingInterfaceMethod = false;
    }

    private void writeFields(StringBuilder result) {
        Set<Method> allMethods = new HashSet<>();
        getAllMethods(clazz, allMethods);

        Set<String> methodNames = new HashSet<>();
        for (Method curMethod : allMethods) {
            if (!curMethod.isSynthetic()) {
                if (!curMethod.getName().matches("[a-zA-Z0-9$_]+")) continue;
                methodNames.add(curMethod.getName());
            }
        }

        // Add fields as properties
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean shouldIgnore = isInterface || methodNames.contains(field.getName());
            if (!field.getName().matches("[a-zA-Z0-9$_]+")) continue;

            if (shouldIgnore) result.append("/* ");

            if (writeModifiers(field, result, false, shouldIgnore)) {
                if (shouldIgnore) result.append("*/\n");
                continue;
            }

            if (!Modifier.isFinal(field.getModifiers())) {
                result.append(field.getName()).append(": ").append(typeToString(field.getGenericType(), null)).append(";");
            } else {
                result.append("readonly ").append(" ").append(field.getName()).append(": ").append(typeToString(field.getGenericType(), null)).append(";");
            }

            if (shouldIgnore) result.append(" */");
            result.append('\n');
        }
    }

    private void writeMembers(StringBuilder result) {
        writeFields(result);

        if (clazz.getDeclaredFields().length > 0) {
            result.append('\n');
        }

        if (!isInterface) {
            VisibilityLevel level = VisibilityLevel.PRIVATE;

            Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
            for (Constructor<?> constructor : declaredConstructors) {
                String str = this.writeTsConstructor(constructor);
                VisibilityLevel newLevel = VisibilityLevel.of(constructor);
                if (newLevel.isHigherThan(level)) level = newLevel;
                result.append(str).append('\n');
            }

            if (declaredConstructors.length == 0) {
                result.append("/*").append(level.displayName).append("*/").append(" public constructor();\n");
            }

            Class<?> superclass = clazz.getSuperclass();
            if (PackageExclusions.isExcluded(superclass)) superclass = null;
            if ((superclass != null && superclass != Object.class) || forcedSuperclass != null) {
                if (forcedSuperclass != null) {
                    Main.getLogger().warn("Forcing super call to %s! (forced %s in %s)".formatted(clazz, forcedSuperclass, clazz));
                    Class<?> classFromType = getClassFromType(forcedSuperclass);
                    if (classFromType == null)
                        Main.getLogger().error("Forced superclass isn't defined as class! (forced %s in %s)".formatted(forcedSuperclass, clazz));
                    if (classFromType == Object.class)
                        Main.getLogger().error("Forced superclass is Object! (forced %s in %s)".formatted(forcedSuperclass, clazz));
                    superclass = classFromType;
                }

                Constructor<?>[] constructors = superclass != null ? superclass.getDeclaredConstructors() : new Constructor[0];
                if (constructors.length == 0) {
                    Main.getLogger().warn("Superclass %s has no constructor! (in %s)".formatted(superclass, clazz));
                    result.append("/*").append(level.displayName).append("*/").append(" public constructor(...args: any[]) { super() };\n\n");
                } else {
                    String params = "undefined as any, ".repeat(constructors[0].getParameterCount());
                    if (!params.isEmpty()) {
                        params = params.substring(0, params.length() - 2);
                    }
                    result.append("/*").append(level.displayName).append("*/").append(" public constructor(...args: any[]) { super(%s) };\n\n".formatted(
                            params
                    ));
                }
            } else
                result.append("/*").append(level.displayName).append("*/").append(" public constructor(...args: any[]) { };\n\n");
        }

        Set<Method> allMethods = new LinkedHashSet<>();
        getAllMethods(clazz, allMethods);

        LinkedHashMap<String, List<Method>> staticMethodByName = new LinkedHashMap<>();
        for (Method curMethod : allMethods) {
            if (Modifier.isStatic(curMethod.getModifiers()) && !curMethod.isSynthetic()) {
                staticMethodByName.computeIfAbsent(curMethod.getName(), key -> new ArrayList<>()).add(curMethod);
            }
        }
        for (var entry : staticMethodByName.entrySet()) {
            String name = entry.getKey();
            List<Method> methods = entry.getValue();

            VisibilityLevel level = VisibilityLevel.PRIVATE;
            for (Method method : methods) {
                if (!method.getName().matches("[a-zA-Z0-9$_]+")) continue;
                String str = writeTsFunction(name, method);
                if (str == null) continue;
                VisibilityLevel newLevel = VisibilityLevel.of(method);
                if (newLevel.isHigherThan(level)) level = newLevel;
                result.append(str).append('\n');
            }

            if (level.isAtLeast(VisibilityLevel.PROTECTED)) {
                result.append(writeTsImpl(name, level, true)).append('\n');
            }
        }

        LinkedHashMap<String, List<Method>> methodByName = new LinkedHashMap<>();
        for (Method curMethod : allMethods) {
            if (!Modifier.isStatic(curMethod.getModifiers()) && !curMethod.isSynthetic()) {
                methodByName.computeIfAbsent(curMethod.getName(), k -> new ArrayList<>()).add(curMethod);
            }
        }
        for (var entry : methodByName.entrySet()) {
            String name = entry.getKey();
            List<Method> methods = entry.getValue();

            VisibilityLevel level = VisibilityLevel.PRIVATE;
            for (Method method : methods) {
                if (!method.getName().matches("[a-zA-Z0-9$_]+")) continue;
                String str = writeTsFunction(name, method);
                if (str == null) continue;
                VisibilityLevel newLevel = VisibilityLevel.of(method);
                if (newLevel.isHigherThan(level)) level = newLevel;
                result.append(str).append('\n');
            }

            if (level.isAtLeast(VisibilityLevel.PROTECTED) && !isInterface) {
                result.append(writeTsImpl(name, level, false)).append('\n');
            }
        }
    }

    private String typeToString(Type type, @Nullable Executable exec) {
        if (Objects.requireNonNull(type) instanceof Class<?> cls) {
            if (PackageExclusions.isExcluded(cls)) return "any";

            Class<?> componentType = cls;
            String suffix = "";
            if (cls.isArray()) {
                componentType = cls.getComponentType();
                suffix += "[]";
                if (componentType.isArray()) {
                    componentType = componentType.getComponentType();
                    suffix += "[]";
                    if (componentType.isArray()) {
                        componentType = componentType.getComponentType();
                        suffix += "[]";
                        if (componentType.isArray()) {
                            return "any";
                        }
                    }
                }
            }

            TypeVariable<? extends Class<?>>[] genericParams = componentType.getTypeParameters();
            if (genericParams.length > 0) {
                StringBuilder sb = new StringBuilder(toTsType(componentType).replace(".", "$"));
                sb.append("<");
                for (TypeVariable<? extends Class<?>> ignored : genericParams) {
                    sb.append("any, ");
                }
                sb.delete(sb.length() - 2, sb.length());
                sb.append(">");
                sb.append(suffix);
                return sb.toString();
            }

            return toTsType(cls) + suffix;
        } else if (type instanceof TypeVariable<?> typeVariable) {
            for (TypeVariable<?> clsTypeParam : clazz.getTypeParameters()) {
                if (typeVariable.getName().equals(clsTypeParam.getName())) {
                    return clsTypeParam.getName();
                }
            }
            if (exec != null) {
                for (TypeVariable<?> execTypeParam : exec.getTypeParameters()) {
                    if (typeVariable.getName().equals(execTypeParam.getName())) {
                        return execTypeParam.getName();
                    }
                }
                return "any";
            }

            return "any";
        } else if (type instanceof ParameterizedType parameterizedType) {
            StringBuilder result = new StringBuilder();
            Type genericType = parameterizedType.getRawType();
            if (genericType == null) return "any";

            String typeString = genericType instanceof Class<?> cls ? toTsType(cls) : typeToString(genericType, exec);
            if (Objects.equals(typeString, "any")) return "any";
            if (genericType instanceof Class<?> cls && !Modifier.isPublic(cls.getModifiers()) && !Modifier.isProtected(cls.getModifiers()))
                return "any";
            if (genericType == Object.class) return "any";
            if (genericType.getTypeName().equals("?")) return "any";
            if (genericType instanceof Class<?> cls && PackageExclusions.isExcluded(cls)) return "any";

            result.append(typeString);
            Type[] genericArgs = parameterizedType.getActualTypeArguments();
            if (genericArgs.length > 0) {
                result.append("<");
                for (int i = 0; i < genericArgs.length; i++) {
                    result.append(typeToString(genericArgs[i], exec));
                    if (i < genericArgs.length - 1) {
                        result.append(", ");
                    }
                }
                result.append(">");
            }
            return result.toString();
        } else if (type instanceof WildcardType) {
            return "any";
        } else if (type instanceof GenericArrayType genericArrayType) {
            return typeToString(genericArrayType.getGenericComponentType(), exec) + "[]";
        }
        Main.getLogger().warn("Unknown type: " + type.getClass().getName());
        return type.toString().replace("?", "any");
    }

    private <T> void getAllMethods(@NotNull Class<T> curClass, Set<Method> output) {
        Set<Method> set = new LinkedHashSet<>(Arrays.asList(curClass.getDeclaredMethods()));
        if (curClass.getSuperclass() != null) {
            getAllMethods(curClass.getSuperclass(), output);
        }

        for (Class<?> anInterface : curClass.getInterfaces()) {
            getAllMethods(anInterface, output);
        }

        output.addAll(set);
    }

    public void addImport(@Nullable String code) {
        if (code == null) return;

        this.imports.add(code);
    }

    public @Nullable String toTsPrimitiveType(Class<?> type) {
        if (type == Boolean.class) return "boolean";
        else if (type == Character.class) return "string";
        else if (!type.isPrimitive() && Number.class.isAssignableFrom(type)) return "number";

        if (type == int.class) return "number";
        else if (type == long.class) return "number";
        else if (type == float.class) return "number";
        else if (type == double.class) return "number";
        else if (type == boolean.class) return "boolean";
        else if (type == String.class) return "string";
        else if (type == byte[].class) return "Uint8Array";
        else if (type == Object.class) return "any";
        else if (type == byte.class) return "number";
        else if (type == short.class) return "number";
        else if (type == char.class) return "string";
        else if (type == void.class) return "void";
        else if (type == int[].class) return "number[]";
        else if (type == long[].class) return "number[]";
        else if (type == float[].class) return "number[]";
        else if (type == double[].class) return "number[]";
        else if (type == boolean[].class) return "boolean[]";
        else if (type == String[].class) return "string[]";
        else if (type == byte[][].class) return "Uint8Array[]";
        else if (type == Object[].class) return "any[]";
        else if (type == short[].class) return "number[]";
        else if (type == char[].class) return "string[]";
        else return null;
    }

    public String writeTsImpl(String methodName, VisibilityLevel level, boolean isStatic) {
        if (isStatic) {
            if (isInterface && !writingInterfaceMethod) {
                return "/* %s static %s(...args: any[]): any { return undefined as any; } */\n".formatted(level.displayName(), methodName);
            }
            return "/*%s*/ public static %s(...args: any[]): any { return undefined as any; }\n".formatted(level.displayName(), methodName);
        }
        return "/*%s*/ public %s(...args: any[]): any { return undefined as any; }\n".formatted(level.displayName(), methodName);
    }

    @Nullable
    public String writeTsFunction(String methodName, Method method) {
        StringBuilder result = new StringBuilder();

        if (Modifier.isStatic(method.getModifiers()) && isInterface && !writingInterfaceMethod) {
            result.append("/* ");
        }

        if (writeModifiers(method, result)) return null;

        result.append(methodName);

        // Get the type parameters of the method
        TypeVariable<?>[] typeParameters = method.getTypeParameters();
        if (typeParameters.length > 0) {
            result.append("<");
            for (int i = 0; i < typeParameters.length; i++) {
                TypeVariable<?> typeVariable = typeParameters[i];
                result.append(typeVariable.getName());

                // Handle bounds (extends)
                Type[] bounds = typeVariable.getBounds();
                if (bounds.length > 0 && !bounds[0].equals(Object.class)) {
                    result.append(" extends ");
                    for (int j = 0; j < bounds.length; j++) {
                        Type bound = bounds[j];
                        result.append(typeToString(bound, method));
                        if (j < bounds.length - 1) {
                            result.append(" & ");
                        }
                    }
                }

                if (i < typeParameters.length - 1) {
                    result.append(", ");
                }
            }
            result.append(">");
        }

        // Add function name and parameters
        result.append("(");
        writeTsFuncParams(method, result);
        result.append("): ").append(typeToString(method.getGenericReturnType(), method)).append(";");

        if (Modifier.isStatic(method.getModifiers()) && isInterface && !writingInterfaceMethod) {
            result.append(" */");
        }

        return result.toString();
    }

    private boolean writeModifiers(Member member, StringBuilder result) {
        return writeModifiers(member, result, false);
    }

    private boolean writeModifiers(Member member, StringBuilder result, boolean addAnyways) {
        return writeModifiers(member, result, addAnyways, false);
    }

    private boolean writeModifiers(Member member, StringBuilder result, boolean addAnyways, boolean dontComment) {
        int modifiers = member.getModifiers();
        if (isInterface && !Modifier.isStatic(modifiers)) return false;

        if (Modifier.isPublic(modifiers)) {
            if (member instanceof Field) result.append("declare ");

            if (!isInterface) result.append("public ");

            if (Modifier.isStatic(modifiers) && !isInterface) {
                result.append("static ");
            }
        } else if (Modifier.isProtected(modifiers)) {
            if (member instanceof Field) result.append("declare ");

            if (dontComment) {
                result.append("protected ");
            } else {
                result.append("/*protected*/ public ");
            }

            if (Modifier.isStatic(modifiers) && !isInterface) {
                result.append("static ");
            }
        } else if (Modifier.isPrivate(modifiers) && addAnyways) {
            if (dontComment) {
                result.append("private public ");
            } else {
                result.append("/*private*/ public ");
            }

            if (Modifier.isStatic(modifiers) && !isInterface) {
                result.append("static ");
            }
            return true;
        } else if (addAnyways) {
            if (dontComment) {
                result.append("package public ");
            } else {
                result.append("/*package*/ public ");
            }

            if (Modifier.isStatic(modifiers) && !isInterface) {
                result.append("static ");
            }
            return true;
        } else {
            return true;
        }

        return false;
    }

    public String writeTsConstructor(Constructor<?> constructor) {
        StringBuilder result = new StringBuilder();
        writeModifiers(constructor, result, true);

        // Get the type parameters of the method
        TypeVariable<?>[] typeParameters = constructor.getTypeParameters();
        if (typeParameters.length > 0) {
            result.append("  ");
            result.append("constructor");
        } else {
            result.append("constructor");
        }

        // Add function name and parameters
        result.append("(");

        Parameter[] parameters = constructor.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Type parameterType = parameter.getParameterizedType();

            // Convert the type to string
            result.append("arg").append(i).append(": ").append(typeToString(parameterType, null));

            if (i < parameters.length - 1) {
                result.append(", ");
            }
        }

        result.append(")").append(";");

        return result.toString();
    }

    private void writeTsFuncParams(Executable executable, StringBuilder result) {
        Parameter[] parameters = executable.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Type parameterType = parameter.getParameterizedType();

            // Convert the type to string
            result.append("arg").append(i).append(": ").append(typeToString(parameterType, executable));

            if (i < parameters.length - 1) {
                result.append(", ");
            }
        }
    }

    public @NotNull String toTsType(@Nullable Class<?> returnType) {
        if (returnType == null) return "any";
        if (returnType == Object.class) return "any";
        if (returnType.getName().equals("?")) return "any";
        if (PackageExclusions.isExcluded(returnType)) return "any";

        if (returnType == clazz) return name;

        String primitiveType = toTsPrimitiveType(returnType);
        if (primitiveType != null) return primitiveType;

        if (returnType.isArray()) return toTsType(returnType.getComponentType()) + "[]";

        String name = Converters.convert(returnType.getName());
        if (name == null) name = returnType.getName().replace(".", "$");

        addImport(toTsImport(returnType));

        return name.replace(".", "$");
    }

    public @Nullable String toTsImport(@Nullable Class<?> type) {
        return toTsImport(type, false);
    }

    public @Nullable String toTsImport(@Nullable Class<?> type, boolean forceObject) {
        if (type == null || PackageExclusions.isExcluded(type)) return null;
        if (type.isArray()) return toTsImport(type.getComponentType());

        try {
            if (forceObject && type == Object.class) {
                String name = type.getName();
                String convert = Converters.convert(name);
                if (convert == null) convert = name;
                String s = convertImport(clazz, type, type.getPackageName(), convert);
                return s != null ? s.replace("import Object", "import java$lang$Object") : null;
            } else if (type == int.class || type == long.class || type == float.class || type == double.class
                    || type == boolean.class || type == String.class || type == void.class || type == Object.class
                    || type == short.class || type == byte.class || type == char.class)
                return null;
            else {
                String name = type.getName();
                String convert = Converters.convert(name);
                if (convert == null) convert = name;
                return convertImport(clazz, type, type.getPackageName(), convert);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to import: " + type.getName(), e);
        }
    }
}
