package com.voidmirror.playerremotecontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerSpring {

    @Autowired
    private PlayerController playerController;

    @PostMapping(value = "/code", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void receiveCode(@RequestBody ControlCode code) {
        playerController.executeSignal(code.getCode());
    }

    @GetMapping(value = "/code")
    public String getCode() {
//        playerController.executeSignal("fullscreen");
        System.out.println("### GET EXECUTED");
        return "YES";
    }

}
