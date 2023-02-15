package com.voidmirror.playerremotecontrol.service;

import com.voidmirror.playerremotecontrol.TimerExecutor;
import com.voidmirror.playerremotecontrol.entities.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemModuleService {

    @Autowired
    private TimerExecutor timerExecutor;
    @Autowired
    private NircmdService nircmdService;

    public void processSignal(Signal signal) {
        switch (signal.getSignalType()) {
            case TIMER:
                timerExecutor.processSignal(signal);
                break;
            case NIR:
                nircmdService.processSignal(signal);
                break;
        }
    }

}
