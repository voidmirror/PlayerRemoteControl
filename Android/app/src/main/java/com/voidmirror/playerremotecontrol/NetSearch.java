package com.voidmirror.playerremotecontrol;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

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


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetSearch {

    private int subnetSearchStart = 0;
    private int subnetSearchStop = 256;

    private Context context;
    private HttpController httpController;

    public NetSearch(Context context) {
        this.context = context;
        httpController = new HttpController(context);
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

    public String getSubnet() {

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

        return subnet;

    }

    public void reallySearch() {
        String subnet = getSubnet();

        searchOpenedServer2(subnet)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(d -> {
//                    httpController.setHost(d);
//                    httpController.sendSignal("checkOnline");
//                    httpController.sendSignal(
//                            httpController.makeRequest(RequestType.CODE, "checkOnline")
//                    );
                    httpController.sendSignal(
                            new Request.Builder()
                                .url(d + "/code")
                                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"code\":\"checkOnline\"}"))
                                .build()
                    );
                }, d -> {
                    d.printStackTrace();
                    Log.e("SubnetSearchError", "CheckOnline is not possible");
                });

//        Single.just(httpController.getRevealedHost())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread())
//                .subscribe()

        httpController.getSearchedHost()
                .subscribe(host -> {
                    System.out.println("### FROM GETSEARCHEDHOST: now host is: " + host);
                    httpController.setRevealedHost(host);
                });

    }

    public Observable<String> searchOpenedServer2(String subnet) {
        return  Observable.create(subscriber -> {
            for (int i = 1; i < 255; i++) {
                subscriber.onNext("http://" + subnet + "." + i + ":4077");
            }
        });
    }


}
