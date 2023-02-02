package com.voidmirror.playerremotecontrol.controller;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.widget.Toast;

import com.voidmirror.playerremotecontrol.additional.SignalType;
import com.voidmirror.playerremotecontrol.additional.SystemModule;
import com.voidmirror.playerremotecontrol.api.ConnectionApi;
import com.voidmirror.playerremotecontrol.api.PlcApi;
import com.voidmirror.playerremotecontrol.entity.Signal;
import com.voidmirror.playerremotecontrol.entity.SignalResponse;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpController {

    private static HttpController instance;

    public static Context applicationContext;

    private PlcApi plcApi;

    private static String host;

    private static int port = 4077;

    private static String localNet = null;

    public HttpController getInstance() {
        if (instance == null) {
            instance = new HttpController();
        }
        return instance;
    }

    public static HttpController getInstance(Context context) {
        applicationContext = context;
        if (instance == null) {
            instance = new HttpController();
        }
        String subnet = getSubnet();
        System.out.println("### Subnet: " + subnet);
        if (!Pattern.matches("(\\d{1,3}\\.){2}\\d{1,3}", subnet) || subnet.split("\\.")[0].equals("0")) {
            System.out.println("### PATTERN MATCHES: " + !Pattern.matches("(\\d{1,3}\\.){2}\\d{1,3}", subnet));
            System.out.println("### ZERO EQUALITY: " + subnet.split("\\.")[0].equals("0"));
            return null;
        }

        localNet = subnet;
        return instance;
    }

    private HttpController() {
        if (host != null) {
            makeApi(host);
        }
    }

    private void makeApi(String host) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .build();
        plcApi = retrofit.create(PlcApi.class);
    }

    public static String getSubnet() {

        WifiManager wifiManager = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

//        ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!wifiManager.isWifiEnabled()) {
            Observable.create(sub -> {
                Toast toast = Toast.makeText(applicationContext, "Wifi disabled!!!", Toast.LENGTH_SHORT);
                toast.show();
            })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            return "Wifi issue";
        }
        if (wifiInfo.getBSSID() == null || wifiInfo.getBSSID().equals("")) {
            Observable.create(sub -> {
                // TODO: Toast "Wifi is NOT connected!"
//                Toast toast = Toast.makeText(applicationContext, "Wifi is NOT connected", Toast.LENGTH_LONG);
                Toast toast = Toast.makeText(applicationContext, "Wifi state: " + wifiManager.isWifiEnabled() + ", BSSID: " + wifiInfo.getBSSID() + ", WifiState: " + wifiManager.getWifiState(), Toast.LENGTH_LONG);
                toast.show();
            })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
        String subnet = getSubnetAddress(wifiManager.getDhcpInfo().gateway);

        String ip = Formatter.formatIpAddress(wifiManager.getDhcpInfo().ipAddress);

        return subnet;

    }

    private static String getSubnetAddress(int address)
    {
        String ipString = String.format(
                "%d.%d.%d",
                (address & 0xff),
                (address >> 8 & 0xff),
                (address >> 16 & 0xff));

        return ipString;
    }

    public void findConnection(Consumer<SignalResponse> consumer) {
        Observable<SignalResponse> connect = Observable.create(subscriber -> {
            for (int i = 2; i < 255; i++) {
                ConnectionApi api;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://" + localNet + "." + i + ":" + port + "/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                api = retrofit.create(ConnectionApi.class);
                api.sendConnect(new Signal(SystemModule.SYSTEM, SignalType.CONNECTION)).enqueue(new Callback<SignalResponse>() {
                    @Override
                    public void onResponse(Call<SignalResponse> call, Response<SignalResponse> response) {
                        if (response.body() != null && response.body().getResponse().equals("connect")) {
                            host = "http://" + retrofit.baseUrl().host().split("connect")[0] + ":" + port + "/";
                            System.out.println("### BASE URL: " + host);
                            subscriber.onNext(response.body());
                            initRetrofitApi();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignalResponse> call, Throwable t) {
                        // TODO: comment after checking (minimize io load)
                        System.out.println("### " + call.request().url() + " - no response");
                    }
                });
            }
        });
        // TODO: check; accept() may produce some errors (unusual use)
        connect
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(consumer::accept, Throwable::printStackTrace);

    }

    public void initRetrofitApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.plcApi = retrofit.create(PlcApi.class);
    }

    public void sendSignal(Signal signal) {
        plcApi.sendSignal(signal).enqueue(new Callback<SignalResponse>() {
            @Override
            public void onResponse(Call<SignalResponse> call, Response<SignalResponse> response) {
                if (response.body() != null) {
                    switch (response.body().getResponseType()) {
                        case TIMER:
                            Observable.create(sub -> {
                                Toast toast = Toast.makeText(applicationContext, response.body().getResponse(), Toast.LENGTH_SHORT);
                                toast.show();
                            }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<SignalResponse> call, Throwable t) {
                // do nothing?
            }
        });
    }

}
