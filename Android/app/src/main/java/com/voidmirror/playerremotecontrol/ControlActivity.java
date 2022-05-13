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
    Button btnExit;
    Button btnFullscreen;
    Button btnSoundUp;
    Button btnSoundDown;
    NetController netController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        establishConnection();

        btnShiftLeft = findViewById(R.id.btnShiftLeft);
        btnShiftRight = findViewById(R.id.btnShiftRight);
        btnPause = findViewById(R.id.btnPause);
        btnExit = findViewById(R.id.btnExit);
        btnFullscreen = findViewById(R.id.btnFullscreen);
        btnSoundUp = findViewById(R.id.soundUp);
        btnSoundDown = findViewById(R.id.soundDown);

        btnShiftLeft.setOnClickListener(view -> {
//            Toast toast = Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT);
//            toast.show();
            netController.sendSignal("shiftLeft");
        });
        btnShiftRight.setOnClickListener(view -> {
            netController.sendSignal("shiftRight");
        });
        btnPause.setOnClickListener(view -> {
            netController.sendSignal("playPause");
        });
        btnExit.setOnClickListener(view -> {
            netController.sendSignal("closeServer");
            finish();
        });
        btnFullscreen.setOnClickListener(view -> {
            netController.sendSignal("fullscreen");
        });
        btnSoundUp.setOnClickListener(view -> {
            netController.sendSignal("soundUp");
        });
        btnSoundDown.setOnClickListener(view -> {
            netController.sendSignal("soundDown");
        });



    }

    @Override
    protected void onStop() {
        super.onStop();
        netController.sendSignal("closeConnection");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        establishConnection();
    }

    private void establishConnection() {
        netController = new NetController(this);
    }

    //    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_control);
//        btnShiftLeft = findViewById(R.id.btnShiftLeft);
//        btnShiftRight = findViewById(R.id.btnShiftRight);
//
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                netController = new NetController(ControlActivity.this);
//                Toast toast0 = Toast.makeText(getApplicationContext(), "CONTROLLER CREATED", Toast.LENGTH_SHORT);
//                toast0.show();
//                btnShiftLeft.setOnClickListener(view -> {
//                    Toast toast = Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT);
//                    toast.show();
//                    netController.SendSignal("shiftLeft");
//                });
//                btnShiftRight.setOnClickListener(view -> {
//                    Toast toast = Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT);
//                    toast.show();
//                });
//            }
//        });
//
//        thread.start();
//
//
//
//
//    }

}
