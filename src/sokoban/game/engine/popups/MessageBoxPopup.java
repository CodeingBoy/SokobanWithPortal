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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Set;

/**
 * 消息确认弹框 Popup，常用于提示用户信息
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
    private ActionListener btnYesListener;
    private ActionListener btnNoListener;
    private ActionListener btnOKListener;
    private ActionListener dismissListener;
    private boolean canDismiss = true;

    /**
     * 构建一个消息确认弹框 Popup
     *
     * @param scene           父场景
     * @param backgroundColor 背景颜色
     * @param title           标题
     * @param styles          风格属性设置。欲设置多个风格属性，请使用 EnumSet.of 方法传入多个风格属性
     * @param message         消息
     */
    @Deprecated
    public MessageBoxPopup(SuperScene scene, Color backgroundColor, String title, Set<Style> styles, String... message) {
        super(scene, backgroundColor);
        this.title = title;
        this.message = message;
        this.styles = styles;

        setKeyboardInputHandler(new MsgPopupKeyboardHandler(new KeyboardInput()));
        setMouseInputHandler(superMouseInputHandler);
    }

    /**
     * 构建一个消息确认弹框 Popup
     *
     * @param scene           父场景
     * @param backgroundColor 背景颜色
     * @param styles          风格属性设置。欲设置多个风格属性，请使用 EnumSet.of 方法传入多个风格属性
     * @param title           标题
     * @param message         消息
     */
    public MessageBoxPopup(SuperScene scene, Color backgroundColor, Set<Style> styles, String title, String... message) {
        super(scene, backgroundColor);
        this.title = title;
        this.message = message;
        this.styles = styles;

        setKeyboardInputHandler(new MsgPopupKeyboardHandler(new KeyboardInput()));
        setMouseInputHandler(superMouseInputHandler);
    }

    /**
     * 设置按下 Esc 时，调用的动作监听器
     *
     * @param dismissListener 欲被调用的监听器
     */
    public void setDismissListener(ActionListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    /**
     * 设置是否可以用 Esc 关闭 Popup<br/>
     *
     * @param canDismiss 是否可以用 Esc 关闭 Popup
     */
    public void setCanDismiss(boolean canDismiss) {
        this.canDismiss = canDismiss;
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

    /**
     * 设置点击“是”按钮时，调用的动作监听器
     *
     * @param actionListener 欲被调用的监听器
     */
    public void setYesListener(ActionListener actionListener) {
        btnYesListener = actionListener;
    }

    /**
     * 设置点击“否”按钮时，调用的动作监听器
     *
     * @param actionListener 欲被调用的监听器
     */
    public void setNoListener(ActionListener actionListener) {
        btnNoListener = actionListener;
    }

    /**
     * 设置点击“好的”按钮时，调用的动作监听器
     *
     * @param actionListener 欲被调用的监听器
     */
    public void setOKListener(ActionListener actionListener) {
        btnOKListener = actionListener;
    }

    public enum Style {
        MBP_OK, MBP_YESNO, MBP_ICONERROR, MBP_ICONCHECK, MBP_ICONCROSS, MBP_ICONQUESTION, MBP_ICONINFORMATION
    }

    /**
     * 消息弹框 Popup 专用键盘处理器
     */
    class MsgPopupKeyboardHandler extends KeyboardInputHandler {
        public MsgPopupKeyboardHandler(KeyboardInput input) {
            super(input);
        }

        @Override
        public void processInput() {
            if (input.isKeyDownOnce(KeyEvent.VK_ESCAPE) && canDismiss) {
                if (dismissListener != null) dismissListener.actionPerformed(null);
                dispose();
            }
        }
    }
}

/**
 * “好的”按钮定义
 */
class OKButton extends Button {
    private final static Image OK = new ImageIcon("pic/buttons/popups/OK.png").getImage();
    private final static Image OK_HOVER = new ImageIcon("pic/buttons/popups/OK_hover.png").getImage();
    private ActionListener listener = null;

    public OKButton(Point start) {
        super(start, OK, OK_HOVER, OK);
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

/**
 * “是”按钮定义
 */
class YesButton extends Button {
    private final static Image YES = new ImageIcon("pic/buttons/popups/Yes.png").getImage();
    private final static Image YES_HOVER = new ImageIcon("pic/buttons/popups/Yes_hover.png").getImage();
    private ActionListener listener = null;

    public YesButton(Point start) {
        super(start, YES, YES_HOVER, YES);
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

/**
 * “否”按钮定义
 */
class NoButton extends Button {
    private final static Image NO = new ImageIcon("pic/buttons/popups/No.png").getImage();
    private final static Image NO_HOVER = new ImageIcon("pic/buttons/popups/No_hover.png").getImage();
    private ActionListener listener = null;

    public NoButton(Point start) {
        super(start, NO, NO_HOVER, NO);
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
