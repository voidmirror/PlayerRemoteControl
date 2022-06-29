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
    Button btnSoundUpInternal;
    Button btnSoundDownInternal;
    NetController netController;
    HttpController httpController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        httpController = new HttpController(getApplicationContext());
        httpController.setHost("http://192.168.0.79:4077");




        establishConnection();

        btnShiftLeft = findViewById(R.id.btnShiftLeft);
        btnShiftRight = findViewById(R.id.btnShiftRight);
        btnPause = findViewById(R.id.btnPause);
        btnConnect = findViewById(R.id.btnConnect);
        btnFullscreen = findViewById(R.id.btnFullscreen);
        btnSoundUp = findViewById(R.id.soundUp);
        btnSoundDown = findViewById(R.id.soundDown);
//        btnSoundUpInternal = findViewById(R.id.soundUpInternal);
//        btnSoundDownInternal = findViewById(R.id.soundDownInternal);

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

        // TODO: change button or remove
//        btnExit.setEnabled(false);
//        btnExit.setOnClickListener(view -> {
//            netController.sendSignal("closeServer");
//            finish();
//        });

        btnFullscreen.setOnClickListener(view -> {
            netController.sendSignal("fullscreen");
        });
        btnSoundUp.setOnClickListener(view -> {
            netController.sendSignal("soundUp");
        });
        btnSoundDown.setOnClickListener(view -> {
            netController.sendSignal("soundDown");
        });
//        btnSoundUpInternal.setOnClickListener(view -> {
//            netController.sendSignal("soundUpInternal");
//        });
//        btnSoundDownInternal.setOnClickListener(view -> {
//            netController.sendSignal("soundDownInternal");
//        });

        btnConnect.setOnClickListener(view -> {
            httpController.establishConnection();
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


}
