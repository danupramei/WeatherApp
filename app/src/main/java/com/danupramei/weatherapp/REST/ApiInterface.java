package com.danupramei.weatherapp.REST;

import com.danupramei.weatherapp.Models.Weather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    // MOVIES
    @GET("onecall")
    Observable<Weather> getForcase(@Query("lat") String lat, @Query("lon") String lng, @Query("exclude") String exclude, @Query("units") String units, @Query("APPID") String key);
}
