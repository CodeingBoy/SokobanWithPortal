package sokoban.game.engine.input;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by CodeingBoy on 2016-7-11-0011.
 */
public class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener {
    private boolean[] isButtonPressing = new boolean[3];
    private int[] buttonDownLastFrames = new int[3];
    private Point curPos = new Point(0, 0);
    private Point position;
    private int curWheelRotated, wheelRotated;

    public boolean isButtonDown(int buttonCode) {
        return buttonDownLastFrames[buttonCode - 1] > 0;
    }

    public boolean isButtonDownOnce(int buttonCode) {
        return buttonDownLastFrames[buttonCode - 1] == 1;
    }

    public Point getPosition() {
        return position;
    }

    public int getWheelRotated() {
        return wheelRotated;
    }

    public synchronized void poll() {
        position = new Point(curPos);
        wheelRotated = curWheelRotated;
        curWheelRotated = 0;

        for (int i = 0; i < isButtonPressing.length; i++) {
            if (isButtonPressing[i])
                buttonDownLastFrames[i]++;
            else
                buttonDownLastFrames[i] = 0;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int buttonCode = e.getButton() - 1;
        if (buttonCode >= 0 && buttonCode < isButtonPressing.length)
            isButtonPressing[buttonCode] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int buttonCode = e.getButton() - 1;
        if (buttonCode >= 0 && buttonCode < isButtonPressing.length)
            isButtonPressing[buttonCode] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        curPos = e.getPoint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        curWheelRotated += e.getWheelRotation();
    }
}
