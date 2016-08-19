package sokoban.map;

import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.map.objects.Floor;
import sokoban.map.objects.MapObject;

import java.awt.*;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */

/**
 * 地图类，请注意：本类中的所有坐标均使用屏幕坐标，仅在绘制时转换为世界坐标（虽然之后又被转回屏幕坐标）
 */
public class GameMap implements Drawable {
    private final static int PICWIDTH = 50, PICHEIGHT = 50;
    private MapObject[][] mapObjs;
    private int mapWidth, mapHeight;
    private String mapName;
    private Map<String, String> mapProperty;
    private Point playerStartPoint;
    private GameObjectsMappingTool gameObjectsMappingTool;

    public GameMap(MapObject[][] mapObjs, int mapWidth, int mapHeight, String mapName, Map<String, String> mapProperty) {
        this.mapObjs = mapObjs;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapName = mapName;
        this.mapProperty = mapProperty;

        String s = this.mapProperty.get("PlayerStartPoint");
        playerStartPoint = new Point(Integer.valueOf(s.split(",")[0]), Integer.parseInt(s.split(",")[1]));
    }

    public GameMap(MapObject[][] mapObjs, Map<String, String> mapProperty) {
        this(mapObjs, Integer.parseInt(mapProperty.get("MapWidth")), Integer.parseInt(mapProperty.get("MapHeight")),
                mapProperty.get("mapName"), mapProperty);
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMappingTool(GameObjectsMappingTool screenMappingTool) {
        this.gameObjectsMappingTool = screenMappingTool;
        MapObject.setGameObjectsMappingTool(gameObjectsMappingTool);
    }

    public String getMapName() {
        return mapName;
    }

    public String getProperty(String name) {
        return mapProperty.get(name);
    }

    public void updateMapScreenPos() {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                mapObjs[i][j].updateW2SVectors();
            }
        }
    }

    @Override
    public void draw(Graphics g, double delta) {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                mapObjs[i][j].draw(g, delta);
            }
        }
    }

    boolean isOKtoMove(int x, int y) {
        return isGridType(x, y, Floor.class);
    }

    <T extends MapObject> boolean isGridType(int x, int y, Class<T> type) {
        return type.isInstance(mapObjs[y][x]);
    }

    public Point getPlayerStartPoint() {
        return playerStartPoint;
    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }
}
