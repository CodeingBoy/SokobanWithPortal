package sokoban.game.engine.graphics;

import sokoban.game.engine.graphics.shapes.Square;

import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class CoordinateSystemShower {

    public static void drawScreenAxis(Graphics g, int start, int end, int step, int length) {
        for (int i = start; i <= end; i += step) {
            g.drawLine(i, 0, i, length);
        }
        for (int i = -start; i <= -end; i -= step) {
            g.drawLine(i, 0, i, length);
        }
    }

    public static void drawScreenAxis(Graphics g, int start, int end, int step, int length, int textYOffset) {
        drawScreenAxis(g, start, end, step, length);

        for (int i = start; i <= end; i += step) {
            g.drawString(String.valueOf(i), i, textYOffset);
        }
        for (int i = -start; i <= -end; i -= step) {
            g.drawString(String.valueOf(i), i, textYOffset);
        }
    }

    public static void drawScreenYAxis(Graphics g, int start, int end, int step, int length) {
        for (int i = start; i <= end; i += step) {
            g.drawLine(0, i, length, i);
        }
        for (int i = -start; i <= -end; i -= step) {
            g.drawLine(0, i, length, i);
        }
    }

    public static void drawScreenYAxis(Graphics g, int start, int end, int step, int length, int textXOffset) {
        drawScreenYAxis(g, start, end, step, length);

        for (int i = start; i <= end; i += step) {
            g.drawString(String.valueOf(i), textXOffset, i);
        }
        for (int i = -start; i <= -end; i -= step) {
            g.drawString(String.valueOf(i), textXOffset, i);
        }
    }

    public static void drawWorldXAxis(Graphics g, int start, int end, int step, int length, ScreenMappingTool screenMappingTool) {
        final Point ORIGIN = screenMappingTool.worldToScreen(new Point(0, 0)); // 坐标原点
        for (int i = start; i <= end; i += step)
            g.drawLine(ORIGIN.x + i, ORIGIN.y - length / 2, ORIGIN.x + i, ORIGIN.y + length / 2);

        for (int i = -start; i >= -end; i -= step)
            g.drawLine(ORIGIN.x + i, ORIGIN.y - length / 2, ORIGIN.x + i, ORIGIN.y + length / 2);
    }

    public static void drawWorldXAxis(Graphics g, int start, int end, int step, int length,
                                      ScreenMappingTool screenMappingTool, int textYOffset) {
        drawWorldXAxis(g, start, end, step, length, screenMappingTool);

        final Point ORIGIN = screenMappingTool.worldToScreen(new Point(0, 0)); // 坐标原点
        for (int i = start; i <= end; i += step)
            g.drawString(String.valueOf(i), ORIGIN.x + i, ORIGIN.y + textYOffset);

        for (int i = -start; i >= -end; i -= step)
            g.drawString(String.valueOf(i), ORIGIN.x + i, ORIGIN.y + textYOffset);
    }

    public static void drawWorldYAxis(Graphics g, int start, int end, int step, int length, ScreenMappingTool screenMappingTool) {
        final Point ORIGIN = screenMappingTool.worldToScreen(new Point(0, 0)); // 坐标原点
        for (int i = start; i <= end; i += step)
            g.drawLine(ORIGIN.x - length / 2, ORIGIN.y + i, ORIGIN.x + length / 2, ORIGIN.y + i);

        for (int i = -start; i >= -end; i -= step)
            g.drawLine(ORIGIN.x - length / 2, ORIGIN.y + i, ORIGIN.x + length / 2, ORIGIN.y + i);

    }

    public static void drawWorldYAxis(Graphics g, int start, int end, int step, int length,
                                      ScreenMappingTool screenMappingTool, int textXOffset) {
        drawWorldYAxis(g, start, end, step, length, screenMappingTool);

        final Point ORIGIN = screenMappingTool.worldToScreen(new Point(0, 0)); // 坐标原点
        for (int i = start; i <= end; i += step)
            g.drawString(String.valueOf(i), ORIGIN.x + textXOffset, ORIGIN.y - i); // 由于屏幕坐标系y轴往下增加 因此原点-i

        for (int i = -start; i >= -end; i -= step)
            g.drawString(String.valueOf(i), ORIGIN.x + textXOffset, ORIGIN.y - i);
    }

    public static void drawMappedWorldXAxis(Graphics g, int start, int end, int step, int length, ScreenMappingTool screenMappingTool) {
        for (int i = start; i <= end; i += step) {
            Square square = new Square(new Vector2f(i, 0), step);
            square.setWorldToScreen(screenMappingTool);
            square.draw(g, 0);
        }

        for (int i = -start; i >= -end; i -= step) {
            Square square = new Square(new Vector2f(i, 0), step);
            square.setWorldToScreen(screenMappingTool);
            square.draw(g, 0);
        }
    }

    public static void drawMappedWorldXAxis(Graphics g, int start, int end, int step, int length,
                                            ScreenMappingTool screenMappingTool, int textYOffset) {
        drawMappedWorldXAxis(g, start, end, step, length, screenMappingTool);

        final Point ORIGIN = screenMappingTool.worldToScreen(new Point(0, 0)); // 坐标原点
        for (int i = start; i <= end; i += step)
            g.drawString(String.valueOf(i), ORIGIN.x + i, ORIGIN.y + textYOffset);

        for (int i = -start; i >= -end; i -= step)
            g.drawString(String.valueOf(i), ORIGIN.x + i, ORIGIN.y + textYOffset);
    }

    public static void drawMappedWorldYAxis(Graphics g, int start, int end, int step, int length, ScreenMappingTool screenMappingTool) {
        for (int i = start; i <= end; i += step) {
            Square square = new Square(new Vector2f(0, i), step);
            square.setWorldToScreen(screenMappingTool);
            square.draw(g, 0);
        }

        for (int i = -start; i >= -end; i -= step) {
            Square square = new Square(new Vector2f(0, i), step);
            square.setWorldToScreen(screenMappingTool);
            square.draw(g, 0);
        }
    }

    public static void drawMappedWorldYAxis(Graphics g, int start, int end, int step, int length,
                                            ScreenMappingTool screenMappingTool, int textXOffset) {
        drawMappedWorldYAxis(g, start, end, step, length, screenMappingTool);

        final Point ORIGIN = screenMappingTool.worldToScreen(new Point(0, 0)); // 坐标原点
        for (int i = start; i <= end; i += step)
            g.drawString(String.valueOf(i), ORIGIN.x + textXOffset, ORIGIN.y - i); // 由于屏幕坐标系y轴往下增加 因此原点-i

        for (int i = -start; i >= -end; i -= step)
            g.drawString(String.valueOf(i), ORIGIN.x + textXOffset, ORIGIN.y - i);
    }
}
