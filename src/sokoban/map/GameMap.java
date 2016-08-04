package sokoban.map;

import sokoban.game.engine.graphics.shapes.Drawable;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class GameMap implements Drawable {
    private Drawable[][] mapObjs;

    @Override
    public void draw(Graphics g, double delta) {

    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }
}
