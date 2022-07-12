package com.voidmirror.playerremotecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.reactivestreams.Subscription;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    Button btnYoutube;

    NetSearch netSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnYoutube = findViewById(R.id.btnYoutube);
        btnYoutube.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ControlActivity.class);
            startActivity(intent);
        });

        netSearch = new NetSearch(getApplicationContext());
        netSearch.search();

//        ArrayList<Boolean> test = test();


//        test().subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation()).subscribe(new Consumer<ArrayList<Boolean>>() {
//            @Override
//            public void accept(ArrayList<Boolean> booleans) throws Throwable {
//                for (int i = 0; i < booleans.size(); i++) {
//                    System.out.println("### " + i + " " + booleans.get(i));
//                }
//            }
//        });


//        for (int i = 0; i < test.size(); i++) {
//            System.out.println("### " + i + " " + test.get(i));
//        }
    }

    public Flowable<ArrayList<Boolean>> test() {
//        Flowable.just("Hello world").subscribe(System.out::println);
//        Flowable.fromCallable(() -> {
//            Thread.sleep(1000);
//            return "Done";
//        }).subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.single())
//                .subscribe(System.out::println, Throwable::printStackTrace);
//
        ArrayList<Boolean> ipList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            ipList.add(false);
        }
//
        return Flowable.fromCallable(() -> {
                    Random r = new Random();

                    for (int i = 0; i < 16; i++) {
                        Thread.sleep(1000);
                        ipList.set(i, r.nextBoolean());
//                        ipList.set(i, true);
                        System.out.println("### TRUE INSIDE FROMCALLABLE" + " " + i);
                    }
                    return ipList;
                });
//        }).subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.computation())
//                .subscribe();

//        Observable<String> values = Observable.fromCallable(() -> {
//            System.out.println("hey");
//            return "result";
//        })
//
//        values.subscribe(
//                v -> System.out.println("Received: " + v),
//                e -> System.out.println("Error: " + e),
//                () -> System.out.println("Completed")
//        );
    }
}