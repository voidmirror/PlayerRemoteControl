package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ControlActivity extends Activity {

    Button btnShiftLeft;
    Button btnShiftRight;
    Button btnPause;
    Button btnTimer;
    Button btnFullscreen;
    Button btnSoundUp;
    Button btnSoundDown;
    Button btnMenu;

    HttpController httpController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        httpController = HttpController.getInstance();
//        LastResponseObserver lastResponseObserver = new LastResponseObserver(this, httpController.getLastResponse());

        btnShiftLeft = findViewById(R.id.btnShiftLeft);
        btnShiftRight = findViewById(R.id.btnShiftRight);
        btnPause = findViewById(R.id.btnPause);
        btnTimer = findViewById(R.id.btnTimer);
        btnFullscreen = findViewById(R.id.btnFullscreen);
        btnSoundUp = findViewById(R.id.soundUp);
        btnSoundDown = findViewById(R.id.soundDown);
        btnMenu = findViewById(R.id.btnMenu);

        btnShiftLeft.setOnClickListener(view -> {
            httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "shiftLeft"));
        });
        btnShiftRight.setOnClickListener(view -> {
            httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "shiftRight"));
        });
        btnPause.setOnClickListener(view -> {
            httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "playPause"));
        });
        btnFullscreen.setOnClickListener(view -> {
            httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "fullscreen"));
        });
        btnSoundUp.setOnClickListener(view -> {
            httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "soundUp"));
        });
        btnSoundDown.setOnClickListener(view -> {
            httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "soundDown"));
        });
        btnMenu.setOnClickListener(this::openPopupMenu);
        btnTimer.setOnClickListener(view -> {

            NumberPicker numberPicker = new NumberPicker(ControlActivity.this);
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(240);
            final int[] timerMinutes = {40};
            numberPicker.setValue(40);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
                System.out.println("### NumberPicker Value Changed: " + oldVal + " ---> " + newVal);
                timerMinutes[0] = newVal;
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(ControlActivity.this);
            builder.setTitle("Timer choice")
                    .setView(numberPicker)
                    .setPositiveButton("Ok", (dialog, which) -> {
                        httpController.sendSignal(httpController.makeRequest(RequestType.TIMER, String.valueOf(timerMinutes[0] * 60)));
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();

        });

//        httpController.getLastResponse()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(v -> {
////                    System.out.println("### Subscribing on LastResponse");
//                    Toast toast = Toast.makeText(ControlActivity.this, v, Toast.LENGTH_SHORT);
//                    toast.show();
//                    httpController.recreateLastResponse();
//                });

    }

    private void openPopupMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.inflate(R.menu.popupmenu);
        menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuNewVideo:
                    EditText editText = new EditText(this);
                    AlertDialog.Builder builderVideo = new AlertDialog.Builder(ControlActivity.this);
                    builderVideo.setTitle("Open link:")
                            .setView(editText)
                            .setPositiveButton("Open", (dialog, which) -> {
                                httpController.sendSignal(httpController.makeRequest(RequestType.LINK, editText.getText().toString()));
                                dialog.dismiss();
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                            .create().show();
                    return true;
                case R.id.menuImmediateShutdown:
                    AlertDialog.Builder builderShutdown = new AlertDialog.Builder(ControlActivity.this);
                    builderShutdown.setTitle("Shutdown?")
                            .setMessage("Are ou sure?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                httpController.sendSignal(httpController.makeRequest(RequestType.TIMER, String.valueOf(0)));
                                dialog.dismiss();
                            })
                            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                            .create().show();
                    return true;
                case R.id.menuCancelShutdown:
                    httpController.sendSignal(httpController.makeRequest(RequestType.TIMER, String.valueOf(-1)));
                    return true;
                case R.id.part0:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "part0"));
                    return true;
                case R.id.part1:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "part1"));
                    return true;
                case R.id.part2:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "part2"));
                    return true;
                case R.id.part3:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "part3"));
                    return true;
                case R.id.part4:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "part4"));
                    return true;
                case R.id.part5:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "part5"));
                    return true;
                case R.id.part6:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "part6"));
                    return true;
                case R.id.part7:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "part7"));
                    return true;
                case R.id.part8:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "part8"));
                    return true;
                case R.id.part9:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "part9"));
                    return true;

                case R.id.menuCloseTab:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "closeTab"));
                    return true;
                case R.id.menuLeftTab:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "leftTab"));
                    return true;
                case R.id.menuRightTab:
                    httpController.sendSignal(httpController.makeRequest(RequestType.CODE, "rightTab"));
                    return true;

                default:
                    return false;
            }
        });
//        menu.setOnDismissListener(menu1 -> Toast.makeText(ControlActivity.this, "onDismiss",
//                Toast.LENGTH_SHORT).show());
        menu.show();
    }

}
