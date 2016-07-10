package sokoban.utils;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CodeingBoy on 2016-7-9-0009.
 */
public class Log {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
    private final static DefaultListModel logContainer = new DefaultListModel();

    public Log() {
    }

    private static String getTimeString() {
        return DATE_FORMAT.format(new Date());
    }

    public static void i(String content) {
        logContainer.addElement(getTimeString() + " info " + content);
    }

    public static void d(String content) {
        logContainer.addElement(getTimeString() + " debug " + content);
    }

    public static void e(String content) {
        logContainer.addElement(getTimeString() + " error " + content);
    }

    public static DefaultListModel getLogContainer() {
        return logContainer;
    }
}
