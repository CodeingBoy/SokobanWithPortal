package sokoban.game.engine.graphics;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * 画面比例调节器，用于维持画面的比例
 */
public class WindowRatioKeeper extends ComponentAdapter {
    private Canvas canvas;
    private Container contentPane;
    private int width, height;
    private double frameRatio;

    public WindowRatioKeeper(Container contentPane, int width, int height, double frameRatio) {
        this.contentPane = contentPane;
        this.width = width;
        this.height = height;
        this.frameRatio = frameRatio;
    }

    public WindowRatioKeeper(Canvas canvas, Container contentPane, int width, int height, double frameRatio) {
        this.canvas = canvas;
        this.contentPane = contentPane;
        this.width = width;
        this.height = height;
        this.frameRatio = frameRatio;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public synchronized void componentResized(ComponentEvent e) {
        if (canvas == null)
            throw new IllegalArgumentException();

        // super.componentResized(e);

        int curPaneWidth = contentPane.getWidth();
        int curPaneHeight = contentPane.getHeight();

        if (frameRatio > 0 && frameRatio < 1) { // frameRatio works
            curPaneWidth *= frameRatio;
            curPaneHeight *= frameRatio;
        }

        Point canvasLocation =
                new Point((contentPane.getWidth() - curPaneWidth) / 2, (contentPane.getHeight() - curPaneHeight) / 2);

        // calc canvas's new size
        int newWidth = curPaneWidth;
        int newHeight = curPaneWidth * height / width;
        if (newHeight > curPaneHeight) {
            newWidth = curPaneHeight * width / height;
            newHeight = curPaneHeight;
        }

        canvasLocation.x += (curPaneWidth - newWidth) / 2;
        canvasLocation.y += (curPaneHeight - newHeight) / 2;

        canvas.setLocation(canvasLocation);
        canvas.setSize(newWidth, newHeight);
    }
}
