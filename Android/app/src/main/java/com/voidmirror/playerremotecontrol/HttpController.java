package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

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
        client = new OkHttpClient();
        this.context = context;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean checkConnection() {
        // TODO: check is server available
        return false;
    }

    public void sendSignal(String code) {
        if (host == null) {
            throw new NullPointerException("Host is not stated");
        }
        String bodyJson = "{\"code\":\""
            + code
            + "\"}";
        System.out.println(bodyJson);
//        RequestBody requestBody = RequestBody.create(TEXT_TYPE, bodyString);
        RequestBody requestBody = RequestBody.create(JSON, bodyJson);
        Request request = new Request.Builder()
                .url(host)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast toast = Toast.makeText(context, "Connection failure", Toast.LENGTH_SHORT);
//                toast.show();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected response code");
                } else {
//                    ((Activity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast toast = Toast.makeText(context, "Connection Success", Toast.LENGTH_SHORT);
//                            toast.show();
//                        }
//                    });

                    if (response.body() != null) {
                        System.out.println(response.body().string());
                    } else {
                        System.out.println("###  RESPONSE BODY IS NULL");
                    }
                    System.out.println("Server is online");
                }
            }
        });
    }
}
