package sokoban.map;

import sokoban.game.engine.graphics.Vector2f;
import sokoban.map.objects.MapObject;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Player extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/box.png");
    private GameMap map;

    public Player(Point point) {
        this(new Vector2f(point.x,point.y));
    }

    public Player(Vector2f start) {
        super(start, PIC);
    }

    public void move(Direction direction) {
        switch (direction) {
            case LEFT:
                move(p.x - 1, p.y);
                break;
            case RIGHT:
                move(p.x + 1, p.y);
                break;
            case UP:
                move(p.x, p.y + 1);
                break;
            case DOWN:
                move(p.x, p.y - 1);
                break;
        }
    }

    public void move(int x, int y) {
        if (map.isOKtoMove(x, y)) {
            setPos(new Point(x, y));
            return;
        } else {

        }

    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
