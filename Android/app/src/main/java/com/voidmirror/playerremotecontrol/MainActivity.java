package com.voidmirror.playerremotecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.reactivestreams.Subscription;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    Button btnYoutube;
    Button btnSearchHost;

    NetSearch netSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpController httpController = HttpController.getInstance();
        btnYoutube = findViewById(R.id.btnYoutube);
        btnYoutube.setEnabled(false);
        btnSearchHost = findViewById(R.id.btnSearchHost);
        btnYoutube.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ControlActivity.class);
            startActivity(intent);
        });

        btnSearchHost.setOnClickListener(view -> {
            netSearch = new NetSearch(this);
            netSearch.reallySearch();
        });

    }

}