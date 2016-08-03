package sokoban.game.engine.graphics.shapes;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public interface Drawable {
    void draw(Graphics g, double delta);

    void fill(Graphics g, double delta);
}
