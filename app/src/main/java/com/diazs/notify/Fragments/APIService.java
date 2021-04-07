package com.diazs.notify.Fragments;

import com.diazs.notify.Notifications.MyResponse;
import com.diazs.notify.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAUgifUuM:APA91bE510gRwQ06RpLJb1E1_KbKCrLDcrDxvQmOneqk_0OzEim_ro2Jx6vlcgun0JrFhLd8xaDkSOLxWRRFA0lKnkSSrNijRYoWGGqjmtpCMVX09Ds_KM7c26esmdOY_WMRppYo7fbr"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
