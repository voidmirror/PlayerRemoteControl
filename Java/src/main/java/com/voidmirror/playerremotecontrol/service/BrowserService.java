package com.voidmirror.playerremotecontrol.service;

import com.voidmirror.playerremotecontrol.KeyboardController;
import com.voidmirror.playerremotecontrol.LinkExecutor;
import com.voidmirror.playerremotecontrol.entities.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrowserService {

    @Autowired
    private KeyboardController keyboardController;
    @Autowired
    private LinkExecutor linkExecutor;

    public void processSignal(Signal signal) {
        switch (signal.getSignalType()) {
            case TAB:
                keyboardController.executeSignal(signal.getSignal());
                break;
            case LINK:
                linkExecutor.processSignal(signal);
                break;
        }
    }

}
