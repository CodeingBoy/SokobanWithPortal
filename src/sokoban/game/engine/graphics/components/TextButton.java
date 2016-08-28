package sokoban.game.engine.graphics.components;


import com.sun.istack.internal.Nullable;
import sokoban.game.utils.TextDrawer;

import java.awt.*;
import java.util.EnumSet;
import java.util.Set;

import static sokoban.game.utils.TextDrawer.RelativePos.*;

/**
 * 带文本的按钮类
 */
public abstract class TextButton extends Button {
    private String[] texts;
    private Point textOffset;
    private Font font;
    private Color normalColor, hoverColor, clickColor;
    private Set<Style> styles;

    /**
     * 构建一个带文本的按钮
     *
     * @param start       按钮左上角屏幕坐标
     * @param normalImg   普通状态下的背景图片
     * @param hoverImg    悬停状态下的背景图片
     * @param pressImg    按下状态下的背景图片
     * @param textOffset  文本相对于按钮左上角的坐标
     * @param font        文本字体
     * @param normalColor 文本颜色
     * @param texts       文本
     */
    public TextButton(Point start, @Nullable Image normalImg, @Nullable Image hoverImg, @Nullable Image pressImg,
                      Point textOffset, Font font, Color normalColor, Color hoverColor, Color clickColor, String... texts) {
        super(start, normalImg, hoverImg, pressImg);
        this.texts = texts;
        this.textOffset = textOffset;
        this.font = font;
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.clickColor = clickColor;
    }

    /**
     * 构建一个带文本的按钮
     *
     * @param start       按钮左上角屏幕坐标
     * @param width       按钮宽度
     * @param height      按钮高度
     * @param normalImg   普通状态下的背景图片
     * @param hoverImg    悬停状态下的背景图片
     * @param pressImg    按下状态下的背景图片
     * @param textOffset  文本相对于按钮左上角的坐标
     * @param font        文本字体
     * @param normalColor 文本颜色
     * @param hoverColor  悬停时文本颜色
     * @param clickColor  点击时文本颜色
     * @param texts       文本
     */
    public TextButton(Point start, int width, int height, @Nullable Image normalImg, @Nullable Image hoverImg, @Nullable Image pressImg,
                      Point textOffset, Font font, Color normalColor, Color hoverColor, Color clickColor, String... texts) {
        super(start, width, height, normalImg, hoverImg, pressImg);
        this.texts = texts;
        this.textOffset = textOffset;
        this.font = font;
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.clickColor = clickColor;
    }

    /**
     * 构建一个带文本的按钮
     *
     * @param start       按钮左上角屏幕坐标
     * @param width       按钮宽度
     * @param height      按钮高度
     * @param normalImg   普通状态下的背景图片
     * @param hoverImg    悬停状态下的背景图片
     * @param pressImg    按下状态下的背景图片
     * @param textOffset  文本相对于按钮左上角的坐标
     * @param font        文本字体
     * @param normalColor 文本颜色
     * @param hoverColor  悬停时文本颜色
     * @param clickColor  点击时文本颜色
     * @param styles      按钮样式，传入多个是请使用 EnumSet.of
     * @param texts       文本
     */
    public TextButton(Point start, int width, int height, @Nullable Image normalImg, @Nullable Image hoverImg, @Nullable Image pressImg,
                      Point textOffset, Font font, Color normalColor, Color hoverColor, Color clickColor, Set<Style> styles, String... texts) {
        super(start, width, height, normalImg, hoverImg, pressImg);
        this.texts = texts;
        this.textOffset = textOffset;
        this.font = font;
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.clickColor = clickColor;
        this.styles = styles;
    }


    /**
     * 构建一个居中的，带文本的按钮
     *
     * @param start       按钮中心屏幕坐标
     * @param width       按钮宽度
     * @param height      按钮高度
     * @param normalImg   普通状态下的背景图片
     * @param hoverImg    悬停状态下的背景图片
     * @param pressImg    按下状态下的背景图片
     * @param textOffset  文本相对于按钮左上角的坐标
     * @param font        文本字体
     * @param normalColor 文本颜色
     * @param hoverColor  悬停时文本颜色
     * @param clickColor  点击时文本颜色
     * @param styles      按钮样式，传入多个时请使用 EnumSet.of
     * @param texts       文本
     */
    public TextButton(boolean center, Point start, int width, int height, @Nullable Image normalImg, @Nullable Image hoverImg, @Nullable Image pressImg,
                      Point textOffset, Font font, Color normalColor, Color hoverColor, Color clickColor, Set<Style> styles, String... texts) {
        this(new Point(start.x - width / 2, start.y - height / 2), width, height, normalImg, hoverImg, pressImg, textOffset, font, normalColor, hoverColor, clickColor, styles, texts);
    }

    @Override
    public void draw(Graphics g, double delta) {
        boolean isHovering = isHovering(), isClicking = isClicking();

        super.draw(g, delta);

        int x, y;
        Set<TextDrawer.RelativePos> posSet;
        Color textColor;
        if (styles != null && styles.contains(Style.CENTER_TEXT)) {
            x = (int) (originVectors[0].x + getWidth() / 2 + textOffset.x);
            y = (int) (originVectors[0].y + getHeight() / 2 + textOffset.y);
            posSet = EnumSet.of(POS_CENTER_FIRSTLINE);
        } else {
            x = (int) (originVectors[0].x + textOffset.x);
            y = (int) (originVectors[0].y + textOffset.y);
            posSet = EnumSet.of(POS_LEFT, POS_TOP);
        }

        if (isHovering && hoverColor != null)
            textColor = hoverColor;
        else if (isClicking && clickColor != null)
            textColor = clickColor;
        else
            textColor = normalColor;

        TextDrawer.drawString(g, x, y, posSet, font, textColor, texts);
    }

    public enum Style {
        CENTER_TEXT
    }


}
