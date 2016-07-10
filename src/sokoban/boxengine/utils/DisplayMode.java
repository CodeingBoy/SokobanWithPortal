package sokoban.boxengine.utils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by CodeingBoy on 2016-7-10-0010.
 */
public class DisplayMode {
    protected Dimension size;
    protected int bitDepth;
    protected int refreshRate;

    public DisplayMode(Dimension size, int bitDepth, int refreshRate) {
        this.size = size;
        this.bitDepth = bitDepth;
        this.refreshRate = refreshRate;
    }

    public DisplayMode(java.awt.DisplayMode displayMode) {
        this(new Dimension(displayMode.getWidth(), displayMode.getHeight()), displayMode.getBitDepth(), displayMode.getRefreshRate());
    }

    public static DisplayMode[] getDisplayModes() {
        java.awt.DisplayMode[] originDisplayModes =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayModes();

        ArrayList<DisplayMode> displayModes = new ArrayList<DisplayMode>();
        for (java.awt.DisplayMode mode : originDisplayModes) {
            displayModes.add(new DisplayMode(mode));
        }

        return displayModes.toArray(new DisplayMode[0]);
    }

    public static void main(String[] args) {
        for (DisplayMode mode : getDisplayModes()
                ) {
            System.out.println(mode);
        }
    }

    public Dimension getSize() {
        return size;
    }

    public int getBitDepth() {
        return bitDepth;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DisplayMode{");
        sb.append("size=").append(size);
        sb.append(", bitDepth=").append(bitDepth);
        sb.append(", refreshRate=").append(refreshRate);
        sb.append('}');
        return sb.toString();
    }
}
