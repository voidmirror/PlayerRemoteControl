package com.voidmirror.playerremotecontrol.service;

import com.voidmirror.playerremotecontrol.KeyboardController;
import com.voidmirror.playerremotecontrol.entities.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeModuleService {

    @Autowired
    private KeyboardController keyboardController;

    public void processSignal(Signal signal) {
        switch (signal.getSignalType()) {
            case PLAYER:
                keyboardController.executeSignal(signal.getSignal());
                break;
        }
    }

}
