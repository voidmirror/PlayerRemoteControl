package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
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
    private ReplaySubject<String> searchedHost;
    private ReplaySubject<String> lastResponse;

    private static class SingletonHolder {
        public static final HttpController HOLDER_INSTANCE = new HttpController();
    }

    private HttpController() {
        client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(2000, TimeUnit.MILLISECONDS)
                .build();
        searchedHost = ReplaySubject.create();
        lastResponse = ReplaySubject.create();
    }

    public static HttpController getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public Subject<String> getSearchedHost() {
        return searchedHost;
    }

    public ReplaySubject<String> getLastResponse() {
        return lastResponse;
    }

    public void recreateLastResponse() {
        lastResponse.onComplete();
        lastResponse = ReplaySubject.create();
    }

    public void sendSignal(Request request) {

        dataSource(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    System.out.println("### V:" + response.toString());
                    handleResponse(response);
                }, v -> {
                    Log.e("RX Error", "Something goes wrong...");
//                    v.printStackTrace();
                });

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
                key = "timerNum";
                url = "timer";
                break;
        }

        String bodyJson = "{\"" + key+ "\":\""
                + body
                + "\"}";
        RequestBody requestBody = RequestBody.create(JSON, bodyJson);

        return new Request.Builder()
                .url(host + "/" + url)
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
        try {

            String body = response.peekBody(2048).string();
            System.out.println("### BODY IS: " + body);

            if (response.body() != null && response.body().string().contains("checkedOnline")) {
                searchedHost.onNext(response.request().url().toString());
            }
            if (response.body() != null) {
                lastResponse.onNext(body);
            }
        } catch (IOException e) {
            Log.e("HandleResponse", "Body is null or body.string() is empty");
            e.printStackTrace();
        }
    }

}
