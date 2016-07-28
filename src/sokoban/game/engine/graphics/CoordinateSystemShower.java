package sokoban.game.engine.graphics;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class CoordinateSystemShower {

    public static void drawWorldXAxis(Graphics g, int start, int end, int step, int length) {
        for (int i = start; i <= end; i += step) {
            g.drawLine(i, 0, i, length);
        }
        for (int i = -start; i <= -end; i -= step) {
            g.drawLine(i, 0, i, length);
        }
    }

    public static void drawWorldXAxis(Graphics g, int start, int end, int step, int length, boolean drawText, int textY) {
        for (int i = start; i <= end; i += step) {
            g.drawLine(i, 0, i, length);
            if (drawText)
                g.drawString(String.valueOf(i), i, textY);
        }
        for (int i = -start; i <= -end; i -= step) {
            g.drawLine(i, 0, i, length);
            if (drawText)
                g.drawString(String.valueOf(i), i, textY);
        }
    }

    public static void drawWorldYAxis(Graphics g, int start, int end, int step, int length) {
        for (int i = start; i <= end; i += step) {
            g.drawLine(0, i, length, i);
        }
        for (int i = -start; i <= -end; i -= step) {
            g.drawLine(0, i, length, i);
        }
    }

    public static void drawWorldYAxis(Graphics g, int start, int end, int step, int length, boolean drawText, int textX) {
        for (int i = start; i <= end; i += step) {
            g.drawLine(0, i, length, i);
            if (drawText)
                g.drawString(String.valueOf(i), textX, i);
        }
        for (int i = -start; i <= -end; i -= step) {
            g.drawLine(0, i, length, i);
            if (drawText)
                g.drawString(String.valueOf(i), textX, i);
        }
    }

    public static void drawScreenXAxis(Graphics g, int start, int end, int step, int length, ScreenMappingTool screenMappingTool) {
        final Point ORIGIN = screenMappingTool.getScreenPoint(new Point(0, 0)); // 坐标原点
        for (int i = start; i <= end; i += step)
            g.drawLine(ORIGIN.x + i, ORIGIN.y - length / 2, ORIGIN.x + i, ORIGIN.y + length / 2);

        for (int i = -start; i >= -end; i -= step)
            g.drawLine(ORIGIN.x + i, ORIGIN.y - length / 2, ORIGIN.x + i, ORIGIN.y + length / 2);
    }

    public static void drawScreenXAxis(Graphics g, int start, int end, int step, int length,
                                       ScreenMappingTool screenMappingTool, int textY) {
        drawScreenXAxis(g, start, end, step, length, screenMappingTool);

        final Point GRIDORIGIN = screenMappingTool.getScreenPoint(new Point(0, 0)); // 坐标原点
        for (int i = start; i <= end; i += step)
            g.drawString(String.valueOf(i), GRIDORIGIN.x + i, GRIDORIGIN.y + textY);

        for (int i = -start; i >= -end; i -= step)
            g.drawString(String.valueOf(i), GRIDORIGIN.x + i, GRIDORIGIN.y + textY);
    }

    public static void drawScreenYAxis(Graphics g, int start, int end, int step, int length, ScreenMappingTool screenMappingTool) {
        final Point ORIGIN = screenMappingTool.getScreenPoint(new Point(0, 0)); // 坐标原点
        for (int i = start; i <= end; i += step)
            g.drawLine(ORIGIN.x - length / 2, ORIGIN.y + i, ORIGIN.y + length / 2, ORIGIN.y + i);

        for (int i = -start; i >= -end; i -= step)
            g.drawLine(ORIGIN.x - length / 2, ORIGIN.y + i, ORIGIN.y + length / 2, ORIGIN.y + i);

    }

    public static void drawScreenYAxis(Graphics g, int start, int end, int step, int length,
                                       ScreenMappingTool screenMappingTool, int textX) {
        drawScreenYAxis(g, start, end, step, length, screenMappingTool);

        final Point GRIDORIGIN = screenMappingTool.getScreenPoint(new Point(0, 0)); // 坐标原点
        for (int i = start; i <= end; i += step)
            g.drawString(String.valueOf(i), GRIDORIGIN.x + textX, GRIDORIGIN.y + i);

        for (int i = -start; i >= -end; i -= step)
            g.drawString(String.valueOf(i), GRIDORIGIN.x + textX, GRIDORIGIN.y + i);
    }
}
