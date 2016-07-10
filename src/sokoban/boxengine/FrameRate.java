package sokoban.boxengine;

import sokoban.utils.Log;

/**
 * Created by CodeingBoy on 2016-7-10-0010.
 */
public class FrameRate {
    private String latestFrameRateString = "FPS 0";
    private String formatString = "FPS %s";
    private int latestFrameRate = 0;
    private long delta = 0;
    private long lasttime;
    private int frameCount = 0;
    private boolean shouldLog = false;
    private boolean isInitalized = false;

    public FrameRate() {

    }

    public void initialize(){
        lasttime = System.currentTimeMillis();
        isInitalized = true;
    }

    public void calculate() {
        long currentTime = System.currentTimeMillis();
        delta += currentTime - lasttime;
        lasttime = currentTime;
        frameCount++;

        if (delta >= 1000) {
            delta -= 1000;
            latestFrameRate = frameCount;
            latestFrameRateString = String.format(formatString, latestFrameRate);
            if (shouldLog)
                Log.i(getLatestFrameRateString());

            frameCount = 0;
        }
    }

    public String getLatestFrameRateString() {
        return latestFrameRateString;
    }

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public void setShouldLog(boolean shouldLog) {
        this.shouldLog = shouldLog;
    }

    public boolean isInitalized() {
        return isInitalized;
    }
}
