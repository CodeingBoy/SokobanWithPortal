package sokoban.utils;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志记录工具
 */
public class Log {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
    private final static DefaultListModel logContainer = new DefaultListModel();

    public Log() {
    }

    private static String getTimeString() {
        return DATE_FORMAT.format(new Date());
    }

    @Deprecated
    public static void i(String content) {
        logContainer.addElement(getTimeString() + " info " + content);
    }

    public static void i(Class clazz, String content) {
        logContainer.addElement(getTimeString() + " info " + clazz.getName() + " " + content);
    }

    @Deprecated
    public static void d(String content) {
        logContainer.addElement(getTimeString() + " debug " + content);
    }

    public static void d(Class clazz, String content) {
        logContainer.addElement(getTimeString() + " debug " + clazz.getName() + " " + content);
    }

    @Deprecated
    public static void e(String content) {
        logContainer.addElement(getTimeString() + " error " + content);
    }

    public static void e(Class clazz, String content) {
        logContainer.addElement(getTimeString() + " error " + clazz.getName() + " " + content);
    }


    public static DefaultListModel getLogContainer() {
        return logContainer;
    }
}
