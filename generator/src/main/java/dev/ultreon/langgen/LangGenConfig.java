package dev.ultreon.langgen;

import java.nio.file.Path;

public class LangGenConfig {
    public static boolean generateStub = true;
    public static Path stubPath = Path.of("modules/quantum-stubs");
    public static String version = "0.1.0";
}
