package com.voidmirror.playerremotecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    Button btnYoutube;
    private static String host = "192.168.0.79";
    private static int port = 4077;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnYoutube = findViewById(R.id.btnYoutube);
//        btnYoutube.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, ControlActivity.class);
//            startActivity(intent);
//        });
        btnYoutube.setOnClickListener(view -> {
            startRemoteController();
        });
    }

    public void startRemoteController() {
        AtomicBoolean isTimeout = new AtomicBoolean(false);
        Thread thread = new Thread(() -> {
            synchronized (isTimeout) {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(host, port), 3000);
                    isTimeout.set(true);
                    isTimeout.notify();
                } catch (SocketTimeoutException e) {
                    isTimeout.set(false);
                    isTimeout.notify();
                } catch (IOException e) {
                    System.out.println("### SOCKET PRE-CONNECT PROBLEM ###");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            synchronized (isTimeout) {
                isTimeout.wait();
            }
        } catch (InterruptedException e) {
            System.out.println("### WAITING IS INTERRUPTED ###");
            e.printStackTrace();
        }
        if (isTimeout.get()) {
            Intent intent = new Intent(MainActivity.this, ControlActivity.class);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, R.string.NetControllerUnableToConnect, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}