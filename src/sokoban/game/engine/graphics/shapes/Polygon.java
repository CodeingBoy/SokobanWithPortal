package sokoban.game.engine.graphics.shapes;

import sokoban.game.engine.graphics.Matrix3x3f;
import sokoban.game.engine.graphics.Vector2f;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public class Polygon extends Shape {
    protected Vector2f[] originVectors;
    protected Vector2f[] currentVectors;
    protected Matrix3x3f center;
    protected double rotateAngle;

    protected Color color;

    protected Polygon() {
    }

    public Polygon(Vector2f... originVectors) {
        this.originVectors = originVectors;
    }

    public void setCenter(Matrix3x3f center) {
        this.center = center;
    }

    @Override
    public boolean isPointInside(Point point) {
        return false;
    }

    @Override
    public boolean isOverlapped(Shape shape) {
        return false;
    }

    public java.awt.Polygon toAWTPolygon() {
        java.awt.Polygon polygon = new java.awt.Polygon();
        for (Vector2f v : originVectors) {
            polygon.addPoint((int) v.x, (int) v.y);
        }
        return polygon;
    }

    public void scale(double x, double y) {
        mul(Matrix3x3f.scale(x, y));
    }

    public void rotate(double rad) {
        mul(Matrix3x3f.rotate(rad));
    }

    private void mul(Matrix3x3f m) {
        for (int i = 0; i < currentVectors.length; i++) {
            currentVectors[i] = m.mul(currentVectors[i]);
        }
    }

    @Override
    protected void update(double delta) {
        if (currentVectors == null)
            currentVectors = new Vector2f[originVectors.length];

        System.arraycopy(originVectors, 0, currentVectors, 0, originVectors.length);

        if (rotateDelta != 0) {
            rotateAngle += rotateDelta * delta;
            rotate(rotateAngle);
        }

        if (center != null) {
            mul(center);
        }


    }

    @Override
    public void draw(Graphics g, double delta) {
        update(delta);

        Color originColor = g.getColor();
        if (color != null)
            g.setColor(color);

        for (int i = 0; i < currentVectors.length - 1; i++)
            g.drawLine((int) currentVectors[i].x, (int) currentVectors[i].y,
                    (int) currentVectors[i + 1].x, (int) currentVectors[i + 1].y);

        g.drawLine((int) currentVectors[currentVectors.length - 1].x, (int) currentVectors[currentVectors.length - 1].y,
                (int) currentVectors[0].x, (int) currentVectors[0].y);

        // g.drawLine((int) currentVectors[0].x, (int) currentVectors[0].y, center.toPoint().x, center.toPoint().y);

        g.setColor(originColor);

    }

    @Override
    public void fill(Graphics g, double delta) {
        update(delta);
        g.fillPolygon(this.toAWTPolygon());
    }
}
