package sokoban.map.objects;

import sokoban.game.engine.graphics.Vector2f;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Floor extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/floor.png");

    public Floor(Vector2f start) {
        super(start, PIC);
    }

    public Floor(Point start) {
        this(new Vector2f(start.x, start.y));
    }
}
