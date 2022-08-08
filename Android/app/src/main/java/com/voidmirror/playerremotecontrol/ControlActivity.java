package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ControlActivity extends Activity {

    Button btnShiftLeft;
    Button btnShiftRight;
    Button btnPause;
    Button btnConnect;
    Button btnFullscreen;
    Button btnSoundUp;
    Button btnSoundDown;
    HttpController httpController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        httpController = new HttpController(this);
        httpController.setHost("http://192.168.0.79:4077/code"); // wireless
//        httpController.setHost("http://192.168.0.46:4077/code"); // lan
//        httpController.setHost("http://192.168.43.1:4077"); // androidAP


        btnShiftLeft = findViewById(R.id.btnShiftLeft);
        btnShiftRight = findViewById(R.id.btnShiftRight);
        btnPause = findViewById(R.id.btnPause);
        btnConnect = findViewById(R.id.btnConnect);
        btnFullscreen = findViewById(R.id.btnFullscreen);
        btnSoundUp = findViewById(R.id.soundUp);
        btnSoundDown = findViewById(R.id.soundDown);

        btnShiftLeft.setOnClickListener(view -> {
            httpController.sendSignal("shiftLeft");
        });
        btnShiftRight.setOnClickListener(view -> {
            httpController.sendSignal("shiftRight");
        });
        btnPause.setOnClickListener(view -> {
            httpController.sendSignal("playPause");
        });
        btnFullscreen.setOnClickListener(view -> {
            httpController.sendSignal("fullscreen");
        });
        btnSoundUp.setOnClickListener(view -> {
            httpController.sendSignal("soundUp");
        });
        btnSoundDown.setOnClickListener(view -> {
            httpController.sendSignal("soundDown");
        });
        btnConnect.setOnClickListener(view -> {
            httpController.sendSignal("startTimer");
        });

    }

}
