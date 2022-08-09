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
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
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
    private String revealedHost = null;
    private Subject<String> searchedHost;
    private Context context;

    public HttpController(Context context) {
        client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(2000, TimeUnit.MILLISECONDS)
                .build();
        this.context = context;
        searchedHost = PublishSubject.create();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public String getRevealedHost() {
        return revealedHost;
    }

    public void setRevealedHost(String revealedHost) {
        this.revealedHost = revealedHost;
    }

    public Subject<String> getSearchedHost() {
        return searchedHost;
    }


    // TODO: maybe duplicate this method to send to revealed and unrevealed
    public void sendSignal(Request request) {
        if (host == null) {
            throw new NullPointerException("Host is not stated");
        }

        dataSource(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    System.out.println("### V:" + response.toString());
                    handleResponse(response);
                }, v -> Log.e("RX Error", "Something goes wrong..."));

    }

    public Request makeRequest(RequestType type, String body) {
        String key = "";
        String url = "";
        switch (type) {
            case CODE:
                key = "code";
                url = "code";
                break;
            case LINK:
                key = "link";
                url = "link";
                break;
            case TIMER:
                key = "timerSetup";
                url = "timer";
                break;
        }

        String bodyJson = "{\"" + key+ "\":\""
                + body
                + "\"}";
        RequestBody requestBody = RequestBody.create(JSON, bodyJson);

        return new Request.Builder()
                .url(host + "/" + url)               //TODO: url - part after / --> host + "/" + url
                .post(requestBody)
                .build();
    }

    public Observable<Response> dataSource(Request request) {
        return Observable.create(subscriber -> {

            Response r = client.newCall(request).execute();
            if (r.code() == 200) {
                subscriber.onNext(r);
            } else {
                subscriber.onError(new Exception("Server response code != 200"));
            }

        });
    }

    public void handleResponse(Response response) {
        // TODO: if response.body() has "checkedOnline", searched
        try {
            if (response.body().string().contains("checkedOnline")) {
                // TODO: here onNext() <--- host (http://ip:port) from response.url()
//                searchedHost.onNext();
//                response.body()
            }
        } catch (IOException e) {
            Log.e("HandleResponse", "Body is null or body.string() is empty");
            e.printStackTrace();
        }
    }

}
