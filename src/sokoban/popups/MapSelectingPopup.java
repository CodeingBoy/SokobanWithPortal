package sokoban.popups;

import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.components.TextButton;
import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.SuperMouseInputHandler;
import sokoban.game.engine.popups.Popup;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.game.utils.TextDrawer;
import sokoban.map.MapParser;
import sokoban.scenes.gamescene.GameScene;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by CodeingBoy on 2016-8-26-0026.
 */
public class MapSelectingPopup extends Popup {
    private ScreenMappingTool screenMappingTool;
    private Map<String, File> maplist;
    private TextButton btnBack;
    private ArrayList<TextButton> mapBtns;

    /**
     * 创建一个 Popup
     *
     * @param scene 父场景
     */
    public MapSelectingPopup(SuperScene scene) {
        super(scene, new Color(255, 255, 255, 150));
        maplist = MapParser.getMapList(new File("map"));

        setMouseInputHandler(new SuperMouseInputHandler(new MouseInput()));
    }

    @Override
    public void render(Graphics g, double delta) {
        Point titlePos = new Point(getWidth() / 2, (int) (getHeight() * 0.1));
        TextDrawer.drawCenteredString(g, titlePos.x, titlePos.y,
                new Font("微软雅黑", Font.PLAIN, 50), Color.ORANGE, "请选择地图");

        btnBack.draw(g, delta);

        for (TextButton btn : mapBtns) {
            btn.draw(g, delta);
        }
    }

    @Override
    public void refreshObjects() {
        screenMappingTool = new ScreenMappingTool(1, 1, scene.getCanvas());

        btnBack = new TextButton(new Point((int) (getWidth() * 0.5), (int) (getHeight() * 0.7)), 500, 70, null, null, null,
                new Point(250, 35), new Font("微软雅黑", Font.PLAIN, 35), Color.white, Color.cyan, Color.BLACK, "返回") {
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
        ((SuperMouseInputHandler) getMouseInputHandler()).add("btnBack", btnBack);

        mapBtns = new ArrayList<>();

        int y = (int) (getHeight() * 0.2);
        for (Map.Entry<String, File> entry : maplist.entrySet()) {
            TextButton btn = new TextButton(new Point(getWidth() / 2 - 150, y), 100, 70, null, null, null,
                    new Point(50, 35), new Font("微软雅黑", Font.PLAIN, 30), Color.white, Color.BLACK, Color.BLACK, entry.getKey()) {
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
            mapBtns.add(btn);
            ((SuperMouseInputHandler) getMouseInputHandler()).add(entry.getKey(), btn);

            y += 70;
        }
    }
}
