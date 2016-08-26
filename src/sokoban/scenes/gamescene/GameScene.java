package sokoban.scenes.gamescene;

import sokoban.game.engine.GameWindow;
import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.WindowRatioKeeper;
import sokoban.game.engine.input.KeyboardInput;
import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.KeyboardInputHandler;
import sokoban.game.engine.input.handler.SuperMouseInputHandler;
import sokoban.game.engine.popups.MessageBoxPopup;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.map.Direction;
import sokoban.map.GameMap;
import sokoban.map.GameObjectsMappingTool;
import sokoban.map.MapParser;
import sokoban.map.objects.MapObject;
import sokoban.map.objects.Player;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.EnumSet;

import static sokoban.game.engine.popups.MessageBoxPopup.Style;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class GameScene extends SuperScene {
    private static final int WORLD_WIDTH = 300;
    private static final int WORLD_HEIGHT = 300;
    Player player;
    private ScreenMappingTool screenMappingTool;
    private GameMap map;

    public GameScene(GameMap map) {
        this.map = map;
    }

    public GameScene(File mapFile) throws FileNotFoundException {
        map = MapParser.parseMapFile(mapFile);
    }

    public GameScene() {
    }

    public static void main(String[] args) {
        GameWindow window = new GameWindow(new Dimension(800, 600), "Test", new GameScene());
        window.showWindow();
    }

    @Override
    public void onPrepare() {
        try {
            map = MapParser.parseMapFile(new File("map/level2.map"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        windowRatioKeeper = new WindowRatioKeeper(null, window.getContentPane(), map.getMapWidth(), map.getMapHeight(), 0);
        keyboardInputHandler = new GameKeyboardHandler(new KeyboardInput());
        mouseInputHandler = new SuperMouseInputHandler(new MouseInput());
        ((SuperMouseInputHandler) mouseInputHandler).add("map", map);

        window.setTitle(map.getMapName() + " - 推箱子"); // set title
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onExitingRendering() {

    }

    @Override
    public void beforeRendering() {
        map.setMappingTool(new GameObjectsMappingTool(map.getMapWidth(), map.getMapHeight(), canvas));
        map.updateMapScreenPos();
        drawables.put("Map", map);

        player = new Player(map.getPlayerStartPoint(), map);
        // player.setWorldToScreen(new ScreenMappingTool(map.getMapWidth(), map.getMapHeight(), canvas));
        //drawables.put("Player", player);

    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        // window.addComponentListener(new WindowRatioKeeper(canvas, window.getContentPane(), map.getMapWidth(), map.getMapHeight(), 0));
        screenMappingTool = new ScreenMappingTool(map.getMapWidth(), map.getMapHeight(), canvas);
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                screenMappingTool = new ScreenMappingTool(map.getMapWidth(), map.getMapHeight(), canvas);
                MapObject.setGameObjectsMappingTool(new GameObjectsMappingTool(map.getMapWidth(), map.getMapHeight(), canvas));
                map.updateMapScreenPos();

                if (player != null)
                    player.updateW2SVectors();

                // refreshObjects();
            }
        });
    }

    @Override
    public void afterTiming() {

    }

    @Override
    public void render(Graphics g, double delta) {
        g.setColor(Color.white);
        // CoordinateSystemShower.drawMappedWorldXAxis(g, 0, 10, 1, canvas.getHeight(), screenMappingTool, 0);
        // CoordinateSystemShower.drawMappedWorldYAxis(g, 0, 3, 1, canvas.getWidth(), screenMappingTool, 0);
        player.draw(g, delta);
    }

    private synchronized void createObjects() {

    }

    private synchronized void refreshObjects() {

    }

    class GameKeyboardHandler extends KeyboardInputHandler {
        public GameKeyboardHandler(KeyboardInput input) {
            super(input);
        }

        @Override
        public void processInput() {
            if (input.isKeyDownOnce(KeyEvent.VK_LEFT)) {
                player.move(Direction.LEFT);
            } else if (input.isKeyDownOnce(KeyEvent.VK_RIGHT)) {
                player.move(Direction.RIGHT);
            } else if (input.isKeyDownOnce(KeyEvent.VK_UP)) {
                player.move(Direction.UP);
            } else if (input.isKeyDownOnce(KeyEvent.VK_DOWN)) {
                player.move(Direction.DOWN);
            } else if (input.isKeyDownOnce(KeyEvent.VK_ESCAPE)) {
                MessageBoxPopup popup = new MessageBoxPopup(GameScene.this, new Color(0, 0, 0, 100), "确定退出",
                        EnumSet.of(Style.MBP_YESNO),
                        new String[]{
                                "是否退出游戏？", "您的游戏进度将丢失"
                        });
                popup.setYesListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                attachPopup(popup);
            }
        }
    }
}


