package sokoban.map.objects;

import sokoban.game.engine.graphics.shapes.Drawable;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Floor implements Drawable {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/floor.png");
    private Point p;

    @Override
    public void draw(Graphics g, double delta) {
        g.drawImage(PIC, p.x, p.y, null);
    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }
}
