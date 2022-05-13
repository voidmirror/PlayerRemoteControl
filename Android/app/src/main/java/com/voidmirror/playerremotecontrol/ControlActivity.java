package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText editTextStop;

    NetController netController = null;

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

        editTextStop = findViewById(R.id.editTextStop);

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
            releaseBtnExit();
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
        System.out.println("### NETCONTOLLER " + netController + " ###");
        if (netController.getClientSocket().isConnected()) {
            netController.sendSignal("closeConnection");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        establishConnection();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (netController != null) {
//            netController.sendSignal("closeConnection");
//        }
//    }

    private void establishConnection() {
        System.out.println("### ESTABLISH ###");
        netController = new NetController(this);
    }

    private void releaseBtnExit() {
        if (editTextStop.getText().toString().equals("stop")) {
            netController.sendSignal("closeServer");
        } else {
            netController.sendSignal("closeConnection");
        }
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
