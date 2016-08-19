package sokoban.map;

import sokoban.map.objects.MapObject;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class Box extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/box.png");
    private GameMap map;

    public Box(Point pos) {
        super(pos, PIC);
    }

    public void move(Direction direction) {
        Point p = getPos();
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
