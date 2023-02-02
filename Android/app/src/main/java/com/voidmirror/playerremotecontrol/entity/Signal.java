package com.voidmirror.playerremotecontrol.entity;

import com.voidmirror.playerremotecontrol.additional.SignalType;
import com.voidmirror.playerremotecontrol.additional.SystemModule;

public class Signal {

    private SystemModule systemModule;

    private SignalType signalType;

    private String signal;

    private Signal() {}

    public Signal(SystemModule systemModule, SignalType signalType) {
        this.systemModule = systemModule;
        this.signalType = signalType;
    }

    public Signal(SystemModule systemModule, SignalType signalType, String signal) {
        this.systemModule = systemModule;
        this.signalType = signalType;
        this.signal = signal;
    }

    public SystemModule getSystemModule() {
        return systemModule;
    }

    public Signal setSystemModule(SystemModule systemModule) {
        this.systemModule = systemModule;
        return this;
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public Signal setSignalType(SignalType signalType) {
        this.signalType = signalType;
        return this;
    }

    public String getSignal() {
        return signal;
    }

    public Signal setSignal(String signal) {
        this.signal = signal;
        return this;
    }

    public static Signal create() {
        return new Signal();
    }

    @Override
    public String toString() {
        return "Signal{" +
                "systemModule=" + systemModule +
                ", signalType=" + signalType +
                ", signal='" + signal + '\'' +
                '}';
    }
}
