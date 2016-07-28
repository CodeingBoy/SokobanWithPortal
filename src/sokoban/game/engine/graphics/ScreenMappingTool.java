package sokoban.game.engine.graphics;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class ScreenMappingTool {
    private float worldWidth, worldHeight;
    private float canvasWidth, canvasHeight;
    private Matrix3x3f mappingMatrix;

    public ScreenMappingTool(float worldWidth, float worldHeight, Canvas canvas) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        canvasWidth = canvas.getWidth() - 1;
        canvasHeight = canvas.getHeight() - 1;
        calcMappingMatrix();
    }

    public ScreenMappingTool(float worldWidth, float worldHeight, float canvasWidth, float canvasHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        calcMappingMatrix();
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    private void calcMappingMatrix() {
        // calc scale ratio
        float scale_x = canvasWidth / worldWidth;
        float scale_y = canvasHeight / worldHeight;
        // calc translate
        float translate_x = canvasWidth / 2;
        float translate_y = canvasHeight / 2;
        // calc matrix
        mappingMatrix = Matrix3x3f.scale(scale_x, -scale_y).mul(Matrix3x3f.translate(translate_x, translate_y));
    }

    public Matrix3x3f getScreenMatrix(Matrix3x3f matrix) {
        return mappingMatrix.mul(matrix);
    }

    public Point worldToScreen(Point worldPoint) {
        Vector2f v = getScreenMatrix(Matrix3x3f.translate(new Point(worldPoint.x, worldPoint.y))).toVector();
        return new Point((int) v.x, (int) v.y);
    }

    public Matrix3x3f getMappingMatrix() {
        return mappingMatrix;
    }
}
