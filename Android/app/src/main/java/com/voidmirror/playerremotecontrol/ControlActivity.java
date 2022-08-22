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

//    Spinner dropdown;

    HttpController httpController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        httpController = HttpController.getInstance();
//        LastResponseObserver lastResponseObserver = new LastResponseObserver(this, httpController.getLastResponse());

//        dropdown = findViewById(R.id.dropdown);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.controllerDropdown, android.R.layout.simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        dropdown.setAdapter(adapter);
//        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        //TODO: openning new video in browser
//                        System.out.println("There will be new video soon");
//                        break;
//                    case 1:
//                        //TODO: send signal to shutdown a computer
//                        System.out.println(("There a computer will be shutingdown"));
//
//                        break;
//                    case 2:
//                        httpController.sendSignal(httpController.makeRequest(RequestType.TIMER, String.valueOf(-1)));
////                        httpController.getLastResponse()
////                                .observeOn(AndroidSchedulers.mainThread())
////                                .subscribeOn(Schedulers.io())
////                                .subscribe(v -> {
////                                    System.out.println("### Subscribing on LastResponse");
////                                    Toast toast = Toast.makeText(ControlActivity.this, v, Toast.LENGTH_SHORT);
////                                    toast.show();
////                                    httpController.recreateLastResponse();
////                                });
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

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
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    System.out.println("### NumberPicker Value Changed: " + oldVal + " ---> " + newVal);
                    timerMinutes[0] = newVal;
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(ControlActivity.this);
            builder.setTitle("Timer choice")
                    .setView(numberPicker)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            httpController.sendSignal(httpController.makeRequest(RequestType.TIMER, String.valueOf(timerMinutes[0] * 60)));
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
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
                    return true;
                case R.id.menuImmediateShutdown:
                    return true;
                case R.id.menuCancelShutdown:
                    httpController.sendSignal(httpController.makeRequest(RequestType.TIMER, String.valueOf(-1)));
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
