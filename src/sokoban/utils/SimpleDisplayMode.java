package sokoban.utils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by CodeingBoy on 2016-7-10-0010.
 */
public class SimpleDisplayMode extends DisplayMode {
    public SimpleDisplayMode(java.awt.DisplayMode displayMode) {
        super(displayMode);
    }

    public SimpleDisplayMode(Dimension size, int bitDepth, int refreshRate) {
        super(size, bitDepth, refreshRate);
    }

    public static SimpleDisplayMode[] getDisplayModes() {
        java.awt.DisplayMode[] originDisplayModes =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayModes();

        ArrayList<SimpleDisplayMode> displayModes = new ArrayList<SimpleDisplayMode>();
        for (java.awt.DisplayMode mode : originDisplayModes) {
            if (!displayModes.contains(mode))
                displayModes.add(new SimpleDisplayMode(mode));
        }

        return displayModes.toArray(new SimpleDisplayMode[0]);
    }

    @Override
    public boolean equals(Object obj) {
        DisplayMode another = (DisplayMode) obj;
        if (this.size == another.size)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return (int)size.getWidth() + " x " + (int)size.getHeight();
    }
}
