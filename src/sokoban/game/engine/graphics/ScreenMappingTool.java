package sokoban.game.engine.graphics;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class ScreenMappingTool {
    private float worldWidth, worldHeight;
    private float screenWidth, screenHeight;
    private Matrix3x3f mappingMatrix;

    public ScreenMappingTool(float worldWidth, float worldHeight, Canvas canvas) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        screenWidth = canvas.getWidth() - 1;
        screenHeight = canvas.getHeight() - 1;
        calcMappingMatrix();
    }

    public ScreenMappingTool(float worldWidth, float worldHeight, float screenWidth, float screenHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        calcMappingMatrix();
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public void setWorldWidth(float worldWidth) {
        this.worldWidth = worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public void setWorldHeight(float worldHeight) {
        this.worldHeight = worldHeight;
    }

    private void calcMappingMatrix() {
        // calc scale ratio
        float scale_x = screenWidth / worldWidth;
        float scale_y = screenHeight / worldHeight;
        // calc translate
        float translate_x = screenWidth / 2;
        float translate_y = screenWidth / 2;
        // calc matrix
        mappingMatrix = Matrix3x3f.scale(scale_x, -scale_y).mul(Matrix3x3f.translate(translate_x, translate_y));
    }

    public Matrix3x3f getScreenMatrix(Matrix3x3f matrix) {
        return mappingMatrix.mul(matrix);
    }

    public Point getScreenPoint(Point worldPoint) {
        Vector2f v = getScreenMatrix(Matrix3x3f.translate(new Vector2f(worldPoint.x, worldPoint.y, 1))).toVector();
        return new Point((int) v.x, (int) v.y);
    }

    public Matrix3x3f getMappingMatrix() {
        return mappingMatrix;
    }
}
