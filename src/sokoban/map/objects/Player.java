package sokoban.map.objects;

import sokoban.map.Direction;
import sokoban.map.GameMap;
import sokoban.utils.Log;

import java.awt.*;

public class Player extends MapObject {
    private final static Class LOGCLASS = Player.class;
    private final static Image PIC = Toolkit.getDefaultToolkit().getImage("pic/box.png");
    private GameMap map;

    /**
     * 构造一个玩家对象
     *
     * @param pos 初始位置
     * @param map 所在的地图
     */
    public Player(Point pos, GameMap map) {
        super(pos, PIC);
        this.map = map;
    }

    /**
     * 按指定的方向移动一格
     *
     * @param direction 方向
     */
    public void move(Direction direction) {
        Point p = curPos;
        boolean success = false;
        switch (direction) {
            case LEFT:
                success = move(p.x - 1, p.y, direction);
                break;
            case RIGHT:
                success = move(p.x + 1, p.y, direction);
                break;
            case UP:
                success = move(p.x, p.y - 1, direction);
                break;
            case DOWN:
                success = move(p.x, p.y + 1, direction);
                break;
        }

        if (success) map.onPlayerMoved(getPos());
    }

    /**
     * * 移动到指定的坐标
     *
     * @param x         x坐标
     * @param y         y坐标
     * @param direction 方向
     * @return 是否移动成功
     */
    private boolean move(int x, int y, Direction direction) {
        Log.i("Player requesting move to " + x + "," + y);
        if (map.isOKtoMove(x, y, direction)) {
            setPos(new Point(x, y));
            Log.i(LOGCLASS, "Move approved! New pos " + x + "," + y);
            return true;
        } else {
            Log.i(LOGCLASS, "Move rejected!");
            return false;
        }
    }
}
