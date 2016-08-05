package sokoban.game.engine.graphics;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-26-0026.
 */
public class Matrix3x3f {
    public final static Matrix3x3f ZERO = new Matrix3x3f(
            new double[][]{
                    {0f, 0f, 0f},
                    {0f, 0f, 0f},
                    {0f, 0f, 0f}
            }
    );
    public final static Matrix3x3f IDENTITY = new Matrix3x3f(
            new double[][]{
                    {1f, 0f, 0f},
                    {0f, 1f, 0f},
                    {0f, 0f, 1f}
            }
    );
    private double[][] matrix = new double[3][3];

    public Matrix3x3f(double[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix3x3f() {
    }

    public static void main(String[] args) {
        System.out.println(new Matrix3x3f(new double[][]{
                {3f, 2f, 1f},
                {3f, 2f, 1f}, {3f, 2f, 1f}
        }).mul(Matrix3x3f.ZERO));
    }

    public static Matrix3x3f translate(Point p) {
        return translate(p.x, p.y);
    }

    public static Matrix3x3f translate(Vector2f vector) {
        return translate(vector.x, vector.y);
    }

    public static Matrix3x3f translate(double x, double y) {
        return new Matrix3x3f(new double[][]{
                {1f, 0f, 0f},
                {0f, 1f, 0f},
                {x, y, 1f}
        });
    }

    public static Matrix3x3f scale(double x, double y) {
        return new Matrix3x3f(new double[][]{
                {x, 0f, 0f},
                {0f, y, 0f},
                {0f, 0f, 1f}
        });
    }

    public static Matrix3x3f scale(Vector2f vector) {
        return scale(vector.x, vector.y);
    }

    public static Matrix3x3f shear(double x, double y) {
        return new Matrix3x3f(new double[][]{
                {1f, y, 0f},
                {x, 1f, 0f},
                {0f, 0f, 1f}
        });
    }

    public static Matrix3x3f shear(Vector2f vector) {
        return shear(vector.x, vector.y);
    }

    public static Matrix3x3f rotate(double rad) {
        return new Matrix3x3f(new double[][]{
                {(double) Math.cos(rad), (double) Math.sin(rad), 0f},
                {(double) -Math.sin(rad), (double) Math.cos(rad), 0f},
                {0f, 0f, 1f}
        });
    }

    public Matrix3x3f add(Matrix3x3f m) {
        double[][] result = new double[3][3];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = this.matrix[i][j] + m.matrix[i][j];
            }
        }
        return new Matrix3x3f(result);
    }

    public Matrix3x3f sub(Matrix3x3f m) {
        double[][] result = new double[3][3];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = this.matrix[i][j] - m.matrix[i][j];
            }
        }
        return new Matrix3x3f(result);
    }

    public Matrix3x3f mul(Matrix3x3f m) {
        double[][] result = new double[3][3];
        for (int i = 0; i < result.length; i++) { // row
            for (int j = 0; j < result[i].length; j++) { // col
                for (int k = 0; k < result[i].length; k++) {
                    result[i][j] += this.matrix[i][k] * m.matrix[k][j];
                }
            }
        }
        return new Matrix3x3f(result);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Vector2f mul(Vector2f vector) {
        double x = vector.x * this.matrix[0][0] +
                vector.y * this.matrix[1][0] +
                vector.w * this.matrix[2][0];
        double y = vector.x * this.matrix[0][1] +
                vector.y * this.matrix[1][1] +
                vector.w * this.matrix[2][1];
        double w = vector.x * this.matrix[0][2] +
                vector.y * this.matrix[1][2] +
                vector.w * this.matrix[2][2];
        return new Vector2f(x, y, w);
    }

    public Vector2f[] mul(Vector2f... vectors) {
        Vector2f[] result = new Vector2f[vectors.length];
        for (int i = 0; i < vectors.length; i++) {
            result[i] = mul(vectors[i]);
        }
        return result;
    }

    public Vector2f toVector() {
        return this.mul(new Vector2f(0, 0, 1));
    }

    public Point toPoint() {
        Vector2f v = this.mul(new Vector2f(0, 0, 1));
        return new Point((int) v.x, (int) v.y);
    }
}
