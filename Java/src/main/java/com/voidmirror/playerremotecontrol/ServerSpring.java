package com.voidmirror.playerremotecontrol;

import com.voidmirror.playerremotecontrol.entities.*;
import com.voidmirror.playerremotecontrol.service.SignalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerSpring {

    @Autowired
    private SignalService signalService;

    @PostMapping(value = "/connect", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SignalResponse> connection(@RequestBody Signal signal) {
        System.out.println(signal);
        if (signal.getSystemModule() == SystemModule.SYSTEM && signal.getSignalType() == SignalType.CONNECTION) {
            return new ResponseEntity<>(SignalResponse.create().setResponseType(ResponseType.SYSTEM).setResponse("connect"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/connect-test", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void connectTest(HttpEntity<String> httpEntity) {
        System.out.println(httpEntity.getBody());
    }
    
    @PostMapping(value = "/signal", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void receiveSignal(@RequestBody Signal signal) {
        signalService.processSignal(signal);
    }

//    @PostMapping(value = "/check", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<String> checkingOnline(@RequestBody ControlCode code) {
//        System.out.println(code.getCode());
//        return new ResponseEntity<>("checkedOnline", HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/link", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<String> receiveLink(@RequestBody Link link) {
//        System.out.println(link.getLink());
//        return new ResponseEntity<>(new LinkExecutor().execute(link.getLink()), HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/timer", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<String> receiveTimer(@RequestBody Timer timer) {
//        System.out.println(timer.getTimerNum());
//        return new ResponseEntity<>(new TimerExecutor().execute(timer.getTimerNum()), HttpStatus.OK);
//    }
    
    @GetMapping(value = "/code")
    public String getCode() {
        System.out.println("### GET EXECUTED");
        return "YES";
    }

}
