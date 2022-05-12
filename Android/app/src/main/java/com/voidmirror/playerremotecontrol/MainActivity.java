package com.voidmirror.playerremotecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnYoutube = findViewById(R.id.btnYoutube);
        btnYoutube.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ControlActivity.class);
        startActivity(intent);
        Toast t = Toast.makeText(getApplicationContext(), "Intent started", Toast.LENGTH_SHORT);
        t.show();
    }
}