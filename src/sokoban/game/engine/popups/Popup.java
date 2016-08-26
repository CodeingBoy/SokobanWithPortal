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

public abstract class Popup implements Drawable {
    protected SuperScene scene;
    protected Color backgroundColor;
    protected KeyboardInputHandler keyboardInputHandler;
    protected MouseInputHandler mouseInputHandler;
    protected Map<String, Drawable> drawables = new HashMap<>();


    public Popup(SuperScene scene, Color backgroundColor) {
        this.scene = scene;
        this.backgroundColor = backgroundColor;

        scene.getWindow().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onResized();
            }
        });
    }

    @Override
    public void draw(Graphics g, double delta) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, scene.getWidth(), scene.getHeight());

        render(g, delta);
        renderDrawables(g, delta);
    }

    @Override
    public void fill(Graphics g, double delta) {
        draw(g, delta);
    }

    /**
     * 渲染函数，请重写该函数以便渲染弹出窗口画面
     *
     * @param g     图形对象
     * @param delta 时间增量
     */
    public abstract void render(Graphics g, double delta);

    public KeyboardInputHandler getKeyboardInputHandler() {
        return keyboardInputHandler;
    }

    public void setKeyboardInputHandler(KeyboardInputHandler keyboardInputHandler) {
        this.keyboardInputHandler = keyboardInputHandler;
    }

    public MouseInputHandler getMouseInputHandler() {
        return mouseInputHandler;
    }

    public void setMouseInputHandler(MouseInputHandler mouseInputHandler) {
        this.mouseInputHandler = mouseInputHandler;
    }

    public void dispose() {
        scene.detachPopup();
    }

    public abstract void onResized();

    protected final int getWidth() {
        return scene.getCanvas().getWidth();
    }

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
}
