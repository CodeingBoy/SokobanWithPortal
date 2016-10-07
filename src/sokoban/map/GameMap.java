package sokoban.map;

import sokoban.game.engine.graphics.Matrix3x3f;
import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.game.engine.input.handler.Clickable;
import sokoban.map.objects.Box;
import sokoban.map.objects.MapObject;
import sokoban.map.objects.Placeable;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * 地图类，请注意：本类中的所有坐标均使用屏幕坐标，仅在绘制时转换为世界坐标（虽然之后又被转回屏幕坐标）
 */
public class GameMap implements Drawable, Clickable {
    private final static int PICWIDTH = 50, PICHEIGHT = 50;
    private MapObject[][] mapObjs;
    private int mapWidth, mapHeight;
    private String mapName;
    private Map<String, String> mapProperty;
    private Point playerStartPoint;
    private GameObjectsMappingTool gameObjectsMappingTool;
    private ArrayList<Box> boxes = new ArrayList<>();
    private ArrayList<Point> checkPoints = new ArrayList<>();
    private ActionListener completeListener;

    /**
     * 构建一张游戏地图
     *
     * @param mapObjs     地图对象二维数组引用
     * @param mapWidth    地图宽度
     * @param mapHeight   地图高度
     * @param mapName     地图名
     * @param mapProperty 地图属性
     */
    public GameMap(MapObject[][] mapObjs, int mapWidth, int mapHeight, String mapName, Map<String, String> mapProperty) {
        this.mapObjs = mapObjs;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapName = mapName;
        this.mapProperty = mapProperty;

        String s = this.mapProperty.get("PlayerStartPoint");
        playerStartPoint = new Point(Integer.valueOf(s.split(",")[0]) - 1,
                Integer.parseInt(s.split(",")[1]) - 1); // 减一是因为屏幕坐标系从0,0开始

        Box.setMap(this);
        s = this.mapProperty.get("BoxesStartPoint");
        for (String s1 : s.split(";")) {
            boxes.add(new Box(new Point(Integer.valueOf(s1.split(",")[0]) - 1,
                    Integer.parseInt(s1.split(",")[1]) - 1)));
        }

        s = this.mapProperty.get("CheckPoints");
        for (String s1 : s.split(";")) {
            checkPoints.add(new Point(Integer.valueOf(s1.split(",")[0]) - 1,
                    Integer.parseInt(s1.split(",")[1]) - 1));
        }
    }

    /**
     * 构建一张游戏地图
     *
     * @param mapObjs     地图对象二维数组引用
     * @param mapProperty 地图属性
     */
    public GameMap(MapObject[][] mapObjs, Map<String, String> mapProperty) {
        this(mapObjs, Integer.parseInt(mapProperty.get("MapWidth")), Integer.parseInt(mapProperty.get("MapHeight")),
                mapProperty.get("mapName"), mapProperty);
    }

    /**
     * 设置游戏目标完成监听器，该监听器会在游戏目标完成时被调用
     *
     * @param completeListener 欲设置之游戏目标完成监听器
     */
    public void setCompleteListener(ActionListener completeListener) {
        this.completeListener = completeListener;
    }

    /**
     * 获取地图的高度
     *
     * @return 地图高度（单位：格）
     */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * 获取地图的宽度
     *
     * @return 地图宽度（单位：格）
     */
    public int getMapHeight() {
        return mapHeight;
    }

    public void setMappingTool(GameObjectsMappingTool screenMappingTool) {
        this.gameObjectsMappingTool = screenMappingTool;
        MapObject.setGameObjectsMappingTool(gameObjectsMappingTool);
    }

    /**
     * 获取地图的名字
     *
     * @return 地图名
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * 获取地图文件中定义的属性
     *
     * @param name 属性名
     * @return 属性对应的值 若无该属性，返回null
     */
    public String getProperty(String name) {
        return mapProperty.get(name);
    }

    public void updateMapScreenPos() {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                mapObjs[i][j].updateW2SVectors();
            }
        }
        for (Box b : boxes) {
            b.updateW2SVectors();
        }
    }

    @Override
    public void draw(Graphics g, double delta) {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                mapObjs[i][j].draw(g, delta);
            }
        }
        for (Box b : boxes) {
            b.draw(g, delta);
        }
    }

    /**
     * 是否可以移动到指定坐标
     *
     * @param x         x坐标
     * @param y         y坐标
     * @param direction 方向
     * @return 是否可以移动到指定坐标
     */
    public boolean isOKtoMove(int x, int y, Direction direction) {
        if (x >= mapWidth || x < 0 ||
                y >= mapHeight || y < 0) return false;

        Box box = getBox(x, y);
        if (box != null) {
            if (!box.move(direction))
                return false;
        }

        return isGridType(x, y, Placeable.class);
    }

    /**
     * 获取指定坐标的箱子
     *
     * @param x x坐标
     * @param y y坐标
     * @return 指定坐标的箱子，若无，返回null
     */
    private Box getBox(int x, int y) {
        for (Box b : boxes) {
            Point pos = b.getPos();
            if (pos.x == x && pos.y == y)
                return b;
        }
        return null;
    }

    /**
     * 全部checkpoint是否已放置箱子
     *
     * @return 若是，返回true
     */
    public boolean isCompleted() {
        boolean result = true;
        for (Point p : checkPoints) {
            if (getBox(p.x, p.y) == null) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 判断传入坐标是否指定的类型
     *
     * @param x    x坐标
     * @param y    y坐标
     * @param type 欲判断的类型
     * @return 若是指定的类型，返回true
     */
    boolean isGridType(int x, int y, Class type) {
        return type.isInstance(mapObjs[y][x]);
    }

    /**
     * 获取玩家起始出发位置，在地图文件中定义
     *
     * @return 玩家起始出发位置
     */
    public Point getPlayerStartPoint() {
        return playerStartPoint;
    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }

    @Override
    public boolean isPointInside(Point point) {
        return true;
    }

    @Override
    public void onClick(Point p) {
        System.out.println(p);
        System.out.println(gameObjectsMappingTool.getUnmappedMatrix(Matrix3x3f.translate(new Vector2f(p))).toPoint());
    }

    @Override
    public void onHover(Point p) {
        for (MapObject[] row : mapObjs) {
            for (MapObject o : row) {
                if (o instanceof Placeable && o.isPointInside(p)) {
                    ((Clickable) o).onHover(p);
                    return;
                }
            }
        }
    }

    @Override
    public void onEnteringHover(Point p) {

    }

    @Override
    public void onExitingHover(Point p) {

    }

    /**
     * 当玩家产生移动动作后，此函数会被调用
     *
     * @param newPos 玩家移动后的位置
     */
    public void onPlayerMoved(Point newPos) {
        if (isCompleted()) {
            if (completeListener != null)
                completeListener.actionPerformed(null);
        }
    }
}
