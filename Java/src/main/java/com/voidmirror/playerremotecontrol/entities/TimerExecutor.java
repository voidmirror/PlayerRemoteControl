package com.voidmirror.playerremotecontrol.entities;

import java.io.IOException;
import java.util.logging.Logger;

public class TimerExecutor {

    private String timerSettings;

    public String getTimerSettings() {
        return timerSettings;
    }

    public void setTimerSettings(String timerSettings) {
        this.timerSettings = timerSettings;
    }

    public void executeTimer() {

        try {
            Runtime.getRuntime().exec("");
        } catch (IOException e) {
            Logger.getAnonymousLogger().info("Timer not stated");
            e.printStackTrace();
        }

    }

}
