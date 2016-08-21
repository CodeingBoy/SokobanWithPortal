package sokoban.game.utils;

import sokoban.game.engine.graphics.shapes.Drawable;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class FrameRateDrawable extends FrameRateCalculator implements Drawable {
    int x, y;
    Color color;

    public FrameRateDrawable(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.white;
    }

    public FrameRateDrawable(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public FrameRateDrawable(int x, int y, Color color, boolean shouldLog) {
        this.x = x;
        this.y = y;
        this.color = color;
        super.setShouldLog(shouldLog);
    }

    @Override
    public void draw(Graphics g, double delta) {
        super.calculate();

        TextDrawer.drawString(g, x, y, new String[]{
                super.getLatestFrameRateString(),
                super.getFrameRenderTime() + "ms",
                String.valueOf("Avg:" + super.getFrameAverageRenderTime() + "ms")
        });
    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }
}
