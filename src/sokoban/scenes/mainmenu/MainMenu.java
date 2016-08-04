package sokoban.scenes.mainmenu;

import sokoban.dialog.GameDialog;
import sokoban.game.engine.GameWindow;
import sokoban.game.engine.graphics.Matrix3x3f;
import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.graphics.components.Button;
import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.game.engine.graphics.shapes.Square;
import sokoban.game.engine.input.KeyboardInput;
import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.Clickable;
import sokoban.game.engine.scenes.EmptyScene;
import sokoban.game.engine.scenes.FrameRateScene;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.scenes.mainmenu.inputhandler.KeyboardHandler;
import sokoban.scenes.mainmenu.inputhandler.MouseHandler;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public final class MainMenu extends SuperScene {
    private static final int WORLD_WIDTH = 100;
    private static final int WORLD_HEIGHT = 100;
    private final Image banner = Toolkit.getDefaultToolkit().getImage("banner.png");
    private final Image start = Toolkit.getDefaultToolkit().getImage("start.png");
    private final Image start_hover = Toolkit.getDefaultToolkit().getImage("start_hover.png");
    private SuperScene superScene;
    private ScreenMappingTool screenMappingTool;
    private Map<String, Drawable> drawables = new HashMap<>();

    public MainMenu() {
    }

    public MainMenu(SuperScene superScene) {
        this.superScene = superScene;
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(new FrameRateScene(new EmptyScene(), 50, 50));
        mainMenu.setKeyboardInputHandler(new KeyboardHandler(new KeyboardInput()));
        mainMenu.setMouseInputHandler(new MouseHandler(new MouseInput()));
        GameWindow window = new GameWindow(new Dimension(800, 600), "游戏窗口", mainMenu);
        window.showWindow();
    }

    @Override
    public void beforeRendering() {
        superScene.beforeRendering();
        createObjects();
    }

    private void createObjects() {
        int width = (int) (Math.min(getHeight(), getWidth()) * 0.8);
        Square bgRect = new Square(new Vector2f(-width / 2, -width / 2), width);
        bgRect.setRotateSpeed(Math.toRadians(90));
        Point center = screenMappingTool.worldToScreen(new Point(0, 0));
        bgRect.setCenter(Matrix3x3f.translate(center.x, center.y));
        drawables.put("bgRect", bgRect);

        mouseInputHandler.add("bgRect", bgRect, new Clickable() {
            @Override
            public void onClick(Point p) {

            }

            @Override
            public void onHover(Point p) {
                bgRect.setRotateSpeed(bgRect.getRotateSpeed() + Math.toRadians(1));
            }
        });
    }

    private void refreshObjects() {
        Square bgRect = (Square) drawables.get("bgRect");

        int width = (int) (Math.min(getHeight(), getWidth()) * 0.8);
        if (bgRect == null) {
            bgRect = new Square(new Vector2f(-width / 2, -width / 2), width);
            bgRect.setRotateSpeed(Math.toRadians(90));
        }

        bgRect.setOriginVectors(new Vector2f(-width / 2, -width / 2), width);
        Point center = screenMappingTool.worldToScreen(new Point(0, 0));
        bgRect.setCenter(Matrix3x3f.translate(center.x, center.y));
        drawables.put("bgRect", bgRect);

        Square finalBgRect = bgRect;
        mouseInputHandler.add("bgRect", bgRect, new Clickable() {
            @Override
            public void onClick(Point p) {

            }

            @Override
            public void onHover(Point p) {
                finalBgRect.setRotateSpeed(finalBgRect.getRotateSpeed() + Math.toRadians(0.1));
            }
        });


        Button btnStart =
                new Button(screenMappingTool.worldToScreen(new Point(0 - start.getWidth(null) / 2 - 50, 0)),
                        null, start, start_hover, start) {
                    @Override
                    public void onClick(Point p) {
                        super.onClick(p);
                        System.out.println("clicked");
                        requestSwitchScene(new GameDialog(new FrameRateScene(new EmptyScene(), 50, 50, Color.white, true)));
                    }

                    @Override
                    public void onHover(Point p) {
                        super.onHover(p);
                        // System.out.println("hovering");
                    }
                };
        drawables.put("btnStart", btnStart);
        mouseInputHandler.add("btnStart", btnStart, btnStart);
    }

    @Override
    public void afterTiming() {
        superScene.afterTiming();
    }

    @Override
    public void render(Graphics g, double delta) {
        superScene.render(g, delta);

        // for debug
        // CoordinateSystemShower.drawWorldXAxis(g, 0, getHeight(), 50, canvas.getHeight(), screenMappingTool, 0);
        // CoordinateSystemShower.drawWorldYAxis(g, 0, getWidth(), 50, canvas.getWidth(), screenMappingTool, 0);

        // draw banner
        Point p = screenMappingTool.worldToScreen(new Point(0 - banner.getWidth(null) / 2, 0));
        g.drawImage(banner, p.x, 0, null);

        renderDrawables(g, delta);
    }

    private void renderDrawables(Graphics g, double delta) {
        for (Drawable d : drawables.values()) {
            d.draw(g, delta);
        }
    }

    @Override
    public void onPrepare() {
        window.setLocationRelativeTo(null);
    }

    @Override
    public void onInitialize() {
        canvas = new Canvas();
        canvas.setBackground(Color.black);

        screenMappingTool = new ScreenMappingTool(WORLD_WIDTH, WORLD_HEIGHT, canvas);

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                screenMappingTool = new ScreenMappingTool(WORLD_WIDTH, WORLD_HEIGHT, canvas);
                refreshObjects();
            }
        });
        superScene.onInitialize();
        super.onInitialize();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onExitingRendering() {

    }
}
