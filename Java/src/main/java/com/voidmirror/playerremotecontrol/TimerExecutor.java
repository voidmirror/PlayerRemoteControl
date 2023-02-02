package com.voidmirror.playerremotecontrol;

import com.voidmirror.playerremotecontrol.entities.Signal;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

@Service
public class TimerExecutor implements SignalProcessor {

    private String timerSettings;

    public String getTimerSettings() {
        return timerSettings;
    }

    public void setTimerSettings(String timerNum) {
        this.timerSettings = timerSettings;
    }

    @Override
    public void processSignal(Signal signal)  {
        execute(Integer.parseInt(signal.getSignal()));
    }

    private String execute(Integer timerNum) {

        try {
            if (timerNum == -1) {
                Runtime.getRuntime().exec("shutdown /a");
                return "Computer shutdown canceled";
            } else if (timerNum == 0) {
                Runtime.getRuntime().exec("shutdown /s");
                return "Shutting down now";
            } else {
                Runtime.getRuntime().exec("shutdown /s /f /t " + timerNum);
                return "Shutdown in " + timerNum / 60 + " minutes";
            }
        } catch (IOException e) {
            Logger.getAnonymousLogger().info("Timer not stated");
            e.printStackTrace();
        }

        return "ERROR: Timer error";

    }

}
