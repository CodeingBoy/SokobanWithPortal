package sokoban.dialog;

import sokoban.boxengine.FrameRate;

import javax.swing.*;
import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-10-0010.
 */
public class GameDialog extends JFrame {
    private FrameRate frameRate = null;

    public GameDialog() throws HeadlessException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setTitle("GameDialog");
    }

    public static void main(String[] args) {
        GameDialog dialog = new GameDialog();
        FrameRate frameRate = new FrameRate();
        frameRate.setShouldLog(true);
        dialog.setFrameRate(frameRate);

        LogDialog logDialog = LogDialog.getInstance();
        logDialog.setVisible(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dialog.createGUI();
                dialog.setVisible(true);
            }
        });
    }

    public void createGUI() {
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
    }

    public FrameRate getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(FrameRate frameRate) {
        if (frameRate != null && !frameRate.isInitalized())
            frameRate.initialize();
        this.frameRate = frameRate;
    }

    private class GamePanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            onPaint(g);
            repaint();
        }

        private void onPaint(Graphics g) {
            if (frameRate != null) {
                frameRate.calculate();
                g.setColor(Color.white);
                g.drawString(frameRate.getLatestFrameRateString(), 50, 50);
            }
        }
    }
}
