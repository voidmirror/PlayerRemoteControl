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

        //TODO: something strange, now commented
        /*

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

         */

    }

    public String searchOpenedServer(String subnet) {
        OkHttpClient client = new OkHttpClient();
        String bodyJson = "{\"code\":\""
                + "isServerOpened"
                + "\"}";
        System.out.println(bodyJson);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyJson);

        for (int i = 0; i < 256; i++) {
            Request request = new Request.Builder()
                    .url(subnet + "." + i)
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //TODO: if server sent "opened", return its ip
                }
            });
        }



        return null;

    }

    public void reallySearch() {
        String subnet = getSubnet();

        searchOpenedServer2(subnet)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(d -> {
                    System.out.println("D is " + d);
                    httpController.setHost(d);
                    httpController.sendSignal("checkOnline");
                }, d -> {
                    Log.e("SubnetSearchError", "CheckOnline is not possible");
                });
    }

    public Observable<String> searchOpenedServer2(String subnet) {
        return  Observable.create(subscriber -> {
            for (int i = 0; i < 256; i++) {
//                System.out.println(subnet + "." + i);
                subscriber.onNext(subnet + "." + i);
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
