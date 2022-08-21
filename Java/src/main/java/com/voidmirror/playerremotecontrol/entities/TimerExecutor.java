package com.voidmirror.playerremotecontrol.entities;

import java.io.IOException;
import java.util.logging.Logger;

public class TimerExecutor {

    private String timerSettings;

    public String getTimerSettings() {
        return timerSettings;
    }

    public void setTimerSettings(String timerNum) {
        this.timerSettings = timerSettings;
    }

    public String executeTimer(String timerNum) {

        try {
            if (Integer.parseInt(timerNum) == -1) {
                Runtime.getRuntime().exec("shutdown /a");
                return "Computer shutdown canceled";
            } else {
                Runtime.getRuntime().exec("shutdown /s /f /t " + Integer.parseInt(timerNum));
                return "Shutdown in " + Integer.parseInt(timerNum) / 60 + " minutes";
            }
        } catch (IOException e) {
            Logger.getAnonymousLogger().info("Timer not stated");
            e.printStackTrace();
        }

        return "ERROR: Shutdown error";

    }

}
