package com.skripsi.rida.model;

/**
 * Created by gandhi on 9/15/17.
 */

public class Model_DataAnggota {
    private String idAnggota;
    private String idDcr;
    private String namaAnggota;
    private String jabatan;
    private String noInduk;
    public Model_DataAnggota(){}
    public Model_DataAnggota(String idAnggota, String idDcr, String namaAnggota, String jabatan, String noInduk){
        this.idAnggota = idAnggota;
        this.idDcr = idDcr;
        this.namaAnggota = namaAnggota;
        this.jabatan = jabatan;
        this.noInduk = noInduk;
    }
    public String getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(String idAnggota) {
        this.idAnggota = idAnggota;
    }

    public String getIdDcr() {
        return idDcr;
    }

    public void setIdDcr(String idDcr) {
        this.idDcr = idDcr;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getNoInduk() {
        return noInduk;
    }

    public void setNoInduk(String noInduk) {
        this.noInduk = noInduk;
    }


}
