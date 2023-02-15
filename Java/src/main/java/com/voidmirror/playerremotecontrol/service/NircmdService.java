package com.voidmirror.playerremotecontrol.service;

import com.voidmirror.playerremotecontrol.entities.Signal;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NircmdService {

    private static Boolean NIR_HAS_PATH = false;

    NircmdService() {
        String path = System.getenv("PATH");
        List<String> nircmd = Arrays.stream(path.split(";"))
                .map(String::toLowerCase)
                .filter(s -> s.contains("nircmd"))
                .collect(Collectors.toList());
        System.out.println(nircmd);
        if (!nircmd.isEmpty()) {
            NIR_HAS_PATH = true;
        }
    }

    public void processSignal(Signal signal) {
        if (NIR_HAS_PATH) {
            try {
                switch (signal.getSignal()) {
                    case "systemVolumeUp":
                        Runtime.getRuntime().exec("nircmd" + " changesysvolume 1311");
                        break;
                    case "systemVolumeDown":
                        Runtime.getRuntime().exec("nircmd" + " changesysvolume -1311");
                        break;
                    case "systemVolumeToggleMute":
                        Runtime.getRuntime().exec("nircmd" + " mutesysvolume 2");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
