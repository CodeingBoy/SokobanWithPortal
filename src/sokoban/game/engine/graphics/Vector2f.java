package sokoban.game.engine.graphics;

import java.awt.*;

/**
 * 向量类，提供了向量的表示和运算
 */
public class Vector2f {
    public double x, y, w;

    public Vector2f(double x, double y, double w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }

    public Vector2f(double x, double y) {
        this(x, y, 1);
    }

    public Vector2f(Point p) {
        this(p.x, p.y, 1);
    }

    /**
     * 返回基于弧度值创建的向量
     *
     * @param angle  角度
     * @param radius 半径
     * @return 基于弧度值的向量
     */
    public static Vector2f fromPolar(double angle, double radius) {
        return new Vector2f(
                radius * Math.cos(angle),
                radius * Math.sin(angle)
        );
    }

    /**
     * 对向量进行平移
     *
     * @param tx x偏移
     * @param ty y偏移
     */
    public void move(double tx, double ty) {
        x += tx;
        y += ty;
    }

    /**
     * 对向量进行缩放
     *
     * @param sx x轴缩放值
     * @param sy y轴缩放值
     */
    public void scale(double sx, double sy) {
        x *= sx;
        y *= sx;
    }

    /**
     * 对向量进行旋转
     *
     * @param rad 旋转的角度
     */
    public void rotate(double rad) {
        double tmp = (double) (x * Math.cos(rad) - y * Math.sin(rad));
        y = (double) (x * Math.sin(rad) - y * Math.cos(rad));
        x = tmp;
    }

    /**
     * 对向量进行切变
     *
     * @param sx x轴切变系数
     * @param sy y轴切变系数
     */
    public void shear(double sx, double sy) {
        double tmp = x + sx * y;
        y += sy * x;
        x = tmp;
    }

    /**
     * 获取当前向量的反向量
     *
     * @return 当前向量的反向量
     */
    public Vector2f reverse() {
        return new Vector2f(x, y);
    }

    /**
     * 将当前向量与传入向量进行相加
     *
     * @param v 欲相加的向量
     * @return 相加结果的向量
     */
    public Vector2f add(Vector2f v) {
        return new Vector2f(x + v.x, y + v.y);
    }

    /**
     * 将当前向量与传入向量进行相减
     *
     * @param v 欲相减的向量
     * @return 相减结果的向量
     */
    public Vector2f sub(Vector2f v) {
        return new Vector2f(x - v.x, y - v.y);
    }

    /**
     * 将当前向量与一个系数进行相乘
     *
     * @param scale 欲相乘的系数
     * @return 相乘结果的向量
     */
    public Vector2f mul(double scale) {
        return new Vector2f(x * scale, y * scale);
    }

    /**
     * 将当前向量与一个系数进行相除
     *
     * @param scale 欲相除的系数
     * @return 相除结果的向量
     * @throws IllegalArgumentException 除数为零
     */
    public Vector2f div(double scale) throws IllegalArgumentException {
        if (scale == 0) // 除数为0
            throw new IllegalArgumentException();
        return new Vector2f(x / scale, y / scale);
    }

    /**
     * 运用勾股定理计算向量长度
     *
     * @return 向量长度
     */
    public double length() {
        return Math.sqrt(x * x + y * y); // 勾股定理
    }

    /**
     * 获取向量长度的未开方版本，这在大致比较向量长度时比较有用
     *
     * @return 向量长度的未开方版本
     */
    public double lengthSquare() {
        return x * x + y * y; // 勾股定理
    }

    /**
     * 判断当前向量是否长于传入向量
     *
     * @param v 欲比较的向量
     * @return 是否长于传入向量
     */
    public boolean isLonger(Vector2f v) {
        if (this.lengthSquare() < v.lengthSquare())
            return false;
        else
            return true;
    }

    /**
     * 获取当前向量的单位向量
     *
     * @return 当前向量的单位向量
     */
    public Vector2f norn() {
        return div(length());
    }

    /**
     * 获得当前向量的垂直向量
     *
     * @return 当前向量的垂直向量
     */
    public Vector2f prep() {
        return new Vector2f(-y, x);
    }

    /**
     * 返回当前向量与传入向量的点积
     *
     * @param v 欲进行点积运算的向量
     * @return 当前向量与传入向量的点积单位向量
     */
    public double dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    /**
     * 获得向量与x轴所成的角度
     *
     * @return 向量与x轴所成的角度
     */
    public double getAngle() {
        return Math.atan2(y, x);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Vector2f{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", w=").append(w);
        sb.append('}');
        return sb.toString();
    }
}
