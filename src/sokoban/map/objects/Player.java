package sokoban.map.objects;

import sokoban.map.Direction;
import sokoban.map.GameMap;
import sokoban.utils.Log;

import java.awt.*;

/**
 * 玩家类，代表人在游戏中操控的部分。
 */
public class Player extends MapObject {
    private final static Class LOGCLASS = Player.class;
    private final static Image PIC_UP = Toolkit.getDefaultToolkit().getImage("pic/player/up.png");
    private final static Image PIC_DOWN = Toolkit.getDefaultToolkit().getImage("pic/player/down.png");
    private final static Image PIC_LEFT = Toolkit.getDefaultToolkit().getImage("pic/player/left.png");
    private final static Image PIC_RIGHT = Toolkit.getDefaultToolkit().getImage("pic/player/right.png");
    private GameMap map;
    private Direction lastDirection = Direction.LEFT;

    /**
     * 构造一个玩家对象
     *
     * @param pos 初始位置
     * @param map 所在的地图
     */
    public Player(Point pos, GameMap map) {
        super(pos, PIC_UP);
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

    @Override
    public void draw(Graphics g, double delta) {
        super.draw(g, delta);

        Image drawingPic = null;

        switch (lastDirection) {
            case UP:
                drawingPic = PIC_UP;
                break;
            case DOWN:
                drawingPic = PIC_DOWN;
                break;
            case LEFT:
                drawingPic = PIC_LEFT;
                break;
            case RIGHT:
                drawingPic = PIC_RIGHT;
                break;
        }

        if (drawingPic == null)
            throw new RuntimeException("have no drawingPic!");

        g.drawImage(drawingPic, (int) currentVectors[0].x, (int) currentVectors[0].y,
                (int) (currentVectors[2].x - currentVectors[0].x), (int) (currentVectors[2].y - currentVectors[0].y), null);
    }

    /**
     * 移动到指定的坐标
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
            lastDirection = direction;
            Log.i(LOGCLASS, "Move approved! New pos " + x + "," + y);
            Log.i(LOGCLASS, "Player direction had been set to " + lastDirection);
            return true;
        } else {
            Log.i(LOGCLASS, "Move rejected!");
            return false;
        }
    }
}
