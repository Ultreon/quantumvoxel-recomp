package dev.ultreon.quantum.teavm;

import org.jetbrains.annotations.Nullable;
import org.teavm.jso.JSBody;

public class TeaVMConsole {
    @JSBody(params = {"message"}, script = "console.info(message)")
    public static native void info(String message);

    @JSBody(params = {"message"}, script = "console.debug(message)")
    public static native void debug(String message);

    @JSBody(params = {"message"}, script = "console.warn(message)")
    public static native void warn(String message);

    @JSBody(params = {"message"}, script = "console.error(message)")
    public static native void error(String message);

    @JSBody(params = {"message", "obj"}, script = "console.info(message, obj)")
    public static native void info(String message, @Nullable Object object);

    @JSBody(params = {"message", "obj"}, script = "console.debug(message, obj)")
    public static native void debug(String message, @Nullable Object object);

    @JSBody(params = {"message", "obj"}, script = "console.warn(message, obj)")
    public static native void warn(String message, @Nullable Object object);

    @JSBody(params = {"message", "obj"}, script = "console.eror(message, obj)")
    public static native void error(String message, @Nullable Object object);

}