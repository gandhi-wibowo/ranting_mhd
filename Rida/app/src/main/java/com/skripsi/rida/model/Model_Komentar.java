package com.skripsi.rida.model;

/**
 * Created by gandhi on 9/19/17.
 */

public class Model_Komentar {
    String idKomentar;
    String idEvent;
    String namaKomentator;
    String isiKomentar;
    String tglKomentar;

    public Model_Komentar(){}
    public Model_Komentar(String idEvent, String idKomentar, String namaKomentator, String isiKomentar, String tglKomentar){
        this.idEvent = idEvent;
        this.idKomentar = idKomentar;
        this.namaKomentator = namaKomentator;
        this.isiKomentar = isiKomentar;
        this.tglKomentar = tglKomentar;
    }

    public String getIdKomentar() {
        return idKomentar;
    }

    public void setIdKomentar(String idKomentar) {
        this.idKomentar = idKomentar;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getNamaKomentator() {
        return namaKomentator;
    }

    public void setNamaKomentator(String namaKomentator) {
        this.namaKomentator = namaKomentator;
    }

    public String getIsiKomentar() {
        return isiKomentar;
    }

    public void setIsiKomentar(String isiKomentar) {
        this.isiKomentar = isiKomentar;
    }

    public String getTglKomentar() {
        return tglKomentar;
    }

    public void setTglKomentar(String tglKomentar) {
        this.tglKomentar = tglKomentar;
    }


}
