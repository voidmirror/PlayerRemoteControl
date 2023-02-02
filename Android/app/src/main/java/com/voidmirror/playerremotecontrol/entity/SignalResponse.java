package com.voidmirror.playerremotecontrol.entity;

import com.voidmirror.playerremotecontrol.additional.ResponseType;

public class SignalResponse {

    private ResponseType responseType;

    private String response;

    private SignalResponse() {}

    public String getResponse() {
        return response;
    }

    public SignalResponse setResponse(String response) {
        this.response = response;
        return this;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public SignalResponse setResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    public static SignalResponse create() {
        return new SignalResponse();
    }
}
