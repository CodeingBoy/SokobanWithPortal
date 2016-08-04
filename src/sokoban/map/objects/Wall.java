package sokoban.map.objects;

import sokoban.game.engine.graphics.shapes.Drawable;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Wall implements Drawable{
    @Override
    public void draw(Graphics g, double delta) {

    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }
}
