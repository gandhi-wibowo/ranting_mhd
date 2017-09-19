package com.skripsi.rida.model;

/**
 * Created by gandhi on 9/19/17.
 */

public class Model_Random {
    String idR;
    String namaR;
    String alamatR;
    String gambarR;
    String opsiR;
    String otherR;
    String latR;
    String lngR;

    public Model_Random(){}
    public Model_Random(String idR, String namaR, String alamatR, String gambarR, String opsiR, String otherR,String latR, String lngR){
        this.idR = idR;
        this.namaR = namaR;
        this.alamatR = alamatR;
        this.gambarR = gambarR;
        this.opsiR = opsiR;
        this.otherR = otherR;
        this.latR = latR;
        this.lngR = lngR;
    }
    public String getLatR() {
        return latR;
    }

    public void setLatR(String latR) {
        this.latR = latR;
    }

    public String getLngR() {
        return lngR;
    }

    public void setLngR(String lngR) {
        this.lngR = lngR;
    }
    public String getIdR() {
        return idR;
    }

    public void setIdR(String idR) {
        this.idR = idR;
    }

    public String getNamaR() {
        return namaR;
    }

    public void setNamaR(String namaR) {
        this.namaR = namaR;
    }

    public String getAlamatR() {
        return alamatR;
    }

    public void setAlamatR(String alamatR) {
        this.alamatR = alamatR;
    }

    public String getGambarR() {
        return gambarR;
    }

    public void setGambarR(String gambarR) {
        this.gambarR = gambarR;
    }

    public String getOpsiR() {
        return opsiR;
    }

    public void setOpsiR(String opsiR) {
        this.opsiR = opsiR;
    }

    public String getOtherR() {
        return otherR;
    }

    public void setOtherR(String otherR) {
        this.otherR = otherR;
    }


}
