package dev.ultreon.bubbles.switchgdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.ultreon.quantum.Logger;
import dev.ultreon.quantum.LoggerFactory;
import dev.ultreon.quantum.LoggingKt;
import dev.ultreon.quantum.client.QuantumClientKt;
import dev.ultreon.quantum.client.QuantumVoxel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SafeLoadWrapper implements ApplicationListener {
    private QuantumVoxel quantum;
    private SpriteBatch batch;
    private BitmapFont font;
    private StackTraceElement[] crash;
    private String exceptionMessage;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        try {
            QuantumClientKt.setGamePlatform(new SwitchPlatform());
            LoggingKt.setFactory(new LoggerFactory() {
                @Override
                public @NotNull Logger getLogger(@NotNull String name) {
                    return new Logger() {
                        @Override
                        public void trace(@NotNull String message, @Nullable Object obj) {
                            log("[TRACE] ", message);
                            sleep();
                        }

                        private void sleep() {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void debug(@NotNull String message, @Nullable Object obj) {
                            log("[DEBUG] ", message);
                            sleep();
                        }

                        @Override
                        public void error(@NotNull String message, @Nullable Object obj) {
                            log("[ERROR] ", message);
                            sleep();
                        }

                        @Override
                        public void warn(@NotNull String message, @Nullable Object obj) {
                            log("[WARN] ", message);
                            sleep();
                        }

                        @Override
                        public void info(@NotNull String message, @Nullable Object obj) {
                            log("[INFO] ", message);
                            sleep();
                        }

                        @Override
                        public void info(@NotNull String message) {
                            log("[INFO] ", message);
                            sleep();
                        }

                        @Override
                        public void warn(@NotNull String message) {
                            log("[WARN] ", message);
                            sleep();
                        }

                        @Override
                        public void error(@NotNull String message) {
                            log("[ERROR] ", message);
                            sleep();
                        }

                        @Override
                        public void debug(@NotNull String message) {
                            log("[DEBUG] ", message);
                            sleep();
                        }

                        @Override
                        public void trace(@NotNull String message) {
                            log("[TRACE] ", message);
                            sleep();
                        }

                        private void log(String prefix, String message) {
                            String message1 = message;
                            for (String line : message.split("\r\n|\r|\n")) {
                                System.out.println("[" + name + "/" + Thread.currentThread().getName() + "] " + prefix + line);
                            }
                        }
                    };
                }
            });

            quantum = new QuantumVoxel();
            quantum.create();
        } catch (Throwable e) {
            crash(e);
        }
    }

    @Override
    public void resize(int width, int height) {
        if (quantum != null) {
            try {
                quantum.resize(width, height);
            } catch (Throwable e) {
                crash(e);
            }
        }
    }

    private void crash(Throwable e) {
        this.crash = e.getStackTrace();
        exceptionMessage = e.getClass().getName() + ": " + e.getMessage();
        quantum = null;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        try {
            if (crash != null) {
                batch.begin();
                if (Gdx.graphics == null) {
                    System.out.println(exceptionMessage);
                    for (StackTraceElement element : crash) {
                        System.out.println("    at " + element.getClassName() + "." + element.getMethodName() + " (" + element.getFileName() + ".class" + ":" + element.getLineNumber() + ")");
                    }
                    batch.end();
                    System.exit(1);
                    return;
                }
                int y = Gdx.graphics.getHeight();
                if (font == null) {
                    font = new BitmapFont();
                }
                y -= (int) (font.getLineHeight() + 2);
                font.draw(batch, String.valueOf(exceptionMessage), 0, y);
                for (StackTraceElement element : crash) {
                    if (element == null) continue;
                    y -= (int) (font.getLineHeight() + 2);
                    font.draw(batch, "    at " + element.getClassName().replace('/', '.') + "." + element.getMethodName() + " (" + element.getFileName().substring(element.getFileName().lastIndexOf("/") + 1) + ".class" + ":" + element.getLineNumber() + ")", 0, y);
                }
                batch.end();
                return;
            }
            if (quantum != null) {
                quantum.render();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            if (batch.isDrawing()) batch.end();
            crash(e);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        if (quantum != null) {
            try {
                quantum.dispose();
            } catch (Throwable e) {
                crash(e);
            }
        }
    }
}
