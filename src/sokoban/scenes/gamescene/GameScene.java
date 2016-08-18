package sokoban.scenes.gamescene;

import sokoban.game.engine.GameWindow;
import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.WindowRatioKeeper;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.map.GameMap;
import sokoban.map.MapParser;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class GameScene extends SuperScene {
    private static final int WORLD_WIDTH = 300;
    private static final int WORLD_HEIGHT = 300;
    private ScreenMappingTool screenMappingTool;
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

        windowRatioKeeper = new WindowRatioKeeper(null, window.getContentPane(), map.getMapWidth(), map.getMapHeight(), 0);
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

        window.setSize(window.getSize());
    }

    @Override
    public void onInitialize() {
        // window.addComponentListener(new WindowRatioKeeper(canvas, window.getContentPane(), map.getMapWidth(), map.getMapHeight(), 0));
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
    }

    private synchronized void createObjects() {

    }

    private synchronized void refreshObjects() {

    }
}
