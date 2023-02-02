package com.voidmirror.playerremotecontrol.api;

import com.voidmirror.playerremotecontrol.entity.Signal;
import com.voidmirror.playerremotecontrol.entity.SignalResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ConnectionApi {

    @POST("connect")
    Call<SignalResponse> sendConnect(@Body Signal signal);

}
