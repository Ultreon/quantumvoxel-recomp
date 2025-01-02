package dev.ultreon.langgen.api;

import com.badlogic.gdx.utils.ObjectMap;
import dev.ultreon.quantum.lwjgl3.Lwjgl3Logger;

import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.nio.*;
import java.util.*;
import java.util.function.Consumer;

public class PackageExclusions {
    public static final Lwjgl3Logger PACKAGE_EXCLUSIONS = new Lwjgl3Logger("PackageExclusions");
    private static Set<String> packageNames = new HashSet<>();

    public static void addExclusion(String packageName) {
        if (packageName.endsWith("."))
            throw new IllegalArgumentException("Cannot end with period.");

        packageNames.add(packageName);
    }

    public static boolean isExcluded(Class<?> type) {
        if (type == null) return true;
        if (type == String.class) return false;
        if (type.isPrimitive()) return false;
        if (type == Character.class) return false;
        if (type == Number.class) return false;
        if (type == Byte.class) return false;
        if (type == Short.class) return false;
        if (type == Integer.class) return false;
        if (type == Long.class) return false;
        if (type == Float.class) return false;
        if (type == Double.class) return false;
        if (type == Boolean.class) return false;
        if (type == Enum.class) return false;

        // Extras
        if (type == Object.class) return false;
        if (type == Throwable.class) return false;
        if (type == StackTraceElement.class) return false;
        if (type == PrintStream.class) return false;
        if (type == Class.class) return false;
        if (type == Optional.class) return false;
        if (type == Consumer.class) return false;
        if (type == Iterator.class) return false;
        if (type == Iterable.class) return false;
        if (type == Spliterator.class) return false;
        if (type == Consumer.class) return false;
        if (type == Runnable.class) return false;
        if (type == Buffer.class) return false;
        if (type == FloatBuffer.class) return false;
        if (type == IntBuffer.class) return false;
        if (type == DoubleBuffer.class) return false;
        if (type == ShortBuffer.class) return false;
        if (type == ByteBuffer.class) return false;
        if (type == LongBuffer.class) return false;
        if (type == ObjectMap.class
            || type == ObjectMap.Entry.class
            || type == ObjectMap.Keys.class
            || type == ObjectMap.Values.class
            || type == ObjectMap.Entries.class
        ) return true;
        if (!(Modifier.isPublic(type.getModifiers())
              || Modifier.isProtected(type.getModifiers()))) return true;

        if (type.isArray()) {
            return isExcluded(type.getComponentType());
        }

        if (type.getPackageName().startsWith("com.badlogic.gdx.")) return false;
        if (type.getPackageName().equals("com.badlogic.gdx")) return false;
        if (type.getPackageName().startsWith("dev.ultreon.quantum.")) return false;
        if (type.getPackageName().equals("dev.ultreon.quantum")) return false;


//        if (ClassCompat.isExcluded(type)) return true;
//        for (String name : packageNames) {
//            if (type.getName().startsWith(name + "."))
//                return true;
//        }
//
//        return false;

        return true;
    }
}
