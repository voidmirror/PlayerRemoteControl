package com.voidmirror.playerremotecontrol;

import com.voidmirror.playerremotecontrol.entities.Signal;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LinkExecutor implements SignalProcessor {

    @Override
    public void processSignal(Signal signal) {
        execute(signal.getSignal());
    }

    private String execute(String link) {
        try {
            Runtime.getRuntime().exec("explorer " + link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
