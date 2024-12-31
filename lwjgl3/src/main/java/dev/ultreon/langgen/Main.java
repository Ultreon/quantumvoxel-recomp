package dev.ultreon.langgen;

import dev.ultreon.langgen.api.PackageExclusions;
import dev.ultreon.langgen.javascript.JavascriptGen;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.util.PathConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        OptionParser parser = new OptionParser(true);
        OptionSpec<Boolean> js = parser.acceptsAll(List.of("js", "javascript"), "Generate Javascript files").withOptionalArg().ofType(Boolean.class).defaultsTo(false);
        OptionSpec<Boolean> debug = parser.acceptsAll(List.of("debug"), "Debug mode").withOptionalArg().ofType(Boolean.class).defaultsTo(false);
        OptionSpec<Boolean> stub = parser.acceptsAll(List.of("stub"), "Stub mode").withOptionalArg().ofType(Boolean.class).defaultsTo(false);
        OptionSpec<Boolean> help = parser.acceptsAll(List.of("help"), "Help").withOptionalArg().ofType(Boolean.class).defaultsTo(false);
        OptionSpec<Path> output = parser.acceptsAll(List.of("o", "output"), "Output directory").withRequiredArg().ofType(File.class).withValuesConvertedBy(new PathConverter());

        OptionSet options = parser.parse(args);

        if (options.has(help) || args.length == 0) {
            parser.printHelpOn(System.out);
            System.exit(0);
            return;
        }

        Main.excludes();

        if (options.has("js") || options.has("javascript")) {
            new JavascriptGen().write(output.value(options));
        } else {
            parser.printHelpOn(System.out);
            System.exit(1);
        }
    }

    private static void excludes() {
        PackageExclusions.addExclusion("com.apple");
        PackageExclusions.addExclusion("java.rmi");
        PackageExclusions.addExclusion("apple");
        PackageExclusions.addExclusion("elemental2");
        PackageExclusions.addExclusion("de.damios");
        PackageExclusions.addExclusion("org.bouncycastle");
        PackageExclusions.addExclusion("org.apache.groovy");
        PackageExclusions.addExclusion("com.google.errorprone");
        PackageExclusions.addExclusion("com.google.thirdparty");
        PackageExclusions.addExclusion("dev.ultreon.mixinprovider");
        PackageExclusions.addExclusion("dev.ultreon.gameprovider");
        PackageExclusions.addExclusion("net.java");
        PackageExclusions.addExclusion("org.lwjgl");
        PackageExclusions.addExclusion("com.google");
        PackageExclusions.addExclusion("org.apache");
        PackageExclusions.addExclusion("jsinterop");
        PackageExclusions.addExclusion("netscape");
        PackageExclusions.addExclusion("");
        PackageExclusions.addExclusion("jdk");
        PackageExclusions.addExclusion("sun");
        PackageExclusions.addExclusion("java.awt");
        PackageExclusions.addExclusion("java.net");
        PackageExclusions.addExclusion("javax");
        PackageExclusions.addExclusion("jline");
        PackageExclusions.addExclusion("javassist");
        PackageExclusions.addExclusion("joptsimple");
        PackageExclusions.addExclusion("net.java");
        PackageExclusions.addExclusion("org.codehaus");
        PackageExclusions.addExclusion("org.checkerframework");
        PackageExclusions.addExclusion("org.intellij");
        PackageExclusions.addExclusion("org.jetbrains.annotations");
        PackageExclusions.addExclusion("org.graalvm");
        PackageExclusions.addExclusion("com.badlogic.gdx.backends");
        PackageExclusions.addExclusion("com.badlogic.gdx.graphics.profiling");
        PackageExclusions.addExclusion("org.json");
        PackageExclusions.addExclusion("org.jspecify");
        PackageExclusions.addExclusion("org.mozilla");
        PackageExclusions.addExclusion("org.objectweb");
        PackageExclusions.addExclusion("org.oxbow");
        PackageExclusions.addExclusion("org.reactivestreams");
        PackageExclusions.addExclusion("org.reflections");
        PackageExclusions.addExclusion("org.spongepowered");
        PackageExclusions.addExclusion("org.tukaani");
        PackageExclusions.addExclusion("org.w3c");
        PackageExclusions.addExclusion("org.xml");
        PackageExclusions.addExclusion("java.sql");
        PackageExclusions.addExclusion("java.security");
        PackageExclusions.addExclusion("java.management");
        PackageExclusions.addExclusion("java.beans");
        PackageExclusions.addExclusion("java.applet");
        PackageExclusions.addExclusion("net.fabricmc.impl");
        PackageExclusions.addExclusion("net.minecraft");
        PackageExclusions.addExclusion("scala");
        PackageExclusions.addExclusion("groovy");
        PackageExclusions.addExclusion("kotlin");
        PackageExclusions.addExclusion("kotlinx");
        PackageExclusions.addExclusion("clojure");
        PackageExclusions.addExclusion("junit");
        PackageExclusions.addExclusion("oracle");
        PackageExclusions.addExclusion("com.oracle");
        PackageExclusions.addExclusion("com.sun");
        PackageExclusions.addExclusion("io.javalin");
        PackageExclusions.addExclusion("io.github.classgraph");
        PackageExclusions.addExclusion("com.jcraft");
        PackageExclusions.addExclusion("com.jme3");
        PackageExclusions.addExclusion("javazoom");
        PackageExclusions.addExclusion("org.ietf");
        PackageExclusions.addExclusion("org.jcp");
        PackageExclusions.addExclusion("org.junit");
        PackageExclusions.addExclusion("org.opentest4j");
        PackageExclusions.addExclusion("net.miginfocom");
        PackageExclusions.addExclusion("org.spongepowered");
        PackageExclusions.addExclusion("org.bouncycastle");
        PackageExclusions.addExclusion("org.joml");
        PackageExclusions.addExclusion("groovyjarjarantlr");
        PackageExclusions.addExclusion("groovyjarjarantlr4");
        PackageExclusions.addExclusion("groovyjarjarasm");
        PackageExclusions.addExclusion("groovyjarjarpicocli");
    }
}
