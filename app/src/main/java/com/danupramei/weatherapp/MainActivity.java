package com.danupramei.weatherapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.danupramei.weatherapp.Adapters.WeatherAdapter;
import com.danupramei.weatherapp.Models.Current;
import com.danupramei.weatherapp.Presenters.WeatherPresenter;
import com.danupramei.weatherapp.Utils.SpanningLinearLayoutManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.danupramei.weatherapp.Utils.DateConverter.toDate;
import static com.danupramei.weatherapp.Utils.IconWeather.getIcon;
import static com.danupramei.weatherapp.Utils.StatusView.STATUS_SUKSES;
import static com.danupramei.weatherapp.Utils.VariableGlobal.CITY;
import static com.danupramei.weatherapp.Utils.VariableGlobal.LAT;
import static com.danupramei.weatherapp.Utils.VariableGlobal.LNG;
import static com.danupramei.weatherapp.Utils.VariableGlobal.REGION;

public class MainActivity extends AppCompatActivity implements WeatherPresenter.ViewWeatherForcase, EasyPermissions.PermissionCallbacks  {
    int REQUEST_CITY = 1;
    WeatherAdapter mAdapterWeather;
    RecyclerView rcvWeathers;
    TextView tvHumidity, tvEstimated, tvDeskWeater, tvTemperature, tvDate, tvLocation;
    ImageView ivWeatherImg;
    SwipeRefreshLayout refresh;
    LinearLayout llPilihCity, llAdd;

    WeatherPresenter presenter;
    Context context = MainActivity.this;
    private FusedLocationProviderClient fusedLocationClient;
    SharedPreferences sp;

    String city;
    String region;
    String lat;
    String lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvWeathers = findViewById(R.id.rcv_weathers);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvEstimated = findViewById(R.id.tv_estimated);
        tvDeskWeater = findViewById(R.id.tv_desk_weater);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvDate = findViewById(R.id.tv_date);
        tvLocation = findViewById(R.id.tv_location);
        ivWeatherImg = findViewById(R.id.iv_weather_img);
        refresh = findViewById(R.id.refresh);
        llPilihCity = findViewById(R.id.ll_pilih_city);
        llAdd = findViewById(R.id.ll_add);

        //Presenter
        presenter = new WeatherPresenter(this, context);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        sp = context.getSharedPreferences("location",Context.MODE_PRIVATE);
        city = sp.getString(CITY, "");
        region = sp.getString(REGION, "");
        lat = sp.getString(LAT, null);
        lng = sp.getString(LNG, null);

        if (lat == null && lng == null) {
            Intent intent = new Intent(context, CityListActivity.class);
            someActivityResultLauncher.launch(intent);
        } else {
            refresh.setRefreshing(true);
            presenter.loadWeather(lat, lng, "minutely, alerts, hourly");
            tvLocation.setText(city+", "+region);
        }

        llPilihCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CityListActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadWeather(lat, lng, "minutely, alerts, hourly");
            }
        });
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh.setRefreshing(true);

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    refresh.setRefreshing(false);
                                    lat = String.valueOf(location.getLatitude());
                                    lng = String.valueOf(location.getLongitude());
                                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        city = addresses.get(0).getSubAdminArea();
                                        region = addresses.get(0).getCountryName();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    SharedPreferences.Editor spe = sp.edit();
                                    spe.putString(LAT, lat);
                                    spe.putString(LNG, lng);
                                    spe.putString(CITY, city);
                                    spe.putString(REGION, region);
                                    spe.apply();

                                    presenter.loadWeather(lat, lng, "minutely, alerts, hourly");
                                    tvLocation.setText(city+", "+region);
                                }
                            }
                        });
            }
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        city = data.getStringExtra(CITY);
                        region = data.getStringExtra(REGION);
                        lat = data.getStringExtra(LAT);
                        lng = data.getStringExtra(LNG);

                        SharedPreferences.Editor spe = sp.edit();
                        spe.putString(LAT, lat);
                        spe.putString(LNG, lng);
                        spe.putString(CITY, city);
                        spe.putString(REGION, region);
                        spe.apply();

                        refresh.setRefreshing(true);
                        presenter.loadWeather(lat, lng, "minutely, alerts, hourly");
                        tvLocation.setText(city+", "+region);
                    }
                }
            });

    @Override
    public void setCurrentWeater(Current current, int status) {
        refresh.setRefreshing(false);
        String[] temps = String.valueOf(current.getTemp()).split("\\.");
        tvHumidity.setText(current.getPressure()+" hPa.");
        tvEstimated.setText(current.getHumidity()+"%");
        tvDeskWeater.setText(current.getWeather().get(0).getDescription());
        tvTemperature.setText(temps[0]);
        getIcon(current.getWeather().get(0).getId(), current.getWeather().get(0).getMain(), ivWeatherImg);
        tvDate.setText(toDate(current.getDt()));
    }

    @Override
    public void setAdapterWeather(WeatherAdapter mAdapter, int status) {
        refresh.setRefreshing(false);
        if (status == STATUS_SUKSES){
            rcvWeathers.setHasFixedSize(true);
            rcvWeathers.setNestedScrollingEnabled(false);
            rcvWeathers.setLayoutManager(new SpanningLinearLayoutManager(MainActivity.this, SpanningLinearLayoutManager.HORIZONTAL, false));
            rcvWeathers.setAdapter(mAdapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}