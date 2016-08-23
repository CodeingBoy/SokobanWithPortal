package sokoban.game.engine.graphics.shapes;

import sokoban.game.engine.graphics.Matrix3x3f;
import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.utils.TextDrawer;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public class Rect extends Polygon {
    private double width, height;

    // public Rect(Vector2f v1, Vector2f v2, Vector2f v3, Vector2f v4) {
    //     super(v1, v2, v3, v4);
    // }

    // public Rect(Point p1, Point p2, Point p3, Point p4) {
    //     super(Arrays.asList(p1, p2, p3, p4));
    // }

    public Rect(Vector2f start, int width, int height) {
        super(start, new Vector2f(start.x + width, start.y),
                new Vector2f(start.x + width, start.y + height), new Vector2f(start.x, start.y + height));
        this.width = width;
        this.height = height;
    }

    public void setOriginVectors(Vector2f start, int width, int height) {
        super.setOriginVectors(start, new Vector2f(start.x + width, start.y),
                new Vector2f(start.x + width, start.y + height), new Vector2f(start.x, start.y + height));
    }

    /**
     * 基于左上角不变的前提下，缩放图形的边到指定的倍率
     *
     * @param x x边缩放倍率
     * @param y y边缩放倍率
     */
    @Override
    public void scale(double x, double y) {
        currentVectors[2] = Matrix3x3f.scale(x, y).mul(currentVectors[2]);

        width = currentVectors[2].x - currentVectors[0].x;
        height = currentVectors[2].y - currentVectors[0].y;

        currentVectors[1].x = currentVectors[2].x;
        currentVectors[3].y = currentVectors[2].y;
    }

    // public Rect(Point start, int width, int height) {
    //     super();
    //     super(Arrays.asList(start, new Point(start.x + width, start.y),
    //             new Point(start.x + width, start.y + height), new Point(start.x, start.y + height)));
    // }


    @Override
    protected void drawDebugInf(Graphics g) {
        TextDrawer.drawString(g, (int) currentVectors[0].x, (int) currentVectors[0].y, null, TextDrawer.COLOR_DEBUGINF, new String[]{
                originVectors[0].x + "," + originVectors[0].y,
                (int) currentVectors[0].x + "," + (int) currentVectors[0].y,
                String.valueOf("angle: " + rotateAngle),
                String.valueOf("size:" + (int) width + " x " + (int) height)
        });
    }
}
