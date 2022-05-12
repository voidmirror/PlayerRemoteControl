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

}
