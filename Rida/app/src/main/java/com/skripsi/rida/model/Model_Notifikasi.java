package com.skripsi.rida.model;

/**
 * Created by gandhi on 9/19/17.
 */

public class Model_Notifikasi {

    String idNotifikasi;
    String isiNotifikasi;
    String idUser;
    String idEvent;
    String tglNotifikasi;
    public Model_Notifikasi(){}
    public Model_Notifikasi(String idNotifikasi, String isiNotifikasi, String idUser, String idEvent, String tglNotifikasi){
        this.idNotifikasi = idNotifikasi;
        this.isiNotifikasi = isiNotifikasi;
        this.idUser = idUser;
        this.idEvent = idEvent;
        this.tglNotifikasi = tglNotifikasi;
    }

    public String getIdNotifikasi() {
        return idNotifikasi;
    }

    public void setIdNotifikasi(String idNotifikasi) {
        this.idNotifikasi = idNotifikasi;
    }

    public String getIsiNotifikasi() {
        return isiNotifikasi;
    }

    public void setIsiNotifikasi(String isiNotifikasi) {
        this.isiNotifikasi = isiNotifikasi;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getTglNotifikasi() {
        return tglNotifikasi;
    }

    public void setTglNotifikasi(String tglNotifikasi) {
        this.tglNotifikasi = tglNotifikasi;
    }


}
