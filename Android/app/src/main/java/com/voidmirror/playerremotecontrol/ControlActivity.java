package com.voidmirror.playerremotecontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.PopupMenu;

import com.voidmirror.playerremotecontrol.additional.SignalType;
import com.voidmirror.playerremotecontrol.additional.SystemModule;
import com.voidmirror.playerremotecontrol.controller.HttpController;
import com.voidmirror.playerremotecontrol.entity.Signal;

import java.util.regex.Pattern;

public class ControlActivity extends Activity {

    Button btnShiftLeft;
    Button btnShiftRight;
    Button btnPause;
    Button btnTimer;
    Button btnFullscreen;
    Button btnSoundUp;
    Button btnSoundDown;
    Button btnMenu;
    Button btnSystemVolumeUp;
    Button btnSystemVolumeDown;
    Button btnSystemVolumeMute;

    HttpController httpController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        httpController = HttpController.getInstance(this);

        btnShiftLeft = findViewById(R.id.btnShiftLeft);
        btnShiftRight = findViewById(R.id.btnShiftRight);
        btnPause = findViewById(R.id.btnPause);
        btnTimer = findViewById(R.id.btnTimer);
        btnFullscreen = findViewById(R.id.btnFullscreen);
        btnSoundUp = findViewById(R.id.soundUp);
        btnSoundDown = findViewById(R.id.soundDown);
        btnMenu = findViewById(R.id.btnMenu);
        btnSystemVolumeUp = findViewById(R.id.systemVolumeUp);
        btnSystemVolumeDown = findViewById(R.id.systemVolumeDown);
        btnSystemVolumeMute = findViewById(R.id.systemVolumeMute);

        // NullPointerException is impossible (see MainActivity --> btn.setOnclickListener()
        switch (getIntent().getStringExtra("playerType")) {
            case "youtube":
                btnShiftLeft.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("shiftLeft")));
                btnShiftRight.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("shiftRight")));
                btnPause.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("playPause")));
                break;
            case "standard":
                btnShiftLeft.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("left")));
                btnShiftRight.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("right")));
                btnPause.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("space")));
                break;
        }



        btnFullscreen.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("fullscreen")));
        btnSoundUp.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("soundUp")));
        btnSystemVolumeUp.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.SYSTEM).setSignalType(SignalType.NIR).setSignal("systemVolumeUp")));
        btnSoundDown.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("soundDown")));
        btnSystemVolumeDown.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.SYSTEM).setSignalType(SignalType.NIR).setSignal("systemVolumeDown")));
        btnSystemVolumeMute.setOnClickListener(view -> httpController.sendSignal(Signal.create().setSystemModule(SystemModule.SYSTEM).setSignalType(SignalType.NIR).setSignal("systemVolumeToggleMute")));

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
                        httpController.sendSignal(Signal.create()
                                .setSystemModule(SystemModule.SYSTEM)
                                .setSignalType(SignalType.TIMER)
                                .setSignal(String.valueOf(timerMinutes[0] * 60)));
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();

        });

    }

    private void openPopupMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.inflate(R.menu.popupmenu);
        menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuNewVideo:
                    EditText editText = new EditText(this);
                    ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData data = manager.getPrimaryClip();
                    if (data != null) {
                        Pattern pattern = Pattern.compile("https://youtu\\.be/.*|https://www\\.youtube\\.com/watch.*");
                        if (pattern.matcher(data.getItemAt(0).getText().toString()).matches()) {
                            editText.setText(data.getItemAt(0).getText().toString());
                        }
                    }
                    AlertDialog.Builder builderVideo = new AlertDialog.Builder(ControlActivity.this);
                    builderVideo.setTitle("Open link:")
                            .setView(editText)
                            .setPositiveButton("Open", (dialog, which) -> {
                                httpController.sendSignal(Signal.create().setSystemModule(SystemModule.BROWSER).setSignalType(SignalType.LINK).setSignal(editText.getText().toString()));
                                dialog.dismiss();
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                            .create().show();
                    return true;
                case R.id.menuImmediateShutdown:
                    AlertDialog.Builder builderShutdown = new AlertDialog.Builder(ControlActivity.this);
                    builderShutdown.setTitle("Shutdown?")
                            .setMessage("Are you sure?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                httpController.sendSignal(Signal.create().setSystemModule(SystemModule.SYSTEM).setSignalType(SignalType.TIMER).setSignal("0"));
                                dialog.dismiss();
                            })
                            .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                            .create().show();
                    return true;
                case R.id.menuCancelShutdown:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.SYSTEM).setSignalType(SignalType.TIMER).setSignal("-1"));
                    return true;
                case R.id.part0:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("part0"));
                    return true;
                case R.id.part1:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("part1"));
                    return true;
                case R.id.part2:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("part2"));
                    return true;
                case R.id.part3:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("part3"));
                    return true;
                case R.id.part4:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("part4"));
                    return true;
                case R.id.part5:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("part5"));
                    return true;
                case R.id.part6:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("part6"));
                    return true;
                case R.id.part7:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("part7"));
                    return true;
                case R.id.part8:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("part8"));
                    return true;
                case R.id.part9:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.YOUTUBE).setSignalType(SignalType.PLAYER).setSignal("part9"));
                    return true;
                case R.id.menuCloseTab:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.BROWSER).setSignalType(SignalType.TAB).setSignal("closeTab"));
                    return true;
                case R.id.menuLeftTab:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.BROWSER).setSignalType(SignalType.TAB).setSignal("leftTab"));
                    return true;
                case R.id.menuRightTab:
                    httpController.sendSignal(Signal.create().setSystemModule(SystemModule.BROWSER).setSignalType(SignalType.TAB).setSignal("rightTab"));
                    return true;

                default:
                    return false;
            }
        });
        menu.show();
    }

}
