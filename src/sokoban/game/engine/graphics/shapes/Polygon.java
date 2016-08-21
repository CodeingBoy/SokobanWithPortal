package sokoban.game.engine.graphics.shapes;

import sokoban.game.engine.graphics.Matrix3x3f;
import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.utils.TextDrawer;
import sokoban.utils.Settings;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public class Polygon extends Shape {
    protected Vector2f[] originVectors;
    protected Vector2f[] currentVectors;
    protected Matrix3x3f w2sMatrix;
    protected Vector2f[] w2sVectors;
    protected Matrix3x3f center;
    protected Color color;
    private boolean isWinding;

    protected Polygon() {
    }

    public Polygon(Vector2f... originVectors) {
        this.originVectors = originVectors;
    }

    public void setOriginVectors(Vector2f... originVectors) {
        this.originVectors = originVectors;
        refreshW2SVectors();
    }

    public void setWinding(boolean winding) {
        isWinding = winding;
    }

    public void setCenter(Matrix3x3f center) {
        this.center = center;
    }

    @Override
    public boolean isPointInside(Point point) {
        int inside = 0;

        if (currentVectors == null || currentVectors.length <= 2) return false; // 构不成图形
        Vector2f start = currentVectors[currentVectors.length - 1];
        boolean isPointAbove_start = start.y >= point.y; // 欲检测的点是否在起始点之上？

        for (Vector2f end : currentVectors) {
            boolean isPointAbove_end = end.y >= point.y; // 欲检测的点是否在终止点之上？
            if (isPointAbove_start != isPointAbove_end) { // 欲检测的点不在两点形成的线段的上面或下面
                double m = (end.y - start.y) / (end.x - start.x); // 计算斜率
                double x = start.x + (point.y - start.y) / m; // 根据斜率计算交点
                if (x >= point.x) {
                    if (isWinding) { // 是否将环绕着的部分也计算在内？
                        inside += isPointAbove_start ? 1 : -1; // 如果边从下往上 计数值+1 否则-1
                    } else {
                        inside = inside == 1 ? 0 : 1; // 奇数次时在图形内 偶数次时不在图形内
                    }
                }
            }
            // 当前点检测完毕 作为下次检测的起始点
            isPointAbove_start = isPointAbove_end;
            start = end;
        }

        return inside == 1;
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

    public void mul(Matrix3x3f m) {
        if (currentVectors == null)
            update(0);
        for (int i = 0; i < currentVectors.length; i++) {
            currentVectors[i] = m.mul(currentVectors[i]);
        }
    }

    @Override
    protected void update(double delta) {
        if (w2sVectors == null) {
            refreshW2SVectors();
        }

        if (currentVectors == null)
            currentVectors = new Vector2f[originVectors.length];

        System.arraycopy(w2sVectors, 0, currentVectors, 0, w2sVectors.length);

        if (rotateDelta != 0) {
            rotateAngle += rotateDelta * delta;
            rotate(rotateAngle);
        }

        if (center != null) {
            mul(center);
        }
    }

    @Override
    public void setWorldToScreen(ScreenMappingTool screenMappingTool) {
        w2sMatrix = screenMappingTool.getW2sMatrix();
        refreshW2SVectors();
    }

    private void refreshW2SVectors() {
        if (w2sMatrix == null)
            w2sVectors = originVectors;
        else
            w2sVectors = w2sMatrix.mul(originVectors);
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

        if (Settings.isDebugMode()) {
            drawDebugInf(g);
        }
    }

    @Override
    public void fill(Graphics g, double delta) {
        update(delta);
        g.fillPolygon(this.toAWTPolygon());
    }

    @Override
    protected void drawDebugInf(Graphics g) {
        TextDrawer.drawString(g, (int) currentVectors[0].x, (int) currentVectors[0].y, null, TextDrawer.COLOR_DEBUGINF, new String[]{
                originVectors[0].x + "," + originVectors[0].y,
                (int) currentVectors[0].x + "," + (int) currentVectors[0].y,
                String.valueOf("angle: " + rotateAngle)
        });
    }
}
