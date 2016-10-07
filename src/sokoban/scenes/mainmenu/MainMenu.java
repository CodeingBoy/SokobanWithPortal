package sokoban.scenes.mainmenu;

import sokoban.dialog.SettingDialog;
import sokoban.game.engine.GameWindow;
import sokoban.game.engine.graphics.Matrix3x3f;
import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.graphics.components.Button;
import sokoban.game.engine.graphics.shapes.Square;
import sokoban.game.engine.input.KeyboardInput;
import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.Clickable;
import sokoban.game.engine.input.handler.SuperKeyboardInputHandler;
import sokoban.game.engine.input.handler.SuperMouseInputHandler;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.game.engine.sound.SoundManager;
import sokoban.popups.MapSelectingPopup;
import sokoban.scenes.testscene.TestScene;
import sokoban.utils.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public final class MainMenu extends SuperScene {
    private static final int WORLD_WIDTH = 100;
    private static final int WORLD_HEIGHT = 100;
    private final static File hoverSound = new File("sound/hover.wav");
    private final static File clickSound = new File("sound/click.wav");
    private final Image banner = new ImageIcon("banner.png").getImage();
    private final Image start = new ImageIcon("start.png").getImage();
    private final Image start_hover = new ImageIcon("start_hover.png").getImage();
    private final Image exit = new ImageIcon("exit.png").getImage();
    private final Image exit_hover = new ImageIcon("exit_hover.png").getImage();
    private final Image test = new ImageIcon("pic/buttons/test/test.png").getImage();
    private final Image test_hover = new ImageIcon("pic/buttons/test/test_hover.png").getImage();
    private final Image setting = new ImageIcon("pic/buttons/setting/setting.png").getImage();
    private final Image setting_hover = new ImageIcon("pic/buttons/setting/setting_hover.png").getImage();
    private ScreenMappingTool screenMappingTool;

    public MainMenu() {
    }

    public static void main(String[] args) {
        GameWindow window = new GameWindow(new Dimension(800, 600), "游戏窗口", new MainMenu());
        window.showWindow();
    }

    @Override
    public void beforeRendering() {
        screenMappingTool = new ScreenMappingTool(WORLD_WIDTH, WORLD_HEIGHT, canvas);
        createObjects();
        refreshObjects();
    }

    private synchronized void createObjects() {
        int width = (int) (Math.min(getHeight(), getWidth()) * 0.8);
        Square bgRect = new Square(new Vector2f(-width / 2, -width / 2), width);
        bgRect.setRotateSpeed(Math.toRadians(90));
        Point center = screenMappingTool.worldToScreen(new Point(0, 0));
        bgRect.setCenter(Matrix3x3f.translate(center.x, center.y));
        drawables.put("bgRect", bgRect);

        ((SuperMouseInputHandler) getMouseInputHandler()).add("bgRect", new Clickable() {
            @Override
            public boolean isPointInside(Point point) {
                return bgRect.isPointInside(point);
            }

            @Override
            public void onClick(Point p) {

            }

            @Override
            public void onHover(Point p) {
                bgRect.setRotateSpeed(bgRect.getRotateSpeed() + Math.toRadians(1));
            }

            @Override
            public void onEnteringHover(Point p) {

            }

            @Override
            public void onExitingHover(Point p) {

            }
        });
    }

    private synchronized void refreshObjects() {
        Square bgRect = (Square) drawables.get("bgRect");

        int width = (int) (Math.min(getHeight(), getWidth()) * 0.8);
        if (bgRect == null) {
            bgRect = new Square(new Vector2f(-width / 2, -width / 2), width);
            bgRect.setRotateSpeed(Math.toRadians(90));
        }

        bgRect.setOriginVectors(new Vector2f(-width / 2, -width / 2), width);
        bgRect.setColor(Color.white);
        // bgRect.setWorldToScreen(screenMappingTool);
        Point center = screenMappingTool.worldToScreen(new Point(0, 0));
        bgRect.setCenter(Matrix3x3f.translate(center.x, center.y));
        drawables.put("bgRect", bgRect);

        Square finalBgRect = bgRect;
        ((SuperMouseInputHandler) getMouseInputHandler()).add("bgRect", new Clickable() {
            @Override
            public boolean isPointInside(Point point) {
                return finalBgRect.isPointInside(point);
            }

            @Override
            public void onClick(Point p) {

            }

            @Override
            public void onHover(Point p) {
                finalBgRect.setRotateSpeed(finalBgRect.getRotateSpeed() + Math.toRadians(0.1));
            }

            @Override
            public void onEnteringHover(Point p) {

            }

            @Override
            public void onExitingHover(Point p) {

            }
        });


        Button btnStart =
                new Button(screenMappingTool.worldToScreen(new Point(0 - start.getWidth(null) / 2, 0)),
                        null, start, start_hover, start) {
                    @Override
                    public void onClick(Point p) {
                        super.onClick(p);
                        //requestSwitchScene(new GameScene());
                        attachPopup(new MapSelectingPopup(MainMenu.this));
                    }
                };
        try {
            btnStart.setHoverSound(hoverSound);
            btnStart.setClickSound(clickSound);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((SuperMouseInputHandler) getMouseInputHandler()).add("btnStart", btnStart, btnStart);
        drawables.put("btnStart", btnStart);

        Button btnTest =
                new Button(screenMappingTool.worldToScreen(new Point(0 - test.getWidth(null) / 2,
                        start.getHeight(null))),
                        null, test, test_hover, test) {
                    @Override
                    public void onClick(Point p) {
                        super.onClick(p);
                        requestSwitchScene(new TestScene());
                    }
                };
        try {
            btnTest.setHoverSound(hoverSound);
            btnTest.setClickSound(clickSound);
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawables.put("btnTest", btnTest);
        ((SuperMouseInputHandler) getMouseInputHandler()).add("btnTest", btnTest, btnTest);

        Button btnSetting =
                new Button(screenMappingTool.worldToScreen(new Point(0 - setting.getWidth(null) / 2,
                        start.getHeight(null) + test.getHeight(null))),
                        null, setting, setting_hover, setting) {
                    @Override
                    public void onClick(Point p) {
                        super.onClick(p);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                SettingDialog settingDialog = new SettingDialog(window);
                                settingDialog.setVisible(true);
                            }
                        });
                    }
                };
        try {
            btnSetting.setHoverSound(hoverSound);
            btnSetting.setClickSound(clickSound);
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawables.put("btnSetting", btnSetting);
        ((SuperMouseInputHandler) getMouseInputHandler()).add("btnSetting", btnSetting, btnSetting);


        Button btnExit =
                new Button(screenMappingTool.worldToScreen(new Point(0 - exit.getWidth(null) / 2,
                        start.getHeight(null) + test.getHeight(null) + setting.getHeight(null))),
                        null, exit, exit_hover, exit) {
                    @Override
                    public void onClick(Point p) {
                        super.onClick(p);
                        window.close();
                    }
                };
        try {
            btnExit.setHoverSound(hoverSound);
            btnExit.setClickSound(clickSound);
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawables.put("btnExit", btnExit);
        ((SuperMouseInputHandler) getMouseInputHandler()).add("btnExit", btnExit, btnExit);
    }

    @Override
    public void afterTiming() {
    }

    @Override
    public void render(Graphics g, double delta) {

        // for debug
        // CoordinateSystemShower.drawWorldXAxis(g, 0, getHeight(), 50, canvas.getHeight(), screenMappingTool, 0);
        // CoordinateSystemShower.drawWorldYAxis(g, 0, getWidth(), 50, canvas.getWidth(), screenMappingTool, 0);

        // draw banner
        Point p = screenMappingTool.worldToScreen(new Point(0 - banner.getWidth(null) / 2, 0));
        g.drawImage(banner, p.x, 0, null);
    }

    @Override
    public void onPrepare() {
        window.setLocationRelativeTo(null);
        window.setMinimumSize(new Dimension(550, 550));

        setKeyboardInputHandler(new SuperKeyboardInputHandler(new KeyboardInput()));
        setMouseInputHandler(new SuperMouseInputHandler(new MouseInput()));

        window.setTitle("推箱子");

        SoundManager soundManager = SoundManager.getInstance();
        soundManager.addClip("bg", new File("sound/bg.wav"));
        soundManager.setClipVolume("bg", Settings.getBGMVolume());
        soundManager.loopClip("bg");
    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                screenMappingTool = new ScreenMappingTool(WORLD_WIDTH, WORLD_HEIGHT, canvas);
                refreshObjects();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        window.setMinimumSize(null);
    }

    @Override
    public void onExitingRendering() {

    }
}