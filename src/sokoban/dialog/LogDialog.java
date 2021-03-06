package sokoban.dialog;

import sokoban.utils.Log;
import sokoban.utils.Settings;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 日志对话框
 */
public class LogDialog extends JFrame {
    private static LogDialog ourInstance = new LogDialog();
    private final JList<String> logs;
    private final DefaultListModel logContainer = Log.getLogContainer();
    private final JScrollPane jScrollPane;

    private LogDialog() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("日志");
        setLayout(new BorderLayout());
        setSize(800, 600);

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

        logs = new JList<>();
        logs.setFont(new Font("宋体", Font.PLAIN, 15));
        logs.setModel(logContainer);

        logContainer.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                JScrollBar sBar = jScrollPane.getVerticalScrollBar();
                jScrollPane.getViewport().setViewPosition(new Point(0, sBar.getMaximum()));
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {

            }

            @Override
            public void contentsChanged(ListDataEvent e) {

            }
        });

        jScrollPane = new JScrollPane(logs);

        add(jScrollPane);
        add(new ButtonPanel(), BorderLayout.SOUTH);
    }

    public static LogDialog getInstance() {
        return ourInstance;
    }


    public static void main(String[] args) {
        LogDialog.getInstance().setVisible(true);
        for (int i = 0; i < 100; i++) {
            Log.d(String.valueOf(i));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class ButtonPanel extends JPanel {
        public ButtonPanel() {
            setLayout(new GridLayout(0, 2));

            JButton btnClose = new JButton("关闭");
            btnClose.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getInstance().setVisible(false);
                    Settings.setShowLog(false);
                }
            });

            JButton btnClear = new JButton("清除");
            btnClear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logContainer.clear();
                }
            });

            add(btnClear);
            add(btnClose);
        }
    }
}
