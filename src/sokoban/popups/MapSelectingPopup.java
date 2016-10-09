package sokoban.popups;

import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.components.TextButton;
import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.SuperMouseInputHandler;
import sokoban.game.engine.popups.MessageBoxPopup;
import sokoban.game.engine.popups.Popup;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.game.utils.TextDrawer;
import sokoban.map.MapParser;
import sokoban.scenes.gamescene.GameScene;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;

/**
 * 游戏地图选择 Popup
 */
public class MapSelectingPopup extends Popup {
    private final static File hoverSound = new File("sound/hover.wav");
    private final static File clickSound = new File("sound/click.wav");
    private ScreenMappingTool screenMappingTool;
    private Map<String, File> maplist;
    private TextButton btnBack, btnHelp;
    private ArrayList<TextButton> mapBtns;

    /**
     * 创建一个 Popup
     *
     * @param scene 父场景
     */
    public MapSelectingPopup(SuperScene scene) {
        super(scene, new Color(0, 0, 0, 200));
        maplist = MapParser.getMapList(new File("map"));

        setMouseInputHandler(new SuperMouseInputHandler(new MouseInput()));
    }

    @Override
    public void render(Graphics g, double delta) {
        Point titlePos = new Point(getWidth() / 2, (int) (getHeight() * 0.1));
        TextDrawer.drawCenteredString(g, titlePos.x, titlePos.y,
                new Font("微软雅黑", Font.PLAIN, 50), Color.ORANGE, "请选择地图");

        btnBack.draw(g, delta);
        btnHelp.draw(g, delta);

        synchronized (mapBtns) {
            for (TextButton btn : mapBtns) {
                btn.draw(g, delta);
            }
        }
    }

    @Override
    public synchronized void refreshObjects() {
        screenMappingTool = new ScreenMappingTool(1, 1, scene.getCanvas());

        btnBack = new TextButton(new Point((int) (getWidth() * 0.8), (int) (getHeight() * 0.7)), 100, 70, null, null, null,
                new Point(0, 0), new Font("微软雅黑", Font.PLAIN, 35), Color.white, Color.cyan, Color.BLACK,
                EnumSet.of(TextButton.Style.CENTER_TEXT), "返回") {
            @Override
            public void onClick(Point p) {
                super.onClick(p);
                dispose();
            }
        };
        try {
            btnBack.setHoverSound(hoverSound);
            btnBack.setClickSound(clickSound);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((SuperMouseInputHandler) getMouseInputHandler()).add("btnBack", btnBack);

        btnHelp = new TextButton(new Point((int) (getWidth() * 0.1), (int) (getHeight() * 0.7)), 100, 70, null, null, null,
                new Point(0, 0), new Font("微软雅黑", Font.PLAIN, 35), Color.white, Color.cyan, Color.BLACK,
                EnumSet.of(TextButton.Style.CENTER_TEXT), "帮助") {
            @Override
            public void onClick(Point p) {
                MessageBoxPopup popup = new MessageBoxPopup(scene, new Color(0, 0, 0, 200),
                        EnumSet.of(MessageBoxPopup.Style.MBP_OK),
                        "游戏帮助",
                        new String[]{
                                "方向键：控制角色移动", "Esc键：调出游戏菜单"
                        });
                popup.setOKListener(new ActionListener() {
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
            btnHelp.setHoverSound(hoverSound);
            btnHelp.setClickSound(clickSound);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((SuperMouseInputHandler) getMouseInputHandler()).add("btnHelp", btnHelp);

        mapBtns = new ArrayList<>();

        synchronized (mapBtns) {
            int y = (int) (getHeight() * 0.25);
            for (Map.Entry<String, File> entry : maplist.entrySet()) {
                TextButton btn = new TextButton(true, new Point(getWidth() / 2, y), 200, 40, null, null, null,
                        new Point(0, 0), new Font("微软雅黑", Font.PLAIN, 30), Color.WHITE, Color.green, Color.WHITE,
                        EnumSet.of(TextButton.Style.CENTER_TEXT), entry.getKey()) {
                    @Override
                    public void onClick(Point p) {
                        super.onClick(p);
                        try {
                            scene.requestSwitchScene(new GameScene(entry.getValue()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                };
                try {
                    btn.setHoverSound(hoverSound);
                    btn.setClickSound(clickSound);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mapBtns.add(btn);
                ((SuperMouseInputHandler) getMouseInputHandler()).add(entry.getKey(), btn);

                y += 50;
            }
        }
    }
}
