package sokoban.game.engine.popups;

import sokoban.game.engine.graphics.ScreenMappingTool;
import sokoban.game.engine.graphics.Vector2f;
import sokoban.game.engine.graphics.components.Button;
import sokoban.game.engine.graphics.shapes.Rect;
import sokoban.game.engine.input.KeyboardInput;
import sokoban.game.engine.input.MouseInput;
import sokoban.game.engine.input.handler.KeyboardInputHandler;
import sokoban.game.engine.input.handler.SuperMouseInputHandler;
import sokoban.game.engine.scenes.SuperScene;
import sokoban.game.utils.TextDrawer;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Set;

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
    private SuperMouseInputHandler superMouseInputHandler = new SuperMouseInputHandler(new MouseInput());
    private Set<Style> styles;
    private Button btnOK, btnYes, btnNo;
    private ActionListener btnYesListener, btnNoListener, btnOKListener;

    /**
     * @param scene
     * @param backgroundColor
     * @param title
     * @param styles          欲设置多个风格属性，请使用 EnumSet.of 方法传入多个风格属性
     * @param message
     */
    public MessageBoxPopup(SuperScene scene, Color backgroundColor, String title, Set<Style> styles, String... message) {
        super(scene, backgroundColor);
        this.title = title;
        this.message = message;
        this.styles = styles;

        setKeyboardInputHandler(new MsgPopupKeyboardHandler(new KeyboardInput()));
        setMouseInputHandler(superMouseInputHandler);
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
    public void refreshObjects() {
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

        if (styles.contains(Style.MBP_OK)) {
            if (drawables.get("btnOK") != null) {
                superMouseInputHandler.remove((OKButton) drawables.get("btnOK"));
            }

            btnOK = new OKButton(screenMappingTool.worldToScreen(new Point(-100, 120)));
            ((OKButton) btnOK).setListener(btnOKListener);

            drawables.put("btnOK", btnOK);
            superMouseInputHandler.add("btnOK", btnOK);
        } else if (styles.contains(Style.MBP_YESNO)) {
            if (drawables.get("btnYes") != null) {
                superMouseInputHandler.remove((YesButton) drawables.get("btnYes"));
            }

            if (drawables.get("btnNo") != null) {
                superMouseInputHandler.remove((NoButton) drawables.get("btnNo"));
            }

            btnYes = new YesButton(screenMappingTool.worldToScreen(new Point(-200, 120)));
            ((YesButton) btnYes).setListener(btnYesListener);

            btnNo = new NoButton(screenMappingTool.worldToScreen(new Point(0, 120)));
            ((NoButton) btnNo).setListener(btnNoListener);

            drawables.put("btnYes", btnYes);
            drawables.put("btnNo", btnNo);
            superMouseInputHandler.add("btnYes", btnYes);
            superMouseInputHandler.add("btnNo", btnNo);
        }

        // popupFrame.setCenter(Matrix3x3f.translate(screenMappingTool.worldToScreen(new Point(0, 0))));

    }

    public void setYesListener(ActionListener actionListener) {
        btnYesListener = actionListener;
    }

    public void setNoListener(ActionListener actionListener) {
        btnNoListener = actionListener;
    }

    public void setOKListener(ActionListener actionListener) {
        btnOKListener = actionListener;
    }

    public enum Style {
        MBP_OK, MBP_YESNO, MBP_ICONERROR, MBP_ICONCHECK, MBP_ICONCROSS, MBP_ICONQUESTION, MBP_ICONINFORMATION
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

class OKButton extends Button {
    private final static Image OK = Toolkit.getDefaultToolkit().getImage("pic/buttons/popups/OK.png");
    private final static Image OK_HOVER = Toolkit.getDefaultToolkit().getImage("pic/buttons/popups/OK_hover.png");
    private ActionListener listener = null;

    public OKButton(Point start) {
        super(start, null, OK, OK_HOVER, OK);
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(Point p) {
        super.onClick(p);
        if (listener != null) listener.actionPerformed(null);
    }
}

class YesButton extends Button {
    private final static Image YES = Toolkit.getDefaultToolkit().getImage("pic/buttons/popups/Yes.png");
    private final static Image YES_HOVER = Toolkit.getDefaultToolkit().getImage("pic/buttons/popups/Yes_hover.png");
    private ActionListener listener = null;


    public YesButton(Point start) {
        super(start, null, YES, YES_HOVER, YES);
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(Point p) {
        super.onClick(p);
        if (listener != null) listener.actionPerformed(null);
    }
}

class NoButton extends Button {
    private final static Image NO = Toolkit.getDefaultToolkit().getImage("pic/buttons/popups/No.png");
    private final static Image NO_HOVER = Toolkit.getDefaultToolkit().getImage("pic/buttons/popups/No_hover.png");
    private ActionListener listener = null;

    public NoButton(Point start) {
        super(start, null, NO, NO_HOVER, NO);
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(Point p) {
        super.onClick(p);
        if (listener != null) listener.actionPerformed(null);
    }
}
