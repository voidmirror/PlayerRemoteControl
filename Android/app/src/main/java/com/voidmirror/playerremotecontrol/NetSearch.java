package com.voidmirror.playerremotecontrol;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.net.InetAddress;
import java.util.ArrayList;

import javax.xml.transform.Source;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NetSearch {

    private int subnetSearchStart = 0;
    private int subnetSearchStop = 256;

    private Context context;

    public NetSearch(Context context) {
        this.context = context;
    }

    private String getSubnetAddress(int address)
    {
        String ipString = String.format(
                "%d.%d.%d",
                (address & 0xff),
                (address >> 8 & 0xff),
                (address >> 16 & 0xff));

        return ipString;
    }

    public void search() {

        System.out.println("### START SEARCH ###");
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String subnet = getSubnetAddress(wifiManager.getDhcpInfo().gateway);
        System.out.println("gateway " + wifiManager.getDhcpInfo().gateway);
        System.out.println("ipadress " + wifiManager.getDhcpInfo().ipAddress);
        System.out.println("serveradress " + wifiManager.getDhcpInfo().serverAddress);
        System.out.println("Subnet " + subnet);
        System.out.println("### FINISH SEARCH ###");

        String ip = Formatter.formatIpAddress(wifiManager.getDhcpInfo().ipAddress);
        System.out.println("IP: " + ip);

        ArrayList<Boolean> reachable = null;
//        for (int i = 0; i < 256; i++) {
//            reachable.add(false);
//        }

//        for (int i = 0; i < 256; i++) {
//            ExecutorService executor = Executors.newFixedThreadPool(256);

            long time = System.currentTimeMillis();
            Observable<ArrayList<Boolean>> listObservable = Observable.fromCallable(() -> {

                ArrayList<Boolean> list = new ArrayList<>();

                for (int i = 0; i < 256; i++) {
                    boolean result = false;
                    try {
                        InetAddress address = InetAddress.getByName(subnet + "." + String.valueOf(i));
                        result = address.isReachable(1000);
                        list.set(i, result);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                return list;
            });
            listObservable.subscribe(new DisposableObserver<ArrayList<Boolean>>() {
                @Override
                public void onNext(@NonNull ArrayList<Boolean> booleans) {
                    
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            });

            System.out.println("### TIME: " + (System.currentTimeMillis() - time));

//        }
        getOpenLocalAddresses(subnet).subscribeOn(Schedulers.computation()).observeOn(Schedulers.single()).subscribe(booleans -> {
            for (int i = 0; i < booleans.size(); i++) {
                System.out.println("### Subnet issue: " + booleans.get(i) + " " + subnet + "." + i);
            }
        });

    }

    public Flowable<ArrayList<Boolean>> getOpenLocalAddresses(String subnet) {

        // TODO: Manual boundary 'from i to n' using vars
        ArrayList<Boolean> ipList = new ArrayList<>();
        for (int i = 0; i < subnetSearchStop; i++) {
            ipList.add(false);
        }

        return Flowable.fromCallable(() -> {
            for (int i = subnetSearchStart; i < subnetSearchStop; i++) {
                InetAddress inetAddress = InetAddress.getByName(subnet + "." + i);
                ipList.set(i, inetAddress.isReachable(10));
            }
            return ipList;
        });




//        return Observable.create()
    }

    public Flowable<ArrayList<Boolean>> getHTTPLocalAddresses(String subnet) {
        // do the save with OKHTTP and see return code (find 200)
        return null;
    }



}
