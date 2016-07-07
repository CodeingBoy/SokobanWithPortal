package sokoban.dialog;

import javax.swing.*;

/**
 * Created by CodeingBoy on 2016-7-7-0007.
 */
public class SplashDialog extends JDialog {

    public static void main(String[] args) {
        new SplashDialog().setVisible(true);
    }

    public SplashDialog() {
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400,300);
        setTitle("推箱子-载入中……");

    }
}
