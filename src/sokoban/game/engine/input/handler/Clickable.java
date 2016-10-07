package sokoban.game.engine.input.handler;

import java.awt.*;

/**
 * “可被点击”接口
 */
public interface Clickable {
    /**
     * 判断传入点是否在对象内
     *
     * @param point 欲判断的点
     * @return 传入点是否在对象内
     */
    boolean isPointInside(Point point);

    /**
     * 定义对象被点击时的动作
     *
     * @param p 点击时的点
     */
    void onClick(Point p);

    /**
     * 定义鼠标悬停在对象上时的动作
     *
     * @param p 悬停时的点
     */
    void onHover(Point p);

    /**
     * 定义鼠标从未悬停到悬停时的动作
     *
     * @param p 悬停时的点
     */
    void onEnteringHover(Point p);

    /**
     * 定义鼠标从悬停到未悬停时的动作
     * @param p 悬停时的点
     */
    void onExitingHover(Point p);
}
