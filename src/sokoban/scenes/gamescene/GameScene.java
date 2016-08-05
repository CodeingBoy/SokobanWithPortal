package sokoban.scenes.gamescene;

import sokoban.game.engine.GameWindow;
import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.map.GameMap;
import sokoban.map.MapParser;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class GameScene extends SuperScene {
    private static final int WORLD_WIDTH = 300;
    private static final int WORLD_HEIGHT = 300;
    private ScreenMappingTool screenMappingTool;
    private Map<String, Drawable> drawables = new HashMap<>();
    private GameMap map;

    public static void main(String[] args) {
        GameWindow window = new GameWindow(new Dimension(800, 600), "Test", new GameScene());
        window.showWindow();
    }

    @Override
    public void onPrepare() {
        try {
            map = MapParser.parseMapFile(new File("map/map1.map"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onExitingRendering() {

    }

    @Override
    public void beforeRendering() {
        map.setScreenMappingTool(new ScreenMappingTool(map.getMapWidth(), map.getMapHeight(), canvas));
        map.updateMapScreenPos();
        drawables.put("Map", map);
    }

    @Override
    public void onInitialize() {
        canvas = new Canvas();
        canvas.setBackground(Color.black);

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                screenMappingTool = new ScreenMappingTool(map.getMapWidth(), map.getMapHeight(), canvas);
                map.setScreenMappingTool(new ScreenMappingTool(map.getMapWidth(), map.getMapHeight(), canvas));
                map.updateMapScreenPos();
                // refreshObjects();
            }
        });
        super.onInitialize();
    }

    @Override
    public void afterTiming() {

    }

    @Override
    public void render(Graphics g, double delta) {
        g.setColor(Color.white);
        // CoordinateSystemShower.drawMappedWorldXAxis(g, 0, 10, 1, canvas.getHeight(), screenMappingTool, 0);
        // CoordinateSystemShower.drawMappedWorldYAxis(g, 0, 3, 1, canvas.getWidth(), screenMappingTool, 0);

        renderDrawables(g, delta);
    }

    private synchronized void renderDrawables(Graphics g, double delta) {
        for (Drawable d : drawables.values()) {
            d.draw(g, delta);
        }
    }

    private synchronized void createObjects() {

    }

    private synchronized void refreshObjects() {

    }
}
