package sokoban.game.utils;

import sokoban.utils.Settings;

import java.awt.*;

/**
 * 文本绘制器，由于Java 2D 绘制文本时的坐标为基于基线的坐标，本类帮助你在正确的位置上绘制文本。
 */
public class TextDrawer {
    public final static Color COLOR_DEBUGINF = new Color(255, 255, 255, 150);
    public final static Font FONT_DEBUGINF = new Font("宋体", Font.PLAIN, 12);

    private TextDrawer() {

    }

    /**
     * 基于左上角绘制文本
     *
     * @param g       图形对象
     * @param x       文本左上角x坐标
     * @param y       文本左上角y坐标
     * @param strings 欲绘制的文本（可多行）
     */
    public static void drawString(Graphics g, int x, int y, String... strings) {
        FontMetrics fontMetrics = g.getFontMetrics();
        int ascent = fontMetrics.getAscent();
        int height = fontMetrics.getHeight();

        int curY = y;
        for (String s : strings) {
            g.drawString(s, x, curY + ascent);
            curY += height;
        }
    }

    /**
     * 基于左上角绘制文本
     *
     * @param g       图形对象
     * @param x       文本左上角x坐标
     * @param y       文本左上角y坐标
     * @param font    欲用于绘制的字体
     * @param color   欲用于绘制的颜色
     * @param strings 欲绘制的文本（可多行）
     */
    public static void drawString(Graphics g, int x, int y, Font font, Color color, String... strings) {
        if (font != null) g.setFont(font);
        if (color != null) g.setColor(color);

        drawString(g, x, y, strings);
    }

    /**
     * 基于中心绘制文本
     *
     * @param g       图形对象
     * @param x       文本中心x坐标
     * @param y       文本中心y坐标
     * @param strings 欲绘制的文本（可多行）
     */
    public static void drawCenteredString(Graphics g, int x, int y, String... strings) {
        FontMetrics fontMetrics = g.getFontMetrics();
        int ascent = fontMetrics.getAscent();
        int height = fontMetrics.getHeight();

        int curY = y; // for multiple lines of texts
        for (String s : strings) {
            int width = fontMetrics.stringWidth(s);
            // calculate string pos based on the upper left corner
            int stringX = x - width / 2;
            int stringY = curY - height / 2 + ascent; // add ascent so that string can draw on where we want
            g.drawString(s, stringX, stringY);

            if (Settings.isDebugMode()) { // for debug
                g.drawRect(x - width / 2, curY - height / 2, width, height);

                g.drawLine(0, stringY, 500, stringY);
                drawString(g, 0, stringY, "new baseline");
            }

            curY += height;
        }
    }

    /**
     * 基于中心绘制文本
     *
     * @param g       图形对象
     * @param x       文本左上角x坐标
     * @param y       文本左上角y坐标
     * @param font    欲用于绘制的字体
     * @param color   欲用于绘制的颜色
     * @param strings 欲绘制的文本（可多行）
     */
    public static void drawCenteredString(Graphics g, int x, int y, Font font, Color color, String... strings) {
        if (font != null) g.setFont(font);
        if (color != null) g.setColor(color);

        drawCenteredString(g, x, y, strings);
    }

    public static int getTextWidth(Graphics g, String s) {
        return g.getFontMetrics().stringWidth(s);
    }

    public static int getTextHeight(Graphics g) {
        return g.getFontMetrics().getHeight();
    }
}
