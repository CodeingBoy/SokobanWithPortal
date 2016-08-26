package sokoban.popups;

import sokoban.dialog.SettingDialog;
import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.components.Button;
import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.SuperMouseInputHandler;
import sokoban.game.engine.popups.Popup;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.scenes.mainmenu.MainMenu;

import java.awt.*;

/**
 * 游戏中暂停画面的Popup
 */
public class PausePopup extends Popup {
    private final static Image IMG_BACK = Toolkit.getDefaultToolkit().getImage("pic/buttons/pause/back.png");
    private final static Image IMG_BACK_HOVER = Toolkit.getDefaultToolkit().getImage("pic/buttons/pause/back_hover.png");
    private final static Image IMG_SETTINGS = Toolkit.getDefaultToolkit().getImage("pic/buttons/pause/setting.png");
    private final static Image IMG_SETTINGS_HOVER = Toolkit.getDefaultToolkit().getImage("pic/buttons/pause/setting_hover.png");
    private final static Image IMG_MAINMENU = Toolkit.getDefaultToolkit().getImage("pic/buttons/pause/mainmenu.png");
    private final static Image IMG_MAINMENU_HOVER = Toolkit.getDefaultToolkit().getImage("pic/buttons/pause/mainmenu_hover.png");
    private final static Image IMG_EXIT = Toolkit.getDefaultToolkit().getImage("pic/buttons/pause/exit.png");
    private final static Image IMG_EXIT_HOVER = Toolkit.getDefaultToolkit().getImage("pic/buttons/pause/exit_hover.png");


    private SuperMouseInputHandler superMouseInputHandler = new SuperMouseInputHandler(new MouseInput());
    private Button btnBack, btnSetting, btnMainmenu, btnExit;
    private ScreenMappingTool screenMappingTool;

    /**
     * 创建一个 Popup
     *
     * @param scene 父场景
     */
    public PausePopup(SuperScene scene) {
        super(scene, new Color(0, 0, 0, 150));
        setMouseInputHandler(superMouseInputHandler);

    }

    @Override
    public void render(Graphics g, double delta) {

    }

    @Override
    public void refreshObjects() {
        screenMappingTool = new ScreenMappingTool(2, 5, scene.getCanvas());

        int yStart = (int) (getWidth() * 0.2);

        btnBack = new Button(new Point((getWidth() - IMG_BACK.getWidth(null)) / 2, yStart),
                null, IMG_BACK, IMG_BACK_HOVER, IMG_BACK_HOVER) {
            @Override
            public void onClick(Point p) {
                super.onClick(p);
                dispose();
            }

            @Override
            public void onHover(Point p) {
                super.onHover(p);
            }
        };

        drawables.put("btnBack", btnBack);
        superMouseInputHandler.add("btnBack", btnBack);

        yStart = (int) (getWidth() * 0.4);

        btnSetting = new Button(new Point((getWidth() - IMG_BACK.getWidth(null)) / 2, yStart),
                null, IMG_SETTINGS, IMG_SETTINGS_HOVER, IMG_SETTINGS_HOVER) {
            @Override
            public void onClick(Point p) {
                super.onClick(p);
                new SettingDialog(scene.getWindow()).setVisible(true);
            }
        };

        drawables.put("btnSetting", btnSetting);
        superMouseInputHandler.add("btnSetting", btnSetting);

        yStart = (int) (getWidth() * 0.6);

        btnMainmenu = new Button(new Point((getWidth() - IMG_BACK.getWidth(null)) / 2, yStart),
                null, IMG_MAINMENU, IMG_MAINMENU_HOVER, IMG_MAINMENU_HOVER) {
            @Override
            public void onClick(Point p) {
                super.onClick(p);
                scene.requestSwitchScene(new MainMenu());
            }
        };

        drawables.put("btnMainmenu", btnMainmenu);
        superMouseInputHandler.add("btnMainmenu", btnMainmenu);

        yStart = (int) (getWidth() * 0.8);

        btnExit = new Button(new Point((getWidth() - IMG_BACK.getWidth(null)) / 2, yStart),
                null, IMG_EXIT, IMG_EXIT_HOVER, IMG_EXIT_HOVER) {
            @Override
            public void onClick(Point p) {
                super.onClick(p);
                System.exit(1);
            }
        };

        drawables.put("btnExit", btnExit);
        superMouseInputHandler.add("btnExit", btnExit);
    }
}
