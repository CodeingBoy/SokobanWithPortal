package sokoban.game.engine.graphics.shapes;

import sokoban.game.engine.graphics.ScreenMappingTool;

import java.awt.*;

/**
 * 椭圆图形类
 */
public class Oval extends Shape {
    protected Point center;
    protected int width, height;

    public Oval(Point center, int width, int height) {
        this.center = center;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean isPointInside(Point point) {
        return false;
    }

    @Override
    public boolean isOverlapped(Shape shape) {
        return false;
    }

    @Override
    protected void rotate(double rad) {

    }

    @Override
    protected void update(double delta) {

    }

    @Override
    public void setWorldToScreen(ScreenMappingTool screenMappingTool) {

    }

    @Override
    protected void drawDebugInf(Graphics g) {

    }

    @Override
    public void move(double x, double y) {

    }

    @Override
    public void scale(double scaleDeltaX, double scaleDeltaY) {

    }

    @Override
    public void draw(Graphics g, double delta) {
        g.drawOval(center.x, center.y, width, height);
    }

    @Override
    public void fill(Graphics g, double delta) {
        g.fillOval(center.x, center.y, width, height);
    }
}
