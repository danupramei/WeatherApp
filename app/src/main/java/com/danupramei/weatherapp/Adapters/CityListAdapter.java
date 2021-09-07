package com.danupramei.weatherapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danupramei.weatherapp.Models.City;
import com.danupramei.weatherapp.R;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {
    private List<City> cityList;
    private Context context;
    private CitySelectListener mCallback;

    public interface CitySelectListener {
        public void onCitySelected(City city, int i);
    }

    public void setCitySelectListener(CitySelectListener mCallback) {
        this.mCallback = mCallback;
    }

    public CityListAdapter(Context context, List<City> cityList) {
        super();
        this.cityList = cityList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llCity;
        TextView tvCity, tvLatLng, tvRegion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvLatLng = itemView.findViewById(R.id.tv_lat_lng);
            tvRegion = itemView.findViewById(R.id.tv_region);
            llCity = itemView.findViewById(R.id.ll_city);
        }
    }

    @NonNull
    @Override
    public CityListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_city, parent, false);
        return new CityListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityListAdapter.ViewHolder holder, int position) {
        City city = cityList.get(position);

        holder.tvCity.setText(city.getNamaKota());
        holder.tvRegion.setText(city.getNegara());
        holder.tvLatLng.setText(city.getLat()+", "+city.getLng());

        holder.llCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onCitySelected(city, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }
}
