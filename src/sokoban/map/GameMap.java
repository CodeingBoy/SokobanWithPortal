package sokoban.map;

import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.shapes.Drawable;
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
    private String MapName;
    private Map<String, String> MapProperty;
    private ScreenMappingTool screenMappingTool;

    public GameMap(MapObject[][] mapObjs, int mapWidth, int mapHeight, String mapName, Map<String, String> mapProperty) {
        this.mapObjs = mapObjs;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        MapName = mapName;
        MapProperty = mapProperty;
    }

    public GameMap(MapObject[][] mapObjs, Map<String, String> mapProperty) {
        this.mapObjs = mapObjs;
        this.mapWidth = Integer.parseInt(mapProperty.get("MapWidth"));
        this.mapHeight = Integer.parseInt(mapProperty.get("MapHeight"));
        MapName = mapProperty.get("MapName");
        MapProperty = mapProperty;
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

    public String getProperty(String name) {
        return MapProperty.get(name);
    }

    public void updateMapScreenPos(){
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                mapObjs[i][j].worldToScreen(screenMappingTool);
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

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }
}
