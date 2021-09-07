package com.danupramei.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.danupramei.weatherapp.Adapters.CityListAdapter;
import com.danupramei.weatherapp.Models.City;

import java.util.ArrayList;
import java.util.List;

import static com.danupramei.weatherapp.Utils.VariableGlobal.CITY;
import static com.danupramei.weatherapp.Utils.VariableGlobal.LAT;
import static com.danupramei.weatherapp.Utils.VariableGlobal.LNG;
import static com.danupramei.weatherapp.Utils.VariableGlobal.REGION;

public class CityListActivity extends AppCompatActivity {
    RecyclerView rcvCity;
    ImageView ivBack;

    CityListAdapter cityListAdapter;
    Context context = CityListActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        ivBack = findViewById(R.id.iv_back);
        rcvCity = findViewById(R.id.rcv_city);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<City> cities = new ArrayList<City>();
        cities.add(new City("Gdansk", "Poland", "54.372158", "18.638306"));
        cities.add(new City("Warszawa", "Poland", "52.237049", "21.017532"));
        cities.add(new City("Krakow", "Poland", "50.049683", "19.944544"));
        cities.add(new City("Wroclaw", "Poland", "51.107883", "17.038538"));
        cities.add(new City("Lodz", "Poland", "51.759445", "19.457216"));

        cityListAdapter = new CityListAdapter(context, cities);
        rcvCity.setHasFixedSize(true);
        rcvCity.setNestedScrollingEnabled(false);
        rcvCity.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rcvCity.setAdapter(cityListAdapter);
        cityListAdapter.setCitySelectListener(new CityListAdapter.CitySelectListener() {
            @Override
            public void onCitySelected(City city, int i) {
                Intent intent = new Intent();
                intent.putExtra(REGION, city.getNegara());
                intent.putExtra(CITY, city.getNamaKota());
                intent.putExtra(LAT, city.getLat());
                intent.putExtra(LNG, city.getLng());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}