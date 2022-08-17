package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

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
import io.reactivex.rxjava3.core.Observer;
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

    private int subnetSearchStart = 2;
    private int subnetSearchStop = 255;

    private Context context;
    private HttpController httpController;

    public NetSearch(Context context) {
        this.context = context;
        this.httpController = HttpController.getInstance();

        httpController.getSearchedHost()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(host -> {

                    /**
                     * Program is used in local network, so no need to worry about
                     * addresses like https://codewars.com and their "/code"
                     */
                    httpController.setHost(host.replace("/check", ""));
                    ((Activity)context).findViewById(R.id.btnYoutube).setEnabled(true);
                    Toast toast = Toast.makeText(((Activity) context), "Connected to " + httpController.getHost().replace("http://", ""), Toast.LENGTH_LONG);
                    toast.show();
                }, e -> {
                    Log.e("PUBSUBJ", "Public subject ejects something strange or nothing");
                    e.printStackTrace();
                });

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

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String subnet = getSubnetAddress(wifiManager.getDhcpInfo().gateway);

        String ip = Formatter.formatIpAddress(wifiManager.getDhcpInfo().ipAddress);

        return subnet;

    }

    public void reallySearch() {
        String subnet = getSubnet();

        searchOpenedServer2(subnet)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(d -> {
                    httpController.sendSignal(
                            new Request.Builder()
                                    .url(d + "/check")
                                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"code\":\"checkOnline\"}"))
                                    .build()
                    );
                }, e -> {
                    e.printStackTrace();
                    Log.e("SubnetSearchError", "CheckOnline is not possible");
                });


    }

    public Observable<String> searchOpenedServer2(String subnet) {
        return  Observable.create(subscriber -> {
            for (int i = subnetSearchStart; i < subnetSearchStop; i++) {
                subscriber.onNext("http://" + subnet + "." + i + ":4077");
            }
        });
    }


}
