package sokoban.map;

import sokoban.game.engine.graphics.Vector2f;
import sokoban.map.objects.MapObject;
import sokoban.utils.Log;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Player extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/box.png");
    private GameMap map;

    public Player(Point start, GameMap map) {
        super(new Vector2f(start.x, start.y), PIC);
        this.map = map;
    }

    public Player(Point point) {
        this(new Vector2f(point.x, point.y));
    }

    public Player(Vector2f start) {
        super(start, PIC);
    }

    public void move(Direction direction) {
        Point p = map.convertToScreen(getPos());
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
        Log.i("Player requesting move to " + x + "," + y);
        if (map.isOKtoMove(x, y)) {
            setPos(map.convertToWorld(new Point(x, y)));
            Log.i("Move approved! New pos " + x + "," + y);
            return;
        } else {
            Log.i("Move rejected!");
        }

    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
