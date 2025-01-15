package dev.ultreon.bubbles.switchgdx;

import com.badlogic.gdx.Gdx;
import dev.ultreon.quantum.client.GamePlatform;
import dev.ultreon.quantum.resource.ResourceManager;
import org.jetbrains.annotations.NotNull;

public class SwitchPlatform implements GamePlatform {
    @Override
    public void loadResources(@NotNull ResourceManager resourceManager) {
        resourceManager.loadFromAssetsTxt(Gdx.files.internal("assets.txt"));
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isMobile() {
        return true;
    }

    @Override
    public void nextFrame() {

    }
}
