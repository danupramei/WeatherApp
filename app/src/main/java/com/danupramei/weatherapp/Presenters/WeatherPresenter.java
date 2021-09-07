package com.danupramei.weatherapp.Presenters;

import android.content.Context;
import android.util.Log;

import com.danupramei.weatherapp.Adapters.WeatherAdapter;
import com.danupramei.weatherapp.Models.Current;
import com.danupramei.weatherapp.Models.Weather;
import com.danupramei.weatherapp.REST.ApiClient;
import com.danupramei.weatherapp.REST.ApiInterface;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static com.danupramei.weatherapp.Utils.StatusView.STATUS_GAGAL;
import static com.danupramei.weatherapp.Utils.StatusView.STATUS_NOCONNECTION;
import static com.danupramei.weatherapp.Utils.StatusView.STATUS_SUKSES;
import static com.danupramei.weatherapp.Utils.VariableGlobal.API_KEY;

public class WeatherPresenter {
    private Context context;
    private ViewWeatherForcase view;
    private WeatherAdapter weatherAdapter;
    private ApiInterface mApi = ApiClient.getClient().create(ApiInterface.class);

    public WeatherPresenter(ViewWeatherForcase view, Context context){
        this.view = view;
        this.context = context;
    }

    public void loadWeather(String lat, String lng, String exclude) {

        mApi.getForcase(lat, lng, exclude, "metric", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Weather weather) {
                        if (weather != null) {
                            weatherAdapter = new WeatherAdapter(context, weather.getDaily());
                            view.setAdapterWeather(weatherAdapter, STATUS_SUKSES);
                            view.setCurrentWeater(weather.getCurrent(), STATUS_SUKSES);
                        } else {
                            view.setAdapterWeather(weatherAdapter, STATUS_GAGAL);
                            view.setCurrentWeater(weather.getCurrent(), STATUS_GAGAL);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof IOException) {
                            view.setAdapterWeather(weatherAdapter, STATUS_NOCONNECTION);
                            view.setCurrentWeater(new Current(), STATUS_NOCONNECTION);
                        } else {
                            Log.d(TAG, "onError: "+e);
                            view.setAdapterWeather(weatherAdapter, STATUS_GAGAL);
                            view.setCurrentWeater(new Current(), STATUS_GAGAL);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface ViewWeatherForcase {
        void setCurrentWeater(Current current, int status);
        void setAdapterWeather(WeatherAdapter mAdapter, int status);
    }
}
