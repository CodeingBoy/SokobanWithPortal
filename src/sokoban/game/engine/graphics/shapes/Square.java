package sokoban.game.engine.graphics.shapes;

import sokoban.game.engine.graphics.Vector2f;

/**
 * Created by CodeingBoy on 2016-8-3-0003.
 */
public class Square extends Rect {
    public Square(Vector2f start, int width) {
        super(start, width, width);
    }

    public void setOriginVectors(Vector2f start, int width) {
        super.setOriginVectors(start, width, width);
    }
}
