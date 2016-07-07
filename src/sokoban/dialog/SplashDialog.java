package sokoban.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * Created by CodeingBoy on 2016-7-7-0007.
 */
public class SplashDialog extends JFrame {
    final LoadingInformationPanel infPanel = new LoadingInformationPanel();

    public SplashDialog() {
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setTitle("推箱子-载入中……");
        setLocationRelativeTo(null);
        setLayout(null);

        ImageIcon splashPic = new ImageIcon("splash.png");
        JLabel picLabel = new JLabel(splashPic);
        picLabel.setVerticalAlignment(JLabel.TOP);
        picLabel.setBounds(0, 0, 500, 300);
        add(picLabel);

        infPanel.setBounds(0, 300, 500, 100);
        add(infPanel);
    }

    public static void main(String[] args) {
        SplashDialog dlg = new SplashDialog();
        dlg.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dlg.extendDialog(500, 400, 5, false);
    }

    public void extendDialog(int width, int height, int step, boolean keepCenter) {
        for (int i = 0; i < Math.max(width, height) / step; i++) {
            setSize(getWidth() < width ? getWidth() + step : width,
                    getHeight() < height ? getHeight() + step : height);
            if (keepCenter) setLocationRelativeTo(null);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLabel(String content) {
        infPanel.label.setText(content);
    }

    public void setProgress(int progress) {
        infPanel.progressBar.setValue(progress);
    }
}

class LoadingInformationPanel extends JPanel {
    JProgressBar progressBar = new JProgressBar(0, 100);
    JLabel label = new JLabel("加载中……");

    public LoadingInformationPanel() {
        setLayout(new GridLayout(0, 1));
        setSize(500, 100);

        label.setHorizontalAlignment(JLabel.CENTER);

        progressBar.setValue(50);

        add(progressBar);
        add(label);
    }


}
