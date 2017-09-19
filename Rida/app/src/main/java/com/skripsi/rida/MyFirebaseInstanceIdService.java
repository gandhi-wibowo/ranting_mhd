package com.skripsi.rida;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        CreateShared("Token","tokenKey",recent_token);
    }

    private void CreateShared(String PrefName, String KeyName, String Value){
        SharedPreferences sharedpreferences = getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(KeyName,Value);
        editor.commit();
    }
}
