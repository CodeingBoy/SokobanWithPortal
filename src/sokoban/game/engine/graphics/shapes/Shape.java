package sokoban.game.engine.graphics.shapes;

import sokoban.game.engine.graphics.ScreenMappingTool;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public abstract class Shape implements Drawable {
    protected double rotateDelta;
    protected double rotateAngle;
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

    /**
     * 检测某点是否在图像内
     *
     * @param point 欲检测的点
     * @return 传入点是否在图像内
     */
    public abstract boolean isPointInside(Point point);

    /**
     * 检测某图形是否与当前图形有重叠
     *
     * @param shape 欲检测的图形
     * @return 传入图形是否与当前图形有重叠
     */
    public abstract boolean isOverlapped(Shape shape);

    /**
     * 使图形旋转一定的角度
     *
     * @param rad 旋转角度
     */
    protected abstract void rotate(double rad);

    /**
     * 更新图像 在渲染之前请先调用此函数并在此函数内实现图像的更新
     *
     * @param delta 时间增量
     */
    protected abstract void update(double delta);

    public double getRotateAngle() {
        return rotateAngle;
    }

    public void setRotateAngle(double rotateAngle) {
        this.rotateAngle = rotateAngle;
    }

    /**
     * 获得每秒旋转的速度
     *
     * @return 每秒旋转的速度
     */
    public double getRotateSpeed() {
        return rotateDelta;
    }

    /**
     * 设置每秒旋转的速度 设为0表示不旋转
     *
     * @param rotateDelta 旋转速度
     */
    public final void setRotateSpeed(double rotateDelta) {
        this.rotateDelta = rotateDelta;
    }

    public abstract void setWorldToScreen(ScreenMappingTool screenMappingTool);

    protected abstract void drawDebugInf(Graphics g);
}
