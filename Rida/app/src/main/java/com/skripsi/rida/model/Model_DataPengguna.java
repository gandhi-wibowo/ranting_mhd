package com.skripsi.rida.model;

/**
 * Created by gandhi on 9/16/17.
 */

public class Model_DataPengguna {
    String IdUser;
    String ParentId;
    String NoInduk;
    String UserLogin;
    String NamaUser;
    String HpUser;
    public Model_DataPengguna(){}
    public Model_DataPengguna(String IdUser, String ParentId, String NoInduk, String UserLogin, String NamaUser, String HpUser){
        this.IdUser = IdUser;
        this.ParentId = ParentId;
        this.NoInduk = NoInduk;
        this.UserLogin = UserLogin;
        this.NamaUser = NamaUser;
        this.HpUser = HpUser;
    }
    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getNoInduk() {
        return NoInduk;
    }

    public void setNoInduk(String noInduk) {
        NoInduk = noInduk;
    }

    public String getUserLogin() {
        return UserLogin;
    }

    public void setUserLogin(String userLogin) {
        UserLogin = userLogin;
    }

    public String getNamaUser() {
        return NamaUser;
    }

    public void setNamaUser(String namaUser) {
        NamaUser = namaUser;
    }

    public String getHpUser() {
        return HpUser;
    }

    public void setHpUser(String hpUser) {
        HpUser = hpUser;
    }


}
