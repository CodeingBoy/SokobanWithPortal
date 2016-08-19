package sokoban.map.objects;

import sokoban.map.Direction;
import sokoban.map.GameMap;
import sokoban.utils.Log;

import java.awt.*;

public class Player extends MapObject {
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/box.png");
    private GameMap map;

    /**
     * 构造一个玩家对象
     * @param pos 初始位置
     * @param map 所在的地图
     */
    public Player(Point pos, GameMap map) {
        super(pos, PIC);
        this.map = map;
    }

    /**
     * 按指定的方向移动一格
     * @param direction 方向
     */
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

    /**
     * 移动到指定的坐标
     * @param x x坐标
     * @param y y坐标
     * @param direction 方向
     */
    private void move(int x, int y, Direction direction) {
        Log.i("Player requesting move to " + x + "," + y);
        if (map.isOKtoMove(x, y, direction)) {
            setPos(new Point(x, y));
            Log.i("Move approved! New pos " + x + "," + y);
            map.isCompleted();
            return;
        } else {
            Log.i("Move rejected!");
        }

    }
}
