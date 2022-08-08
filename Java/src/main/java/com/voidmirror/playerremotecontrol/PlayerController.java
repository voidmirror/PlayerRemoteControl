package com.voidmirror.playerremotecontrol;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

@Service
public class PlayerController {

    private Robot robot;

    public PlayerController() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public String executeSignal(String signal) {

        switch (signal) {
            case "shiftLeft":
                robot.keyPress(KeyEvent.VK_J);
                robot.keyRelease(KeyEvent.VK_J);
                break;
            case "shiftRight":
                robot.keyPress(KeyEvent.VK_L);
                robot.keyRelease(KeyEvent.VK_L);
                break;
            case "playPause":
                robot.keyPress(KeyEvent.VK_K);
                robot.keyRelease(KeyEvent.VK_K);
                break;
            case "fullscreen":
                robot.keyPress(KeyEvent.VK_F);
                robot.keyRelease(KeyEvent.VK_F);
                break;
            case "soundUp":
                robot.keyPress(KeyEvent.VK_UP);
                robot.keyRelease(KeyEvent.VK_UP);
                break;
            case "soundDown":
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                break;
            case "startTimer":
                try {
                    Runtime.getRuntime().exec("C:\\Zed\\Scripts\\Shutdown\\shut.cmd");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "checkOnline":
                return "Is Online";
        }

        return "ok";

    }

}
