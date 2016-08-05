package sokoban.map.objects;

import sokoban.game.engine.graphics.Vector2f;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Wall extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/wall.png");

    public Wall(Vector2f start) {
        super(start, PIC);
    }

    public Wall(Point start) {
        this(new Vector2f(start.x, start.y));
    }

}
