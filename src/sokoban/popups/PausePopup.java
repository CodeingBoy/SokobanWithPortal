package sokoban.popups;

import sokoban.dialog.SettingDialog;
import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.components.Button;
import sokoban.game.engine.input.KeyboardInput;
import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.Enterable;
import sokoban.game.engine.input.handler.SuperKeyboardInputHandler;
import sokoban.game.engine.input.handler.SuperMouseInputHandler;
import sokoban.game.engine.popups.MessageBoxPopup;
import sokoban.game.engine.popups.Popup;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.scenes.mainmenu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 * 游戏中暂停画面的 Popup
 */
public class PausePopup extends Popup {
    private final static Image IMG_BACK = new ImageIcon("pic/buttons/pause/back.png").getImage();
    private final static Image IMG_BACK_HOVER = new ImageIcon("pic/buttons/pause/back_hover.png").getImage();
    private final static Image IMG_SETTINGS = new ImageIcon("pic/buttons/pause/setting.png").getImage();
    private final static Image IMG_SETTINGS_HOVER = new ImageIcon("pic/buttons/pause/setting_hover.png").getImage();
    private final static Image IMG_MAINMENU = new ImageIcon("pic/buttons/pause/mainmenu.png").getImage();
    private final static Image IMG_MAINMENU_HOVER = new ImageIcon("pic/buttons/pause/mainmenu_hover.png").getImage();
    private final static Image IMG_EXIT = new ImageIcon("pic/buttons/pause/exit.png").getImage();
    private final static Image IMG_EXIT_HOVER = new ImageIcon("pic/buttons/pause/exit_hover.png").getImage();

    private final static File hoverSound = new File("sound/hover.wav");
    private final static File clickSound = new File("sound/click.wav");

    private SuperMouseInputHandler superMouseInputHandler = new SuperMouseInputHandler(new MouseInput());
    private SuperKeyboardInputHandler superKeyboardInputHandler = new SuperKeyboardInputHandler(new KeyboardInput());
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
        setKeyboardInputHandler(superKeyboardInputHandler);
    }

    @Override
    public void render(Graphics g, double delta) {

    }

    @Override
    public void refreshObjects() {
        screenMappingTool = new ScreenMappingTool(2, 5, scene.getCanvas());

        int yStart = (int) (getHeight() * 0.2);

        btnBack = new Button(new Point((getWidth() - IMG_BACK.getWidth(null)) / 2, yStart),
                IMG_BACK, IMG_BACK_HOVER, IMG_BACK_HOVER) {
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
        try {
            btnBack.setHoverSound(hoverSound);
            btnBack.setClickSound(clickSound);
        } catch (IOException e) {
            e.printStackTrace();
        }

        drawables.put("btnBack", btnBack);
        superMouseInputHandler.add("btnBack", btnBack);

        yStart = (int) (getHeight() * 0.4);

        btnSetting = new Button(new Point((getWidth() - IMG_BACK.getWidth(null)) / 2, yStart),
                IMG_SETTINGS, IMG_SETTINGS_HOVER, IMG_SETTINGS_HOVER) {
            @Override
            public void onClick(Point p) {
                super.onClick(p);
                new SettingDialog(scene.getWindow()).setVisible(true);
            }
        };
        try {
            btnSetting.setHoverSound(hoverSound);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            btnSetting.setHoverSound(hoverSound);
            btnSetting.setClickSound(clickSound);
        } catch (IOException e) {
            e.printStackTrace();
        }

        drawables.put("btnSetting", btnSetting);
        superMouseInputHandler.add("btnSetting", btnSetting);

        yStart = (int) (getHeight() * 0.6);

        btnMainmenu = new Button(new Point((getWidth() - IMG_BACK.getWidth(null)) / 2, yStart),
                IMG_MAINMENU, IMG_MAINMENU_HOVER, IMG_MAINMENU_HOVER) {
            @Override
            public void onClick(Point p) {
                super.onClick(p);

                MessageBoxPopup popup = new MessageBoxPopup(scene, new Color(0, 0, 0, 200),
                        EnumSet.of(MessageBoxPopup.Style.MBP_YESNO),
                        "确定退出",
                        new String[]{
                                "确认要返回主画面吗？", "您的游戏进度将丢失"
                        });
                popup.setYesListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        scene.requestSwitchScene(new MainMenu());
                    }
                });
                popup.setNoListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        detachPopup();
                    }
                });
                popup.setHoverSound(hoverSound);
                popup.setClickSound(clickSound);

                attachPopup(popup);
            }
        };
        try {
            btnMainmenu.setHoverSound(hoverSound);
            btnMainmenu.setClickSound(clickSound);
        } catch (IOException e) {
            e.printStackTrace();
        }

        drawables.put("btnMainmenu", btnMainmenu);
        superMouseInputHandler.add("btnMainmenu", btnMainmenu);

        yStart = (int) (getHeight() * 0.8);

        btnExit = new Button(new Point((getWidth() - IMG_BACK.getWidth(null)) / 2, yStart),
                IMG_EXIT, IMG_EXIT_HOVER, IMG_EXIT_HOVER) {
            @Override
            public void onClick(Point p) {
                super.onClick(p);

                MessageBoxPopup popup = new MessageBoxPopup(scene, new Color(0, 0, 0, 200),
                        EnumSet.of(MessageBoxPopup.Style.MBP_YESNO),
                        "确定退出",
                        new String[]{
                                "是否退出游戏？", "您的游戏进度将丢失"
                        });
                popup.setYesListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(1);
                    }
                });
                popup.setNoListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        detachPopup();
                    }
                });
                popup.setHoverSound(new File("sound/hover.wav"));
                popup.setClickSound(new File("sound/click.wav"));

                attachPopup(popup);
            }
        };
        try {
            btnExit.setHoverSound(hoverSound);
            btnExit.setClickSound(clickSound);
        } catch (IOException e) {
            e.printStackTrace();
        }

        drawables.put("btnExit", btnExit);
        superMouseInputHandler.add("btnExit", btnExit);

        superKeyboardInputHandler.add(KeyEvent.VK_ESCAPE, new Enterable() {
            @Override
            public void onEnter() {
                dispose();
            }
        });
    }


}
