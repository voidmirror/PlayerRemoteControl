package com.voidmirror.playerremotecontrol;

import java.io.IOException;
import java.util.logging.Logger;

public class TimerExecutor implements Executor {

    private String timerSettings;

    public String getTimerSettings() {
        return timerSettings;
    }

    public void setTimerSettings(String timerNum) {
        this.timerSettings = timerSettings;
    }

    @Override
    public String execute(String timerNum) {

        try {
            if (Integer.parseInt(timerNum) == -1) {
                Runtime.getRuntime().exec("shutdown /a");
                return "Computer shutdown canceled";
            } else if (Integer.parseInt(timerNum) == 0) {
                Runtime.getRuntime().exec("shutdown /s");
                return "Shutting down now";
            } else {
                Runtime.getRuntime().exec("shutdown /s /f /t " + Integer.parseInt(timerNum));
                return "Shutdown in " + Integer.parseInt(timerNum) / 60 + " minutes";
            }
        } catch (IOException e) {
            Logger.getAnonymousLogger().info("Timer not stated");
            e.printStackTrace();
        }

        return "ERROR: Timer error";

    }

}
