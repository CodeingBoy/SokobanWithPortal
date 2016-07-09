package sokoban.utils;

import sokoban.dialog.LogDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CodeingBoy on 2016-7-9-0009.
 */
public class Log {
    private static final LogDialog logDialog = LogDialog.getInstance();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSSS");

    public Log() {
    }

    private static String getTimeString(){
        return DATE_FORMAT.format(new Date());
    }

    public static void i(String content) {
        logDialog.addLog(getTimeString() + " info " + content);
    }

    public static void d(String content) {
        logDialog.addLog(getTimeString() + " debug " + content);
    }

    public static void e(String content) {
        logDialog.addLog(getTimeString() + " error " + content);
    }
}
