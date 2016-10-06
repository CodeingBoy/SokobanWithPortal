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
import sokoban.popups.PausePopup;
import sokoban.scenes.mainmenu.MainMenu;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.EnumSet;

/**
 * Created by CodeingBoy on 2016-8-4-0004.
 */
public class GameScene extends SuperScene {
    Player player;
    private ScreenMappingTool screenMappingTool;
    private GameMap map;
    private boolean completedHinted = false;

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
        map.setCompleteListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (completedHinted) return;
                MessageBoxPopup popup = new MessageBoxPopup(GameScene.this, new Color(0, 0, 0, 100), EnumSet.of(MessageBoxPopup.Style.MBP_YESNO),
                        "闯关成功！", new String[]{
                        "恭喜，闯关成功！", "是否回到主界面？"
                });
                popup.setYesListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        requestSwitchScene(new MainMenu());
                    }
                });
                popup.setNoListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        detachPopup();
                    }
                });

                attachPopup(popup);
                completedHinted = true;
            }
        });

        windowRatioKeeper = new WindowRatioKeeper(null, window.getContentPane(), map.getMapWidth(), map.getMapHeight(), 0);
        setKeyboardInputHandler(new GameKeyboardHandler(new KeyboardInput()));
        setMouseInputHandler(new SuperMouseInputHandler(new MouseInput()));
        ((SuperMouseInputHandler) getMouseInputHandler()).add("map", map);

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
        // resize canvas
        window.getContentPane().setBackground(Color.GRAY);
        windowRatioKeeper.componentResized(null);

        canvas.requestFocus(); // set focus to canvas so user can input

        // set map mapping
        map.setMappingTool(new GameObjectsMappingTool(map.getMapWidth(), map.getMapHeight(), canvas));
        map.updateMapScreenPos();
        drawables.put("Map", map); // put map into drawables

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
                PausePopup popup = new PausePopup(GameScene.this);
                attachPopup(popup);
            }
        }
    }
}


