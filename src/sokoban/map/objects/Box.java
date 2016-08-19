package sokoban.map.objects;

import sokoban.map.Direction;
import sokoban.map.GameMap;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Box extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/box.png");
    private static GameMap map;

    public Box(Point pos) {
        super(pos, PIC);
    }

    public static void setMap(GameMap map) {
        Box.map = map;
    }

    public boolean move(Direction direction) {
        Point p = getPos();
        switch (direction) {
            case LEFT:
                return move(p.x - 1, p.y, direction);
            case RIGHT:
                return move(p.x + 1, p.y, direction);
            case UP:
                return move(p.x, p.y - 1, direction);
            case DOWN:
                return move(p.x, p.y + 1, direction);
        }
        return false;
    }

    public boolean move(int x, int y, Direction direction) {
        if (map.isOKtoMove(x, y, direction)) {
            setPos(new Point(x, y));
            return true;
        } else {
            return false;
        }

    }
}
