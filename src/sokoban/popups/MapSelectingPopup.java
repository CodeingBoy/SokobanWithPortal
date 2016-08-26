package sokoban.popups;

import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.popups.Popup;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.game.utils.TextDrawer;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-26-0026.
 */
public class MapSelectingPopup extends Popup {
    private ScreenMappingTool screenMappingTool;

    /**
     * 创建一个 Popup
     *
     * @param scene 父场景
     */
    public MapSelectingPopup(SuperScene scene) {
        super(scene, new Color(255, 255, 255, 150));
    }

    @Override
    public void render(Graphics g, double delta) {
        Point titlePos = screenMappingTool.worldToScreen(new Point(0, -500));
        TextDrawer.drawCenteredString(g, titlePos.x, titlePos.y,
                new Font("微软雅黑", Font.PLAIN, 50), Color.WHITE, "请选择地图");
    }

    @Override
    public void refreshObjects() {
        screenMappingTool = new ScreenMappingTool(1, 1, scene.getCanvas());
    }
}
