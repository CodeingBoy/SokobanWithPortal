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
        g.setColor(color);
        g.drawString(super.getLatestFrameRateString(), x, y);
        g.drawString(String.valueOf(super.getFrameRenderTime() + "ms"), x, y + 10);
        g.drawString(String.valueOf("Avg:" + super.getFrameAverageRenderTime() + "ms"), x, y + 30);
    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }
}
