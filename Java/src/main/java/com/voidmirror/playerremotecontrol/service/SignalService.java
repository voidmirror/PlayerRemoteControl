package com.voidmirror.playerremotecontrol.service;

import com.voidmirror.playerremotecontrol.entities.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignalService {

    @Autowired
    private YoutubeModuleService youtubeModuleService;
    @Autowired
    private SystemModuleService systemModuleService;
    @Autowired
    private BrowserService browserService;

    public void processSignal(Signal signal) {
        switch (signal.getSystemModule()) {
            case SYSTEM:
                systemModuleService.processSignal(signal);
            case YOUTUBE:
                youtubeModuleService.processSignal(signal);
                break;
            case TWITCH:
                // do nothing
                break;
            case BROWSER:
                browserService.processSignal(signal);
        }
    }

}
