package com.voidmirror.playerremotecontrol;

import com.voidmirror.playerremotecontrol.entities.ControlCode;
import com.voidmirror.playerremotecontrol.entities.Link;
import com.voidmirror.playerremotecontrol.entities.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;

@RestController
public class ServerSpring {

    @Autowired
    private PlayerController playerController;
    
    @PostMapping(value = "/code", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void receiveCode(@RequestBody ControlCode code) {
        System.out.println(code.getCode());
        playerController.executeSignal(code.getCode());
    }

    @PostMapping(value = "/check", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> checkingOnline(@RequestBody ControlCode code) {
        System.out.println(code.getCode());
        return new ResponseEntity<>("checkedOnline", HttpStatus.OK);
    }

    @PostMapping(value = "/link", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> receiveLink(@RequestBody Link link) {
        System.out.println(link.getLink());
        return new ResponseEntity<>(new LinkExecutor().execute(link.getLink()), HttpStatus.OK);
    }

    @PostMapping(value = "/timer", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> receiveTimer(@RequestBody Timer timer) {
        System.out.println(timer.getTimerNum());
        return new ResponseEntity<>(new TimerExecutor().execute(timer.getTimerNum()), HttpStatus.OK);
    }
    
    @GetMapping(value = "/code")
    public String getCode() {
        System.out.println("### GET EXECUTED");
        return "YES";
    }

}
