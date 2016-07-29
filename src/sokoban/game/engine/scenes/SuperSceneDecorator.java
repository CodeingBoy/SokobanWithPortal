package sokoban.game.engine.scenes;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public abstract class SuperSceneDecorator extends SuperScene {
    protected SuperScene superScene;

    public SuperSceneDecorator(SuperScene superScene) {
        this.superScene = superScene;
    }

    @Override
    public void onInitialize() {
        superScene.onInitialize();
    }

    @Override
    public void onRendering() {
        superScene.onRendering();
    }

    @Override
    public void onPrepareRendering() {
        superScene.onPrepareRendering();
    }

    @Override
    public void onPrepare() {
        superScene.onPrepare();
    }

    @Override
    public void onDestroy() {
        superScene.onDestroy();
    }

    @Override
    public void onExitingRendering() {
        superScene.onExitingRendering();
    }

    @Override
    public void render(Graphics g, double delta) {
        superScene.render(g, delta);
    }
}
