package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class NetSearch {

    private int subnetSearchStart = 2;
    private int subnetSearchStop = 255;

    private Context context;
    private HttpControllerOld httpControllerOld;

    public NetSearch(Context context) {
        this.context = context;
        this.httpControllerOld = HttpControllerOld.getInstance();

        httpControllerOld.getSearchedHost()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(host -> {

                    /**
                     * Program is used in local network, so no need to worry about
                     * addresses like https://codewars.com and their "/code"
                     */
                    httpControllerOld.setHost(host.replace("/check", ""));
                    ((Activity)context).findViewById(R.id.btnYoutube).setEnabled(true);
                    Toast toast = Toast.makeText(((Activity) context), "Connected to " + httpControllerOld.getHost().replace("http://", ""), Toast.LENGTH_LONG);
                    toast.show();
                }, e -> {
                    Log.e("PUBSUBJ", "Public subject ejects something strange or nothing");
//                    e.printStackTrace();
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
                    httpControllerOld.sendSignal(
                            new Request.Builder()
                                    .url(d + "/check")
                                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"code\":\"checkOnline\"}"))
                                    .build()
                    );
                }, e -> {
//                    e.printStackTrace();
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
