package sokoban.game.engine.popups;

import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.graphics.shapes.Rect;
import sokoban.game.engine.input.KeyboardInput;
import sokoban.game.engine.input.handler.KeyboardInputHandler;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.game.utils.TextDrawer;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by CodeingBoy on 2016-8-22-0022.
 */
public class MessageBoxPopup extends Popup {
    private final static int FRAMEWIDTH = 4, FRAMEHEIGHT = 3;
    private final static double FRAMEPADDINGRATIO = 0.9;
    private boolean haveAnimation = true;
    private String title, message[];
    private Rect popupFrame;
    private ScreenMappingTool screenMappingTool;

    public MessageBoxPopup(SuperScene scene, Color backgroundColor, String title, String... message) {
        super(scene, backgroundColor);
        this.title = title;
        this.message = message;

        setKeyboardInputHandler(new MsgPopupKeyboardHandler(new KeyboardInput()));

        onResized();
    }

    @Override
    public void render(Graphics g, double delta) {
        popupFrame.draw(g, delta);

        Point titlePos = screenMappingTool.worldToScreen(new Point(0, -100));
        TextDrawer.drawCenteredString(g, titlePos.x, titlePos.y,
                new Font("微软雅黑", Font.PLAIN, 50), Color.WHITE, title);

        Point msgPos = screenMappingTool.worldToScreen(new Point(0, 0));
        TextDrawer.drawCenteredString(g, msgPos.x, msgPos.y,
                new Font("微软雅黑", Font.PLAIN, 40), Color.WHITE, message);
    }

    @Override
    public void onResized() {
        screenMappingTool = new ScreenMappingTool(1, 1, scene.getCanvas());

        int ratioWidth = (int) (getWidth() * FRAMEPADDINGRATIO);
        int ratioHeight = (int) (getHeight() * FRAMEPADDINGRATIO);

        int newWidth = ratioWidth;
        int newHeight = ratioWidth * FRAMEHEIGHT / FRAMEWIDTH;
        if (newHeight > ratioHeight) {
            newWidth = ratioHeight * FRAMEWIDTH / FRAMEHEIGHT;
            newHeight = ratioHeight;
        }

        popupFrame = new Rect(new Vector2f((getWidth() - newWidth) / 2, (getHeight() - newHeight) / 2), newWidth, newHeight);
        popupFrame.setColor(Color.white);

        // popupFrame.setCenter(Matrix3x3f.translate(screenMappingTool.worldToScreen(new Point(0, 0))));

    }

    public void addListener(ActionListener actionListener) {

    }

    class MsgPopupKeyboardHandler extends KeyboardInputHandler {
        public MsgPopupKeyboardHandler(KeyboardInput input) {
            super(input);
        }

        @Override
        public void processInput() {
            if (input.isKeyDownOnce(KeyEvent.VK_ESCAPE)) {
                dispose();
            }
        }
    }
}
