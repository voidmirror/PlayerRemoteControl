package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ControlActivity extends Activity {

    Button btnShiftLeft;
    Button btnShiftRight;
    NetController netController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        netController = new NetController(this);
        btnShiftLeft = findViewById(R.id.btnShiftLeft);
        btnShiftRight = findViewById(R.id.btnShiftRight);
        btnShiftLeft.setOnClickListener(view -> {
            Toast toast = Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT);
            toast.show();
            netController.SendSignal("shiftLeft");
        });
        btnShiftRight.setOnClickListener(view -> {
            Toast toast = Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT);
            toast.show();
        });


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
