package sokoban.game.engine.scenes;

import sokoban.game.utils.FrameRateCalculator;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class FrameRateScene extends SuperSceneDecorator {
    int x, y;
    Color color;
    private FrameRateCalculator frameRate = new FrameRateCalculator();

    public FrameRateScene(SuperScene scene, int x, int y) {
        super(scene);
        this.x = x;
        this.y = y;
        this.color = Color.white;
    }

    public FrameRateScene(SuperScene scene, int x, int y, Color color) {
        super(scene);
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public FrameRateScene(SuperScene scene, int x, int y, Color color, boolean shouldLog) {
        super(scene);
        this.x = x;
        this.y = y;
        this.color = color;
        frameRate.setShouldLog(shouldLog);
    }

    @Override
    public void beforeRendering() {
        super.onPrepareRendering();
        frameRate.initialize();
    }

    @Override
    public void render(Graphics g, double delta) {
        frameRate.calculate();
        g.setColor(color);
        g.drawString(frameRate.getLatestFrameRateString(), x, y);
        super.render(g, delta);
    }
}
