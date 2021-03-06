package sokoban.dialog;

import sokoban.game.engine.GameWindow;
import sokoban.scenes.mainmenu.MainMenu;
import sokoban.utils.Log;
import sokoban.utils.Settings;

import javax.swing.*;
import java.awt.*;

/**
 * 启动画面对话框
 */
public class SplashDialog extends JFrame {
    final LoadingInformationPanel infPanel;

    public SplashDialog() {
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setTitle("推箱子-载入中……");
        setLocationRelativeTo(null);
        setLayout(null);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        infPanel = new LoadingInformationPanel();

        ImageIcon splashPic = new ImageIcon("splash.png");
        JLabel picLabel = new JLabel(splashPic);
        picLabel.setVerticalAlignment(JLabel.TOP);
        picLabel.setBounds(0, 0, 500, 300);
        add(picLabel);

        infPanel.setBounds(0, 300, 500, 100);
        add(infPanel);

        Log.d("Splash loaded!");
    }

    public static void main(String[] args) {
        if (Settings.shouldLogWindow()) {
            LogDialog.getInstance().setVisible(true);
        }

        Settings.logSettings();

        // SplashDialog dlg = new SplashDialog();
        // dlg.setVisible(true);
        // try {
        //     Thread.sleep(2000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // dlg.expand(500, 400, 5, false);
        //
        // try {
        //     Thread.sleep(2000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        //
        // dlg.setVisible(false);
        // dlg.dispose();

        if (!Settings.isSettingsExist()) {
            JOptionPane.showMessageDialog(null, "欢迎您游玩本游戏！\n" +
                    "程序没有检测到配置文件，稍后将显示设置对话框，您可以点击确定使用默认配置游戏。\n" +
                    "保存设置后，您可在主菜单界面呼出设置窗口进行游戏设置", "欢迎！", JOptionPane.INFORMATION_MESSAGE);
            new SettingDialog(null).setVisible(true);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameWindow window = new GameWindow(new Dimension(800, 600), "推箱子", new MainMenu());
                window.showWindow();
            }
        });
    }

    public void expand(int width, int height, int step, boolean keepCenter) {
        for (int i = 0; i < Math.max(width, height) / step; i++) {
            setSize(getWidth() < width ? getWidth() + step : width,
                    getHeight() < height ? getHeight() + step : height);
            if (keepCenter) setLocationRelativeTo(null);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Log.d(e.getLocalizedMessage());
            }
        }
        Log.d("Splash expanded!");
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
        progressBar.setSize(500, 20);

        add(progressBar);
        add(label);
    }


}
