package com.danupramei.weatherapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danupramei.weatherapp.Models.Daily;
import com.danupramei.weatherapp.R;

import java.util.List;

import static com.danupramei.weatherapp.Utils.DateConverter.toDay;
import static com.danupramei.weatherapp.Utils.IconWeather.getIcon;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<Daily> weatherList;
    private Context context;

    public WeatherAdapter(Context context, List<Daily> weatherList) {
        super();
        this.weatherList = weatherList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivWeatherIcon;
        TextView tvTemperatureItem, tvDayItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivWeatherIcon = itemView.findViewById(R.id.iv_weather_icon);
            tvTemperatureItem = itemView.findViewById(R.id.tv_temperature_item);
            tvDayItem = itemView.findViewById(R.id.tv_day_item);
        }
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_forcase, parent, false);
        return new WeatherAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        Daily weather = weatherList.get(position);

        String[] temperature = String.valueOf(weather.getTemp().getDay()).split("\\.");
        getIcon(weather.getWeather().get(0).getId(), weather.getWeather().get(0).getMain(), holder.ivWeatherIcon);
        holder.tvTemperatureItem.setText(temperature[0]);
        holder.tvDayItem.setText(toDay(weather.getDt()));
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
