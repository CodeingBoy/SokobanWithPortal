package sokoban.game.engine.graphics.components;


import com.sun.istack.internal.Nullable;
import sokoban.game.utils.TextDrawer;

import java.awt.*;

/**
 * 带文本的按钮类
 */
public abstract class TextButton extends Button {
    private String[] texts;
    private Point textPos;
    private Font font;
    private Color color;

    /**
     * 构建一个带文本的按钮
     *
     * @param start     按钮左上角屏幕坐标
     * @param normalImg 普通状态下的背景图片
     * @param hoverImg  悬停状态下的背景图片
     * @param pressImg  按下状态下的背景图片
     * @param textPos   文本相对于按钮左上角的坐标
     * @param font      文本字体
     * @param color     文本颜色
     * @param texts     文本
     */
    public TextButton(Point start, @Nullable Image normalImg, @Nullable Image hoverImg, @Nullable Image pressImg,
                      Point textPos, Font font, Color color, String... texts) {
        super(start, normalImg, hoverImg, pressImg);
        this.texts = texts;
        this.textPos = textPos;
        this.font = font;
        this.color = color;
    }

    /**
     * 构建一个带文本的按钮
     *
     * @param start     按钮左上角屏幕坐标
     * @param width     按钮宽度
     * @param height    按钮高度
     * @param normalImg 普通状态下的背景图片
     * @param hoverImg  悬停状态下的背景图片
     * @param pressImg  按下状态下的背景图片
     * @param textPos   文本相对于按钮左上角的坐标
     * @param font      文本字体
     * @param color     文本颜色
     * @param texts     文本
     */
    public TextButton(Point start, int width, int height, @Nullable Image normalImg, @Nullable Image hoverImg, @Nullable Image pressImg,
                      Point textPos, Font font, Color color, String... texts) {
        super(start, width, height, normalImg, hoverImg, pressImg);
        this.texts = texts;
        this.textPos = textPos;
        this.font = font;
        this.color = color;
    }

    @Override
    public void draw(Graphics g, double delta) {
        super.draw(g, delta);

        TextDrawer.drawString(g, textPos.x, textPos.y, font, color, texts);
    }
}
