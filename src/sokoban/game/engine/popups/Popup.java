package sokoban.game.engine.popups;

import sokoban.game.engine.graphics.shapes.Drawable;
import sokoban.game.engine.scenes.Scene;

import java.awt.*;

public abstract class Popup implements Drawable {
    private Scene scene;
    private Color backgroundColor;

    @Override
    public void draw(Graphics g, double delta) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, scene.getWidth(), scene.getHeight());

        render(g, delta);
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
}
