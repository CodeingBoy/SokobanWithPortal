package sokoban.game.utils;

import java.awt.*;

/**
 * 文本绘制器，由于Java 2D 绘制文本时的坐标为基于基线的坐标，本类帮助你在正确的位置上绘制文本。
 */
public class TextDrawer {
    private final static Font defaultFont = new Font("宋体", Font.PLAIN, 9);
    private final static Color defaultColor = Color.WHITE;
    private Font font;
    private Color color;

    /**
     * 使用默认字体和默认颜色构造一个文本绘制器
     */
    public TextDrawer() {
        this(defaultFont, defaultColor);
    }

    /**
     * 使用自定义的字体构造一个文本绘制器
     *
     * @param font 欲使用绘制的字体
     */
    public TextDrawer(Font font) {
        this(font, defaultColor);
    }

    /**
     * 使用自定义的字体和颜色构造一个文本绘制器
     *
     * @param font  欲使用绘制的字体
     * @param color 欲使用绘制的颜色
     */
    public TextDrawer(Font font, Color color) {
        this.font = font;
        this.color = color;
    }

    /**
     * 更换绘制的字体
     *
     * @param font 欲使用绘制的字体
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * 更换绘制的颜色
     *
     * @param color 欲使用绘制的颜色
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * 基于左上角绘制文本
     *
     * @param g       图形对象
     * @param x       文本左上角x坐标
     * @param y       文本左上角y坐标
     * @param strings 欲绘制的文本（可多行）
     */
    public void drawString(Graphics g, int x, int y, String... strings) {

    }

    /**
     * 基于中心绘制文本
     *
     * @param g       图形对象
     * @param x       文本中心x坐标
     * @param y       文本中心y坐标
     * @param strings 欲绘制的文本（可多行）
     */
    public void drawCenteredString(Graphics g, int x, int y, String... strings) {

    }


}
