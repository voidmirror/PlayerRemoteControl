package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetController{
    private static String host = "192.168.0.79";
    private static int port = 4077;
    private static Socket clientSocket;
    private static BufferedWriter out;
    private Activity activityContext;

    public NetController(Activity activityContext) {
        this.activityContext = activityContext;
        Thread thread = new Thread(() -> {
            System.out.println("### ESTABLISHING THREAD STARTED ###");

//            if (!pingServer()) {
//                Toast toast = Toast.makeText(activityContext, R.string.NetControllerConnected, Toast.LENGTH_SHORT);
//                toast.show();
//                activityContext.finish();
//            }


//            try {
//                InetAddress address = InetAddress.getByName(host);
//                System.out.println("### Sending PING ###");
//                Process p = java.lang.Runtime.getRuntime().exec("nc -vz " + host + " " + port);
//                int returnVal = p.waitFor();
//                boolean reachable = returnVal == 0;
//                System.out.println("### REACHABLE " + returnVal + " ###");
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }

//            try {
//                InetAddress address = InetAddress.getByName(host + ":" + port);
//                System.out.println("### " + address.isReachable(5000) + " ###");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            try {
                System.out.println("### BEFORE SOCKET ###");
                clientSocket = new Socket(host, port);

                System.out.println("### AFTER SOCKET ###");
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                System.out.println("### AFTER WRITER ###");
                System.out.println(clientSocket);
                System.out.println(out);
            } catch (UnknownHostException h) {
                System.out.println("### UNKNOWN HOST CATCH EXCEPTION ###");
                Toast toast = Toast.makeText(activityContext, R.string.NetControllerUnableToConnect, Toast.LENGTH_SHORT);
                toast.show();
                clientSocket = null;
                out = null;
                activityContext.finish();
            } catch (IOException e) {
                System.out.println("### IO CATCH EXCEPTION ###");
                clientSocket = null;
                out = null;
                e.printStackTrace();
                activityContext.finish();
            }
        });
        thread.start();
//        Thread threadTimer = new Thread(() -> {
//            try {
//                Thread.sleep(3000);
//                System.out.println(clientSocket);
//                if (clientSocket != null) {
//                    pm.setComponentEnabledSetting(new ComponentName(activityContext, ControlActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
//                } else {
////                    Toast toast = Toast.makeText(activityContext, R.string.NetControllerUnableToConnect, Toast.LENGTH_SHORT);
////                    toast.show();
//                    activityContext.finish();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        threadTimer.start();
    }

    public void sendSignal(String signal) {
        System.out.println("### SIGNAL " + signal + " ###");
        System.out.println(out);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("### SIGNAL 2 " + signal + " ###");
                    out.write(signal + '\n');
                    out.flush();
                } catch (IOException | NullPointerException e) {
                    // TODO: send toast about writer fail
                    e.printStackTrace();
                }
            }
        });

        if (out == null) {
            Toast toast = Toast.makeText(activityContext, R.string.NetControllerUnableToConnect, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            thread.start();
        }
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

    public boolean pingServer() {
        try {
            Socket pinging = new Socket();
            pinging.connect(new InetSocketAddress(host, port), 1000);
            pinging.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
