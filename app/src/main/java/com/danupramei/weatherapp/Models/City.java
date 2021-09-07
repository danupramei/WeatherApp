package com.danupramei.weatherapp.Models;

public class City {
    private String namaKota;
    private String negara;
    private String lat;
    private String lng;

    public City(String namaKota, String negara, String lat, String lng) {
        this.namaKota = namaKota;
        this.negara = negara;
        this.lat = lat;
        this.lng = lng;
    }

    public String getNamaKota() {
        return namaKota;
    }

    public void setNamaKota(String namaKota) {
        this.namaKota = namaKota;
    }

    public String getNegara() {
        return negara;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
