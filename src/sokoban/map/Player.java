package sokoban.map;

import sokoban.map.objects.MapObject;
import sokoban.utils.Log;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Player extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/box.png");
    private GameMap map;

    public Player(Point pos, GameMap map) {
        super(pos, PIC);
        this.map = map;
    }

    public void move(Direction direction) {
        Point p = curPos;
        switch (direction) {
            case LEFT:
                move(p.x - 1, p.y, direction);
                break;
            case RIGHT:
                move(p.x + 1, p.y, direction);
                break;
            case UP:
                move(p.x, p.y - 1, direction);
                break;
            case DOWN:
                move(p.x, p.y + 1, direction);
                break;
        }
    }

    public void move(int x, int y, Direction direction) {
        Log.i("Player requesting move to " + x + "," + y);
        if (map.isOKtoMove(x, y, direction)) {
            setPos(new Point(x, y));
            Log.i("Move approved! New pos " + x + "," + y);
            return;
        } else {
            Log.i("Move rejected!");
        }

    }
}
