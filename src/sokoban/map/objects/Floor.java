package sokoban.map.objects;

import sokoban.game.engine.input.handler.Clickable;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Floor extends MapObject implements Placeable, Clickable {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/floor.png");
    private boolean hovering = false;

    public Floor(Point pos) {
        super(pos, PIC);
    }

    protected Floor(Point pos, Image pic) {
        super(pos, pic);
    }

    @Override
    public void onClick(Point p) {

    }

    @Override
    public void onHover(Point p) {
        hovering = true;
    }

    @Override
    public void onEnteringHover(Point p) {

    }

    @Override
    public void onExitingHover(Point p) {

    }

    @Override
    public void draw(Graphics g, double delta) {
        super.draw(g, delta);

        if (hovering) {
            g.setColor(new Color(255, 255, 255, 150));
            g.fillRect((int) currentVectors[0].x, (int) currentVectors[0].y,
                    (int) (currentVectors[2].x - currentVectors[0].x), (int) (currentVectors[2].y - currentVectors[0].y));
        }
        hovering = false;
    }
}
