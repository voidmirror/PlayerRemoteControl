package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NetController{
    private static String host = "192.168.0.79";
    private static int port = 4077;
    private static Socket clientSocket;
    private static BufferedWriter out;
    private Activity activityContext;

    public NetController(Activity activityContext) {
        this.activityContext = activityContext;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket(host, port);
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//                    Toast toast = Toast.makeText(activityContext, R.string.NetControllerConnected, Toast.LENGTH_SHORT);
//                    toast.show();
                } catch (IOException e) {
                    clientSocket = null;
                    out = null;
//                    Toast toast = Toast.makeText(activityContext, R.string.NetControllerUnableToConnect, Toast.LENGTH_SHORT);
//                    toast.show();
                    e.printStackTrace();
                    closeConnection();
                    activityContext.finish();
                }
//                finally {
//                    // TODO: send toast about socket closing
//                    closeConnection();
//                    System.out.println("CLOSE");
//
//                }
            }
        });
        thread.start();

    }

    public void sendSignal(String signal) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out.write(signal + '\n');
                    out.flush();
                } catch (IOException e) {
                    // TODO: send toast about writer fail
                    Toast toast = Toast.makeText(activityContext, R.string.NetControllerUnableToConnect, Toast.LENGTH_SHORT);
                    toast.show();
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    public void tmp() {

    }

    public void closeConnection() {
        if (clientSocket != null) {
            try {
                clientSocket.close();
                out.close();
            } catch (IOException e) {
                // TODO: closing fail
                e.printStackTrace();
            }
        }
    }
}
