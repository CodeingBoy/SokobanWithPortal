package sokoban.dialog;

import sokoban.game.utils.SimpleDisplayMode;
import sokoban.utils.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by CodeingBoy on 2016-7-7-0007.
 */
public class SettingDialog extends JDialog {
    private final ConfirmPanel confirmPanel;
    private final SettingGridPanel settingGridPanel = new SettingGridPanel();
    private final CheckBoxPanel checkBoxPanel = new CheckBoxPanel();

    public SettingDialog(JFrame modalDlg) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(modalDlg);
        setModal(true);
        setResizable(false);
        setTitle("游戏设置");
        setLayout(new BorderLayout(0, 0));

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

        confirmPanel = new ConfirmPanel();
        confirmPanel.btnNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        confirmPanel.btnYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
                dispose();
            }
        });

        load();

        add(settingGridPanel, BorderLayout.NORTH);
        add(checkBoxPanel);
        add(confirmPanel, BorderLayout.SOUTH);

        pack();
    }

    public static void main(String[] args) {
        new SettingDialog(null).setVisible(true);
        Settings.saveSettings();
    }

    private void load() {
        settingGridPanel.load();
        checkBoxPanel.load();
    }

    private void save() {
        settingGridPanel.save();
        checkBoxPanel.save();
    }

    private class SettingGridPanel extends JPanel {
        private final JCheckBox fullScreen = new JCheckBox("全屏模式运行");
        private final JComboBox<SimpleDisplayMode> displayModeJComboBox = new JComboBox<SimpleDisplayMode>(SimpleDisplayMode.getDisplayModes());
        private final JSlider bgmVolume = new JSlider();
        private final JSlider volume = new JSlider();

        public SettingGridPanel() {
            setLayout(new GridLayout(0, 2, 5, 5));

            add(fullScreen);
            add(displayModeJComboBox);
            add(new JLabel("背景音乐音量：", JLabel.RIGHT));
            add(bgmVolume);
            add(new JLabel("动作音效音量：", JLabel.RIGHT));
            add(volume);

            fullScreen.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    JCheckBox checkBox = (JCheckBox) e.getItem();
                    displayModeJComboBox.setEnabled(checkBox.isSelected());
                }
            });
        }

        void load() {
            fullScreen.setSelected(Settings.isFullScreen());

        }

        void save() {
            Settings.setFullScreen(fullScreen.isSelected());
        }
    }

    private class ConfirmPanel extends JPanel {
        private final JButton btnYes = new JButton("确认");
        private final JButton btnNo = new JButton("取消");

        public ConfirmPanel() {
            setLayout(new GridLayout(0, 2));
            add(btnNo);
            add(btnYes);
        }
    }

    private class CheckBoxPanel extends JPanel {
        private final JCheckBox debuggingMode = new JCheckBox("调试模式");
        private final JCheckBox showLogs = new JCheckBox("显示日志");


        public CheckBoxPanel() {
            setLayout(new GridLayout(0, 3, 5, 5));

            add(debuggingMode);
            add(showLogs);
        }

        void load() {
            debuggingMode.setSelected(Settings.isDebugMode());
            showLogs.setSelected(Settings.shouldLogWindow());
        }

        void save() {
            Settings.setDebugMode(debuggingMode.isSelected());
            Settings.setShowLog(showLogs.isSelected());
        }
    }
}
