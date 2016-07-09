package sokoban.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by CodeingBoy on 2016-7-7-0007.
 */
public class SetttingDialog extends JDialog {

    private final ConfirmPanel confirmPanel;

    public SetttingDialog(JFrame modalDlg) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(modalDlg);
        setModal(true);
        setResizable(false);
        setTitle("游戏设置");
        setLayout(new BorderLayout());

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

        add(new SettingGridPanel(), BorderLayout.NORTH);
        add(new CheckBoxPanel());
        add(confirmPanel, BorderLayout.SOUTH);

        pack();
    }

    public static void main(String[] args) {
        new SetttingDialog(null).setVisible(true);
    }

    private void load() {

    }

    private void save() {

    }

    private class SettingGridPanel extends JPanel {
        private final JSlider volume = new JSlider();

        public SettingGridPanel() {
            setLayout(new GridLayout(0, 2, 5, 5));

            add(new JLabel("分辨率：", JLabel.RIGHT));
            add(new JComboBox<String>());
            add(new JLabel("音效：", JLabel.RIGHT));
            add(volume);

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
        private final JCheckBox fullScreen = new JCheckBox("全屏模式运行");

        public CheckBoxPanel() {
            setLayout(new GridLayout(0, 3, 5, 5));

            add(fullScreen);
        }
    }
}
