package sokoban.game.engine.graphics;

/**
 * Created by CodeingBoy on 2016-7-16-0016.
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

    public void move(double tx, double ty) {
        x += tx;
        y += ty;
    }

    public void scale(double sx, double sy) {
        x *= sx;
        y *= sx;
    }

    public void rotate(double rad) {
        double tmp = (double) (x * Math.cos(rad) - y * Math.sin(rad));
        y = (double) (x * Math.sin(rad) - y * Math.cos(rad));
        x = tmp;
    }

    public void shear(double sx, double sy) {
        double tmp = x + sx * y;
        y += sy * x;
        x = tmp;
    }

    public Vector2f reverse() {
        return new Vector2f(x, y);
    }

    public Vector2f add(Vector2f v) {
        return new Vector2f(x + v.x, y + v.y);
    }

    public Vector2f sub(Vector2f v) {
        return new Vector2f(x - v.x, y - v.y);
    }

    public Vector2f mul(double scale) {
        return new Vector2f(x * scale, y * scale);
    }

    public Vector2f div(double scale) {
        if (scale == 0) // 除数为0
            throw new IllegalArgumentException();
        return new Vector2f(x / scale, y / scale);
    }

    public double length() {
        return Math.sqrt(x * x + y * y); // 勾股定理
    }

    public double lengthSquare() {
        return x * x + y * y; // 勾股定理
    }

    public boolean isLonger(Vector2f v) {
        if (this.lengthSquare() < v.lengthSquare())
            return false;
        else
            return true;
    }

    public Vector2f norn() {
        return div(length());
    }

    public Vector2f prep() {
        return new Vector2f(-y, x);
    }

    public double dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public static Vector2f fromPolar(double angle, double radius) {
        return new Vector2f(
                radius * Math.cos(angle),
                radius * Math.sin(angle)
        );
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
