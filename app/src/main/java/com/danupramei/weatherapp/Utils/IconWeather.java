package com.danupramei.weatherapp.Utils;

import android.widget.ImageView;

import com.danupramei.weatherapp.R;

public class IconWeather {
    public static void getIcon(int id, String name, ImageView img){
        switch (name){
            case "Clear":
                img.setImageResource(R.drawable.terang);
                break;
            case "Clouds":
                if (id == 801){
                    img.setImageResource(R.drawable.terang_berawan);
                } else if (id == 802){
                    img.setImageResource(R.drawable.berawan);
                } else {
                    img.setImageResource(R.drawable.mendung);
                }
                break;
            case "Rain":
                if (id == 500 && id == 501){
                    img.setImageResource(R.drawable.hujan_terang);
                } else if (id >= 502 && id <= 504){
                    img.setImageResource(R.drawable.hujan_ringan);
                } else if (id == 511){
                    img.setImageResource(R.drawable.salju);
                } else {
                    img.setImageResource(R.drawable.hujan_lebat);
                }
                break;
            case "Drizzle":
                img.setImageResource(R.drawable.hujan_lebat);
                break;
            case "Thunderstorm":
                img.setImageResource(R.drawable.hujan_badai);
                break;
            case "Snow":
                img.setImageResource(R.drawable.salju);
                break;
            default:
                img.setImageResource(R.drawable.berkabut);
        }
    }
}
