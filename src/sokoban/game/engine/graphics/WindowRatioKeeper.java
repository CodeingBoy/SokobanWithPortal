package sokoban.game.engine.graphics;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by CodeingBoy on 2016-7-28-0028.
 */
public class WindowRatioKeeper extends ComponentAdapter {
    private Canvas canvas;
    private Container contentPane;
    private int width, height;
    private float frameRatio;

    public WindowRatioKeeper(Canvas canvas, Container contentPane, int width, int height, float frameRatio) {
        this.canvas = canvas;
        this.contentPane = contentPane;
        this.width = width;
        this.height = height;
        this.frameRatio = frameRatio;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        // super.componentResized(e);

        int curPaneWidth = contentPane.getWidth();
        int curPaneHeight = contentPane.getHeight();

        if (frameRatio > 0 && frameRatio < 1) { // frameRatio works
            curPaneWidth *= frameRatio;
            curPaneHeight *= frameRatio;
        }

        Point canvasLocation = new Point((contentPane.getWidth() - curPaneWidth) / 2, (contentPane.getHeight() - curPaneHeight) / 2);

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

        System.out.println("content width " + contentPane.getWidth() + " height " + contentPane.getHeight());
        System.out.println("width " + newWidth + " height " + newHeight);
        System.out.println(canvas.getSize());
        System.out.println(canvas.getLocation());
    }
}
