package com.skripsi.rida;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText EditName,EditPwd;
    TextView Login;
    ProgressDialog progressDialog;
    SessionManager session;
    String username,password,token,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
        EditName = (EditText) findViewById(R.id.email);
        EditPwd = (EditText) findViewById(R.id.password);
        Login = (TextView) findViewById(R.id.signin1);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = EditName.getText().toString();
                password = EditPwd.getText().toString();
                Login(username,password);
            }
        });
    }

    private void Login(String username, String password){
        progressDialog = ProgressDialog.show(this,"Log-in . .","Authentifikasi . .",true);
        if (!validate()) {
            onLoginFailed();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.Login +"username="+username.replace(" ", "%20")+"&password="+password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if(jsonObject.getString("status").equals("sukses")){
                            session.createLoginSession(
                                    jsonObject.getString("user_login"),// nama
                                    jsonObject.getString("id_user"),// id
                                    jsonObject.getString("level"),// type
                                    jsonObject.getString("password"));// password
                            updateToken();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            Snackbar.make(getCurrentFocus(), "Selamat Datang " + jsonObject.getString("nama_user"), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                        else{
                            progressDialog.dismiss();
                            onLoginFailed();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private  void updateToken() {
        HashMap<String, String> user = session.getUserDetails();
        HashMap<String, String> Token = session.getToken();
        token = Token.get("tokenKey");
        id = user.get(SessionManager.KEY_ID);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.Token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", "y");
                params.put("idUser",id);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void onLoginFailed() {
        Snackbar.make(getCurrentFocus(), "Login gagal", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        Login.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        String email = EditName.getText().toString();
        String password = EditPwd.getText().toString();

        if (email.isEmpty() ) {
            EditName.setError("Username tidak boleh kosong");
            valid = false;
        } else {
            EditName.setError(null);
        }

        if (password.isEmpty() ) {
            EditPwd.setError("password tidak boleh kosong");
            valid = false;
        } else {
            EditPwd.setError(null);
        }

        return valid;
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
