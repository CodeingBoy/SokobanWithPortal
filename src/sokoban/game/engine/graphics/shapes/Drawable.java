package sokoban.game.engine.graphics.shapes;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-8-2-0002.
 */
public interface Drawable {
    /**
     * 绘制（仅边界）
     *
     * @param g     欲使用的图形对象
     * @param delta 时间增量
     */
    void draw(Graphics g, double delta);

    /**
     * 填充
     *
     * @param g     欲使用的图形对象
     * @param delta 时间增量
     */
    void fill(Graphics g, double delta);
}
