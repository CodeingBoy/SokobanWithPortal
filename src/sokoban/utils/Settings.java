package sokoban.utils;

import sokoban.game.utils.DisplayMode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * 设置持久化类
 */
public class Settings {
    public final static String KEY_DEBUGMODE = "debug.debugging";
    public final static String KEY_SHOWFRMMERATE = "debug.showFrameRate";
    public final static String KEY_SHOWLOG = "debug.showLog";
    public final static String KEY_FULLSCREEN = "graphics.fullScreen";
    public final static String KEY_RESOLUTION = "graphics.resolution";
    public final static String KEY_BITDEPTH = "graphics.bitDepth";
    public final static String KEY_REFRESHRATE = "graphics.refreshRate";
    public final static String KEY_BGMVOLUME = "sound.bgmVolume";
    public final static String KEY_SEVOLUME = "sound.seVolume";
    private final static String FILENAME = "settings.properties";
    private final static Properties PROPERTIES = new Properties();
    private static ArrayList<ActionListener> actionListeners = new ArrayList<>();

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

    private static Object setProperty(String key, String value) {
        Object o = PROPERTIES.setProperty(key, value);
        excuteListener(key);
        return o;
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
        setProperty(KEY_DEBUGMODE, Boolean.toString(isDebugMode));
    }

    public static boolean shouldShowFrameRate() {
        String s = PROPERTIES.getProperty(KEY_SHOWFRMMERATE, "false");
        return Boolean.parseBoolean(s);
    }

    public static void setShowFrameRate(boolean shouldShowFrameRate) {
        setProperty(KEY_SHOWFRMMERATE, Boolean.toString(shouldShowFrameRate));
    }

    public static boolean shouldLogWindow() {
        String s = PROPERTIES.getProperty(KEY_SHOWLOG, "false");
        return Boolean.parseBoolean(s);
    }

    public static void setShowLog(boolean shouldLogWindow) {
        setProperty(KEY_SHOWLOG, Boolean.toString(shouldLogWindow));
    }

    public static boolean isFullScreen() {
        String s = PROPERTIES.getProperty(KEY_FULLSCREEN, "false");
        return Boolean.parseBoolean(s);
    }

    public static void setFullScreen(boolean isFullScreen) {
        setProperty(KEY_FULLSCREEN, Boolean.toString(isFullScreen));
    }

    public static Dimension getResolution() {
        String s = PROPERTIES.getProperty(KEY_RESOLUTION, "");
        return new Dimension(Integer.parseInt(s.split("x")[0]), Integer.parseInt(s.split("x")[1]));
    }

    public static void setResolution(DisplayMode displayMode) {
        setProperty(KEY_RESOLUTION, displayMode.getSize().width + "x" + displayMode.getSize().height);
        setProperty(KEY_BITDEPTH, String.valueOf(displayMode.getBitDepth()));
        setProperty(KEY_REFRESHRATE, String.valueOf(displayMode.getRefreshRate()));
    }

    public static DisplayMode getDisplayMode() {
        Dimension size = getResolution();
        int bitDepth = Integer.parseInt(PROPERTIES.getProperty(KEY_BITDEPTH, "32"));
        int refreshRate = Integer.parseInt(PROPERTIES.getProperty(KEY_REFRESHRATE, "0"));

        return new DisplayMode(size, bitDepth, refreshRate);
    }

    public static int getBGMVolume() {
        String s = PROPERTIES.getProperty(KEY_BGMVOLUME, "50");
        return Integer.parseInt(s);
    }

    public static void setBGMVolume(int BGMVolume) {
        setProperty(KEY_BGMVOLUME, String.valueOf(BGMVolume));
    }

    public static int getSEVolume() {
        String s = PROPERTIES.getProperty(KEY_SEVOLUME, "50");
        return Integer.parseInt(s);
    }

    public static void setSEVolume(int SEVolume) {
        setProperty(KEY_SEVOLUME, String.valueOf(SEVolume));
    }

    public static void addUpdateListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private static void excuteListener(String key) {
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(new ActionEvent(new Object(), ActionEvent.ACTION_PERFORMED, key));
        }
    }

    public static void removeUpdateListener(ActionListener actionListener) {
        actionListeners.remove(actionListener);
    }

    public static void logSettings() {
        Log.d("Settings information:");
        for (Map.Entry e : PROPERTIES.entrySet()) {
            Log.d(e.getKey() + "=" + e.getValue());
        }
    }
}
