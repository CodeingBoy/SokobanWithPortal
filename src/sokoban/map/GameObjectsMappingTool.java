package sokoban.map;

import sokoban.game.engine.graphics.Matrix3x3f;

import java.awt.*;

/**
 * 地图对象坐标映射工具
 */
public class GameObjectsMappingTool {

    private float worldWidth, worldHeight;
    private float canvasWidth, canvasHeight;
    private Matrix3x3f w2sMatrix, s2wMatrix;

    public GameObjectsMappingTool(float worldWidth, float worldHeight, Canvas canvas) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        canvasWidth = canvas.getWidth() - 1;
        canvasHeight = canvas.getHeight() - 1;
        calcMappingMatrix();
    }

    public GameObjectsMappingTool(float worldWidth, float worldHeight, float canvasWidth, float canvasHeight) {
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
        w2sMatrix = Matrix3x3f.scale(scale_x, scale_y).mul(Matrix3x3f.translate(translate_x, translate_y));
        s2wMatrix = Matrix3x3f.translate(-translate_x, -translate_y).mul(Matrix3x3f.scale(scale_x, scale_y));
    }

    public Matrix3x3f getMappedMatrix(Matrix3x3f matrix) {
        return w2sMatrix.mul(matrix);
    }

    public Matrix3x3f getUnmappedMatrix(Matrix3x3f matrix) {
        return s2wMatrix.mul(matrix);
    }

    public Matrix3x3f getW2sMatrix() {
        return w2sMatrix;
    }
}
