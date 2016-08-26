package sokoban.game.engine.popups;

import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.game.engine.input.handler.KeyboardInputHandler;
import sokoban.game.engine.input.handler.MouseInputHandler;
import sokoban.game.engine.scenes.SuperScene;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Popup：一种可在 Scene 上覆盖显示的特殊 Drawable
 */
public abstract class Popup implements Drawable {
    protected SuperScene scene;
    protected Color backgroundColor;
    protected Map<String, Drawable> drawables = new HashMap<>();
    private KeyboardInputHandler keyboardInputHandler, tempKeyboardInputHandler;
    private MouseInputHandler mouseInputHandler, tempMouseInputHandler;
    private Popup popup;

    /**
     * 创建一个 Popup
     *
     * @param scene           父场景
     * @param backgroundColor 背景颜色
     */
    public Popup(SuperScene scene, Color backgroundColor) {
        this.scene = scene;
        this.backgroundColor = backgroundColor;

        scene.getWindow().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshObjects();
            }
        });
    }

    @Override
    public void draw(Graphics g, double delta) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, scene.getWidth(), scene.getHeight());

        render(g, delta);
        renderDrawables(g, delta);

        if (popup != null) popup.draw(g, delta);
    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }

    /**
     * 渲染函数，请重写该函数以便渲染 Popup 画面
     *
     * @param g     图形对象
     * @param delta 时间增量
     */
    public abstract void render(Graphics g, double delta);

    public KeyboardInputHandler getKeyboardInputHandler() {
        return keyboardInputHandler;
    }

    public void setKeyboardInputHandler(KeyboardInputHandler keyboardInputHandler) {
        if (popup != null) scene.setKeyboardInputHandler(keyboardInputHandler);

        this.keyboardInputHandler = keyboardInputHandler;
    }

    public MouseInputHandler getMouseInputHandler() {
        return mouseInputHandler;
    }

    public void setMouseInputHandler(MouseInputHandler mouseInputHandler) {
        if (popup != null) scene.setMouseInputHandler(mouseInputHandler);

        this.mouseInputHandler = mouseInputHandler;
    }

    /**
     * 销毁自身
     */
    protected void dispose() {
        scene.detachPopup();
    }

    /**
     * 刷新对象函数，该函数在附着 Popup 及窗口大小变更时被调用。<br/>
     * 请重写该函数以使窗口对象正确显示。
     */
    public abstract void refreshObjects();

    /**
     * 获取窗口宽度
     *
     * @return 窗口宽度
     */
    protected final int getWidth() {
        return scene.getCanvas().getWidth();
    }

    /**
     * 获取窗口高度
     *
     * @return 窗口高度
     */
    protected final int getHeight() {
        return scene.getCanvas().getHeight();
    }

    /**
     * 对 Drawable 中的对象进行绘制
     *
     * @param g     图形对象
     * @param delta 时间增量
     */
    private final synchronized void renderDrawables(Graphics g, double delta) {
        for (Drawable d : drawables.values()) {
            d.draw(g, delta);
        }
    }

    /**
     * 附着 Popup<br/>
     * 附着前请先设置好传入 Popup 的键盘、鼠标处理器，附着后无法变更。
     *
     * @param popup 欲附着的 Popup
     */
    public void attachPopup(Popup popup) {
        if (this.popup != null) detachPopup();

        this.popup = popup;

        tempKeyboardInputHandler = keyboardInputHandler;
        tempMouseInputHandler = mouseInputHandler;
        setKeyboardInputHandler(popup.getKeyboardInputHandler());
        setMouseInputHandler(popup.getMouseInputHandler());

        popup.refreshObjects();
    }

    /**
     * 解附着 Popup
     */
    public void detachPopup() {
        setKeyboardInputHandler(tempKeyboardInputHandler);
        setMouseInputHandler(tempMouseInputHandler);
        tempKeyboardInputHandler = null;
        tempMouseInputHandler = null;

        popup = null;
    }
}
