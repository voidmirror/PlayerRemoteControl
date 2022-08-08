package com.voidmirror.playerremotecontrol;

import org.springframework.beans.factory.annotation.Autowired;
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
    private PlayerController playerController;

    @PostMapping(value = "/code", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> receiveCode(@RequestBody ControlCode code) {
        System.out.println(code.getCode());
        return new ResponseEntity<>(playerController.executeSignal(code.getCode()), HttpStatus.OK);
    }
    
    @GetMapping(value = "/code")
    public String getCode() {
        System.out.println("### GET EXECUTED");
        return "YES";
    }

}
