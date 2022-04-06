package com.example.myapplication;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface ApiInterface {

    // API's endpoints
    @GET("/hotelsList")
    public void getHotelsLists(Callback<List<HotelListData>> callback);

}
