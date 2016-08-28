package sokoban.game.utils;

import sokoban.utils.Settings;

import java.awt.*;
import java.util.Set;


/**
 * 文本绘制器，由于Java 2D 绘制文本时的坐标为基于基线的坐标，本类帮助你在正确的位置上绘制文本。
 */
public class TextDrawer {
    public final static Color COLOR_DEBUGINF = new Color(255, 255, 255, 150);
    public final static Font FONT_DEBUGINF = new Font("宋体", Font.PLAIN, 12);

    private TextDrawer() {

    }

    /**
     * 基于所指定的位置属性绘制文本
     *
     * @param g              图形对象
     * @param x              文本指定的x坐标
     * @param y              文本指定的y坐标
     * @param relativePosSet 坐标相对属性
     * @param strings        欲绘制之文本
     */
    public static void drawString(Graphics g, int x, int y, Set<RelativePos> relativePosSet, String... strings) {
        // acquire font information
        FontMetrics fontMetrics = g.getFontMetrics();
        int ascent = fontMetrics.getAscent();
        int height = fontMetrics.getHeight();


        int curY = y;
        for (int i = 0; i < strings.length; i++) {
            int width = fontMetrics.stringWidth(strings[i]);

            // convert location to based on the left top corner
            int stringX, stringY;
            if (relativePosSet.contains(RelativePos.POS_LEFT)) { // left
                stringX = x;
            } else if (relativePosSet.contains(RelativePos.POS_RIGHT)) { // right
                stringX = x - width;
            } else if (relativePosSet.contains(RelativePos.POS_CENTER_FIRSTLINE) ||
                    relativePosSet.contains(relativePosSet.contains(RelativePos.POS_CENTER_CENTERLINE))) { // same situation
                stringX = x - width / 2;
            } else { // did not provide any pos information
                throw new IllegalArgumentException();
            }

            if (relativePosSet.contains(RelativePos.POS_TOP)) {
                stringY = curY;
            } else if (relativePosSet.contains(RelativePos.POS_BOTTOM)) { // bottom
                stringY = curY - height;
            } else if (relativePosSet.contains(RelativePos.POS_CENTER_FIRSTLINE)) {
                stringY = curY - height / 2;
            } else if (relativePosSet.contains(relativePosSet.contains(RelativePos.POS_CENTER_CENTERLINE))) {
                stringY = y + (i - strings.length / 2) * height - height / 2;
            } else { // did not provide any pos information
                throw new IllegalArgumentException();
            }

            g.drawString(strings[i], stringX, stringY + ascent); // draw
            curY += height;
        }

    }

    /**
     * 基于所指定的位置属性绘制文本
     *
     * @param g              图形对象
     * @param x              文本指定的x坐标
     * @param y              文本指定的y坐标
     * @param relativePosSet 坐标相对属性
     * @param font           文本字体
     * @param color          文本颜色
     * @param strings        欲绘制之文本
     */
    public static void drawString(Graphics g, int x, int y, Set<RelativePos> relativePosSet, Font font, Color color,
                                  String... strings) {
        if (font != null) g.setFont(font);
        if (color != null) g.setColor(color);

        drawString(g, x, y, relativePosSet, strings);
    }

    /**
     * 基于左上角绘制文本
     *
     * @param g       图形对象
     * @param x       文本左上角x坐标
     * @param y       文本左上角y坐标
     * @param strings 欲绘制的文本（可多行）
     */
    @Deprecated
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
    @Deprecated
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
    @Deprecated
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
    @Deprecated
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

    public enum RelativePos {
        /**
         * 传入x坐标为文本左侧的坐标
         */
        POS_LEFT,
        /**
         * 传入x坐标为文本右侧的坐标
         */
        POS_RIGHT,
        /**
         * 传入y坐标为文本顶侧的坐标
         */
        POS_TOP,
        /**
         * 传入y坐标为文本底侧的坐标
         */
        POS_BOTTOM,
        /**
         * 传入x,y坐标为文本第一行中心的坐标
         */
        POS_CENTER_FIRSTLINE,
        /**
         * 传入x,y坐标为文本中间行中心的坐标
         */
        POS_CENTER_CENTERLINE
    }
}
