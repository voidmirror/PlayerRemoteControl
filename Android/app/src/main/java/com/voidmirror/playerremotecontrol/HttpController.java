package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpController {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType TEXT_TYPE
            = MediaType.parse("text/html; charset=utf-8");
    private final OkHttpClient client;
    private String host = null;
    private Context context;

    public HttpController(Context context) {
        client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(2000, TimeUnit.MILLISECONDS)
                .build();
        this.context = context;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void sendSignal(String code) {
        if (host == null) {
            throw new NullPointerException("Host is not stated");
        }
        String bodyJson = "{\"code\":\""
                + code
                + "\"}";
        RequestBody requestBody = RequestBody.create(JSON, bodyJson);
        Request request = new Request.Builder()
                .url(host)
                .post(requestBody)
                .build();

        dataSource(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
//                    System.out.println("### V:" + response);
                }, v -> Log.e("RX Error", "Something goes wrong..."));

    }

    public Observable<String> dataSource(Request request) {
        return Observable.create(subscriber -> {

            Response r = client.newCall(request).execute();
            if (r.code() == 200) {
                subscriber.onNext(r.toString());
            } else {
                subscriber.onError(new Exception("Server response code != 200"));
            }
        });
    }

}
