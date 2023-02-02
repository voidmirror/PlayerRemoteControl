package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.widget.Toast;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.Subject;

public class LastResponseObserver {

    final private Activity activity;
    final private Subject<String> subject;

    public LastResponseObserver(Activity activity, Subject<String> subject) {
        this.activity = activity;
        this.subject = subject;
        observe();
    }

    private void observe() {
        subject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(v -> {
//                    System.out.println("### Subscribing on LastResponse");
                    Toast toast = Toast.makeText(activity, v, Toast.LENGTH_SHORT);
                    toast.show();
                    HttpControllerOld.getInstance().recreateLastResponse();
                });
    }
}
