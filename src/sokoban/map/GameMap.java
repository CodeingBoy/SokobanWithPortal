package sokoban.map;

import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.map.objects.Floor;
import sokoban.map.objects.MapObject;

import java.awt.*;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class GameMap implements Drawable {
    private final static int PICWIDTH = 50, PICHEIGHT = 50;
    private MapObject[][] mapObjs;
    private int mapWidth, mapHeight;
    private String mapName;
    private Map<String, String> mapProperty;
    private Point playerStartPoint;
    private ScreenMappingTool screenMappingTool;
    private int XOffset = 0, YOffset = 0;

    public GameMap(MapObject[][] mapObjs, int mapWidth, int mapHeight, String mapName, Map<String, String> mapProperty) {
        this.mapObjs = mapObjs;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapName = mapName;
        this.mapProperty = mapProperty;

        String s = this.mapProperty.get("PlayerStartPoint");
        playerStartPoint = new Point(Integer.valueOf(s.split(",")[0]), Integer.parseInt(s.split(",")[1]));

        if (mapProperty.get("Offset") != null) {
            XOffset = Integer.parseInt(mapProperty.get("Offset").split(",")[0]);
            YOffset = -Integer.parseInt(mapProperty.get("Offset").split(",")[1]);
        }
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

    public void setScreenMappingTool(ScreenMappingTool screenMappingTool) {
        this.screenMappingTool = screenMappingTool;
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
                mapObjs[i][j].setWorldToScreen(screenMappingTool);
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

    public Point convertToWorld(Point point) {
        return new Point(point.x + XOffset, point.y + YOffset);
    }

    public Point convertToScreen(Point point) {
        return new Point(point.x - XOffset, point.y - YOffset);
    }

    private Point convertToWorld(int x, int y) {
        return new Point(x + XOffset, y + YOffset);
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
