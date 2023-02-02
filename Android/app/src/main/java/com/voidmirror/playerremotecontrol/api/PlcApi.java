package com.voidmirror.playerremotecontrol.api;

import com.voidmirror.playerremotecontrol.entity.Signal;
import com.voidmirror.playerremotecontrol.entity.SignalResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PlcApi {

    @POST("signal")
    Call<SignalResponse> sendSignal(@Body Signal signal);

}
