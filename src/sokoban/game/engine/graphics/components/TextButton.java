package sokoban.game.engine.graphics.components;


import com.sun.istack.internal.Nullable;
import sokoban.game.utils.TextDrawer;

import java.awt.*;
import java.util.Set;

/**
 * 带文本的按钮类
 */
public abstract class TextButton extends Button {
    private String[] texts;
    private Point textPos;
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
     * @param textPos     文本相对于按钮左上角的坐标
     * @param font        文本字体
     * @param normalColor 文本颜色
     * @param texts       文本
     */
    public TextButton(Point start, @Nullable Image normalImg, @Nullable Image hoverImg, @Nullable Image pressImg,
                      Point textPos, Font font, Color normalColor, Color hoverColor, Color clickColor, String... texts) {
        super(start, normalImg, hoverImg, pressImg);
        this.texts = texts;
        this.textPos = textPos;
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
     * @param textPos     文本相对于按钮左上角的坐标
     * @param font        文本字体
     * @param normalColor 文本颜色
     * @param hoverColor  悬停时文本颜色
     * @param clickColor  点击时文本颜色
     * @param texts       文本
     */
    public TextButton(Point start, int width, int height, @Nullable Image normalImg, @Nullable Image hoverImg, @Nullable Image pressImg,
                      Point textPos, Font font, Color normalColor, Color hoverColor, Color clickColor, String... texts) {
        super(start, width, height, normalImg, hoverImg, pressImg);
        this.texts = texts;
        this.textPos = textPos;
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
     * @param textPos     文本相对于按钮左上角的坐标
     * @param font        文本字体
     * @param normalColor 文本颜色
     * @param hoverColor  悬停时文本颜色
     * @param clickColor  点击时文本颜色
     * @param styles      按钮样式，传入多个是请使用 EnumSet.of
     * @param texts       文本
     */
    public TextButton(Point start, int width, int height, @Nullable Image normalImg, @Nullable Image hoverImg, @Nullable Image pressImg,
                      Point textPos, Font font, Color normalColor, Color hoverColor, Color clickColor, Set<Style> styles, String... texts) {
        super(start, width, height, normalImg, hoverImg, pressImg);
        this.texts = texts;
        this.textPos = textPos;
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
     * @param textPos     文本相对于按钮左上角的坐标
     * @param font        文本字体
     * @param normalColor 文本颜色
     * @param hoverColor  悬停时文本颜色
     * @param clickColor  点击时文本颜色
     * @param styles      按钮样式，传入多个是请使用 EnumSet.of
     * @param texts       文本
     */
    public TextButton(boolean center, Point start, int width, int height, @Nullable Image normalImg, @Nullable Image hoverImg, @Nullable Image pressImg,
                      Point textPos, Font font, Color normalColor, Color hoverColor, Color clickColor, Set<Style> styles, String... texts) {
        this(new Point(start.x - width / 2, start.y - height / 2), width, height, normalImg, hoverImg, pressImg, textPos, font, normalColor, hoverColor, clickColor, styles, texts);
    }

    @Override
    public void draw(Graphics g, double delta) {
        boolean isHovering = isHovering(), isClicking = isClicking();

        super.draw(g, delta);

        if (styles != null && styles.contains(Style.CENTER_TEXT)) {
            if (isHovering && hoverColor != null)
                TextDrawer.drawCenteredString(g, (int) (originVectors[0].x + getWidth() / 2 + textPos.x),
                        (int) (originVectors[0].y + getHeight() / 2 + textPos.y), font, hoverColor, texts);
            else if (isClicking && clickColor != null)
                TextDrawer.drawCenteredString(g, (int) (originVectors[0].x + getWidth() / 2 + textPos.x),
                        (int) (originVectors[0].y + getHeight() / 2 + textPos.y), font, clickColor, texts);
            else
                TextDrawer.drawCenteredString(g, (int) (originVectors[0].x + getWidth() / 2 + textPos.x),
                        (int) (originVectors[0].y + getHeight() / 2 + textPos.y), font, normalColor, texts);
        } else {
            if (isHovering && hoverColor != null)
                TextDrawer.drawString(g, (int) originVectors[0].x + textPos.x, (int) originVectors[0].y + textPos.y, font, hoverColor, texts);
            else if (isClicking && clickColor != null)
                TextDrawer.drawString(g, (int) originVectors[0].x + textPos.x, (int) originVectors[0].y + textPos.y, font, clickColor, texts);
            else
                TextDrawer.drawString(g, (int) originVectors[0].x + textPos.x, (int) originVectors[0].y + textPos.y, font, normalColor, texts);
        }

    }

    public enum Style {
        CENTER_TEXT
    }


}
