package com.skripsi.rida.model;

/**
 * Created by gandhi on 9/18/17.
 */

public class Model_Event {
    String idEvent;
    String idUser;
    String judulEvent;
    String keteranganEvent;
    String fotoEvent;
    public Model_Event(){}
    public Model_Event(String idEvent, String idUser, String judulEvent, String keteranganEvent, String fotoEvent){
        this.idEvent = idEvent;
        this.idUser=idUser;
        this.judulEvent = judulEvent;
        this.keteranganEvent = keteranganEvent;
        this.fotoEvent = fotoEvent;
    }
    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getJudulEvent() {
        return judulEvent;
    }

    public void setJudulEvent(String judulEvent) {
        this.judulEvent = judulEvent;
    }

    public String getKeteranganEvent() {
        return keteranganEvent;
    }

    public void setKeteranganEvent(String keteranganEvent) {
        this.keteranganEvent = keteranganEvent;
    }

    public String getFotoEvent() {
        return fotoEvent;
    }

    public void setFotoEvent(String fotoEvent) {
        this.fotoEvent = fotoEvent;
    }


}
