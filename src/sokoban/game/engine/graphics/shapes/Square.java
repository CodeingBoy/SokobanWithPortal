package sokoban.game.engine.graphics.shapes;

import sokoban.game.engine.graphics.Vector2f;

/**
 * 正方形图形类
 */
public class Square extends Rect {
    public Square(Vector2f start, int width) {
        super(start, width, width);
    }

    public void setOriginVectors(Vector2f start, int width) {
        super.setOriginVectors(start, width, width);
    }
}
