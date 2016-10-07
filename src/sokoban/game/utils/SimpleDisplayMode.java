package sokoban.game.utils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by CodeingBoy on 2016-7-10-0010.
 */
public class SimpleDisplayMode extends sokoban.game.utils.DisplayMode {
    public SimpleDisplayMode(java.awt.DisplayMode displayMode) {
        super(displayMode);
    }

    public SimpleDisplayMode(DisplayMode displayMode) {
        super(displayMode.size, displayMode.bitDepth, java.awt.DisplayMode.REFRESH_RATE_UNKNOWN);
    }

    public SimpleDisplayMode(Dimension size, int bitDepth, int refreshRate) {
        super(size, bitDepth, refreshRate);
    }

    public static SimpleDisplayMode[] getDisplayModes() {
        DisplayMode[] originDisplayModes = DisplayMode.getDisplayModes();

        ArrayList<SimpleDisplayMode> displayModes = new ArrayList<SimpleDisplayMode>();
        for (DisplayMode mode : originDisplayModes) {
            SimpleDisplayMode addingMode = new SimpleDisplayMode(mode);
            if (!displayModes.contains(addingMode))
                displayModes.add(addingMode);
        }

        return displayModes.toArray(new SimpleDisplayMode[0]);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DisplayMode)) return false;
        sokoban.game.utils.DisplayMode that = (sokoban.game.utils.DisplayMode) obj;

        if (this.size.equals(that.size))
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return (int) size.getWidth() + " x " + (int) size.getHeight();
    }
}
