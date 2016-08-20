package sokoban.utils;

import java.awt.*;
import java.io.*;
import java.util.Properties;

public class Settings {
    private final static String FILENAME = "settings.properties";
    private final static Properties PROPERTIES = new Properties();

    private final static String KEY_DEBUGMODE = "debug.debugging";
    private final static String KEY_SHOWFRMMERATE = "debug.showFrameRate";
    private final static String KEY_SHOWLOG = "debug.showLog";
    private final static String KEY_FULLSCREEN = "graphics.fullScreen";
    private final static String KEY_RESOLUTION = "graphics.resolution";

    static {
        loadSettings();
    }

    private Settings() {
    }

    private static void loadSettings() {
        if (!isSettingsExist()) {
            return;
        }

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(FILENAME))) {
            PROPERTIES.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void saveSettings() {
        // create settings file if not exist
        File file;
        file = new File(FILENAME);
        if (!isSettingsExist()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            PROPERTIES.store(writer, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isSettingsExist() {
        File file = new File(FILENAME);
        return file.exists();
    }

    public static boolean isDebugMode() {
        String s = PROPERTIES.getProperty(KEY_DEBUGMODE, "false");
        return Boolean.parseBoolean(s);
    }

    public static void setDebugMode(boolean isDebugMode) {
        PROPERTIES.setProperty(KEY_DEBUGMODE, Boolean.toString(isDebugMode));
    }

    public static boolean shouldShowFrameRate() {
        String s = PROPERTIES.getProperty(KEY_SHOWFRMMERATE, "false");
        return Boolean.parseBoolean(s);
    }

    public static void setShowFrameRate(boolean shouldShowFrameRate) {
        PROPERTIES.setProperty(KEY_SHOWFRMMERATE, Boolean.toString(shouldShowFrameRate));
    }

    public static boolean shouldLogWindow() {
        String s = PROPERTIES.getProperty(KEY_SHOWLOG, "false");
        return Boolean.parseBoolean(s);
    }

    public static void setShowLog(boolean shouldLogWindow) {
        PROPERTIES.setProperty(KEY_SHOWLOG, Boolean.toString(shouldLogWindow));
    }

    public static boolean isFullScreen() {
        String s = PROPERTIES.getProperty(KEY_FULLSCREEN, "false");
        return Boolean.parseBoolean(s);
    }

    public static void setFullScreen(boolean isFullScreen) {
        PROPERTIES.setProperty(KEY_FULLSCREEN, Boolean.toString(isFullScreen));
    }

    public static Dimension getResolution() {
        String s = PROPERTIES.getProperty(KEY_RESOLUTION, "");
        return new Dimension(Integer.parseInt(s.split("x")[0]), Integer.parseInt(s.split("x")[1]));
    }

    public static void setResolution(DisplayMode displayMode) {
        PROPERTIES.setProperty(KEY_RESOLUTION, displayMode.getWidth() + "x" + displayMode.getWidth());
    }


}
