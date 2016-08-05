package sokoban.map.objects;

import sokoban.game.engine.graphics.Vector2f;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Box extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/box.png");

    public Box(Vector2f start) {
        super(start, PIC);
    }

    public Box(Point start) {
        super(start);
    }
}
