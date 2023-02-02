package com.voidmirror.playerremotecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.voidmirror.playerremotecontrol.controller.HttpController;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Button btnYoutube;
    Button btnSearchHost;

    HttpController httpController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpController = HttpController.getInstance(this);

        btnYoutube = findViewById(R.id.btnYoutube);
        btnYoutube.setEnabled(false);
        btnSearchHost = findViewById(R.id.btnSearchHost);
        btnYoutube.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ControlActivity.class);
            startActivity(intent);
        });

        System.out.println("### HttpController: " + httpController);
        if (httpController == null) {
            waitWifiConnection();
        }

        btnSearchHost.setOnClickListener(view -> {
            httpController.findConnection(response -> btnYoutube.setEnabled(true));
        });

    }

    private void waitWifiConnection() {
        btnSearchHost.setEnabled(false);
        Observable.create(subscriber -> {
            while (this.httpController == null) {
                Thread.sleep(5000);
                this.httpController = HttpController.getInstance(this);
            }

        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe();
    }

}