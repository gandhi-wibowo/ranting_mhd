package com.skripsi.rida.model;

/**
 * Created by gandhi on 9/17/17.
 */

public class Model_DataSekolah {
    String idSekolah;
    String idDcr;
    String namaSekolah;
    String alamatSekolah;
    String fotoSekolah;
    String lat;
    String lng;
    public Model_DataSekolah(){}
    public Model_DataSekolah(String idSekolah, String idDcr, String namaSekolah, String alamatSekolah, String fotoSekolah, String lat, String lng){
        this.idSekolah = idSekolah;
        this.idDcr = idDcr;
        this.namaSekolah = namaSekolah;
        this.alamatSekolah = alamatSekolah;
        this.fotoSekolah = fotoSekolah;
        this.lat = lat;
        this.lng = lng;
    }
    public String getIdSekolah() {
        return idSekolah;
    }

    public void setIdSekolah(String idSekolah) {
        this.idSekolah = idSekolah;
    }

    public String getIdDcr() {
        return idDcr;
    }

    public void setIdDcr(String idDcr) {
        this.idDcr = idDcr;
    }

    public String getNamaSekolah() {
        return namaSekolah;
    }

    public void setNamaSekolah(String namaSekolah) {
        this.namaSekolah = namaSekolah;
    }

    public String getAlamatSekolah() {
        return alamatSekolah;
    }

    public void setAlamatSekolah(String alamatSekolah) {
        this.alamatSekolah = alamatSekolah;
    }

    public String getFotoSekolah() {
        return fotoSekolah;
    }

    public void setFotoSekolah(String fotoSekolah) {
        this.fotoSekolah = fotoSekolah;
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
