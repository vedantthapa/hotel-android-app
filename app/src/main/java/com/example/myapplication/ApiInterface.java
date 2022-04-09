package com.example.myapplication;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface ApiInterface {

    // API's endpoints
    @GET("/hotels/")
    public void getHotelsLists(@Query("check_in") String check_in, @Query("check_out") String check_out, Callback<List<HotelListData>> callback);
    @POST("/booking/")
    public void postGuestsLists(@Body ReservationData reservationData, Callback<String> callback);

}
