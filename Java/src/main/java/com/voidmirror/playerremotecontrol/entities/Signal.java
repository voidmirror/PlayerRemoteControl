package com.voidmirror.playerremotecontrol.entities;

public class Signal {

    private SystemModule systemModule;

    private SignalType signalType;

    private String signal;

    public Signal() {}

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

    public void setSystemModule(SystemModule systemModule) {
        this.systemModule = systemModule;
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public void setSignalType(SignalType signalType) {
        this.signalType = signalType;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
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
