package sokoban.game.engine.popup;

import sokoban.game.engine.graphics.shapes.Drawable;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-22-0022.
 */
public class Popup implements Drawable {
    @Override
    public void draw(Graphics g, double delta) {

    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }
}
