package sokoban.game.engine.graphics.shapes;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public abstract class Shape implements Drawable {
    protected double rotateDelta;
    protected Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public final void switchColor(Graphics g, double delta) {
        Color originColor = g.getColor();
        g.setColor(color);
        draw(g, delta);
        g.setColor(originColor);
    }

    public abstract boolean isPointInside(Point point);

    public abstract boolean isOverlapped(Shape shape);

    protected abstract void rotate(double rad);

    protected abstract void update(double delta);

    public final void setRotateDelta(double rotateDelta) {
        this.rotateDelta = rotateDelta;
    }
}
