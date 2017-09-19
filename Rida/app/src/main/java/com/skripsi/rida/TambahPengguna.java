package com.skripsi.rida;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class TambahPengguna extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    EditText namaPengguna, noInduk,noHp,userName,password,rePassword;
    TextView simpan;
    SessionManager session;

    private OnFragmentInteractionListener mListener;

    public TambahPengguna() {

    }

    public static TambahPengguna newInstance(String param1, String param2) {
        TambahPengguna fragment = new TambahPengguna();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tambah_pengguna, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        namaPengguna = (EditText) view.findViewById(R.id.nama_pengguna);
        noInduk = (EditText) view.findViewById(R.id.no_induk);
        noHp = (EditText) view.findViewById(R.id.hp_user);
        userName = (EditText) view.findViewById(R.id.user_login);
        password = (EditText) view.findViewById(R.id.password);
        rePassword = (EditText) view.findViewById(R.id.rePassword);
        simpan = (TextView) view.findViewById(R.id.simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(validate()){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.TambahPengguna,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                   DataPengguna dataPengguna = new DataPengguna();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.flContent, dataPengguna).commit();
                                    Snackbar.make(v, "Data terupdate", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println(error);
                                    DataPengguna dataPengguna = new DataPengguna();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.flContent, dataPengguna).commit();
                                    Snackbar.make(v, "Gagal ! Periksa koneksi", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                }
                            }
                    ) {
                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded";
                        }

                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Level",user.get(SessionManager.KEY_TYPE));
                            params.put("id_user",user.get(SessionManager.KEY_ID));
                            params.put("nama_user",namaPengguna.getText().toString());
                            params.put("hp_user",noHp.getText().toString());
                            params.put("no_induk",noInduk.getText().toString());
                            params.put("user_login",userName.getText().toString());
                            params.put("password",password.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                    requestQueue.add(stringRequest);
                }

            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
    public boolean validate(){
        boolean valid = true;
        String userName = namaPengguna.getText().toString();
        String New = password.getText().toString();
        String Re = rePassword.getText().toString();


        if(userName.isEmpty()){
            namaPengguna.setError("Harus diisi !");
            valid = false;
        }
        else{
            namaPengguna.setError(null);
        }
        if(New.isEmpty()){
            password.setError("Harus diisi !");
            valid = false;
        }
        else{
            password.setError(null);
        }
        if(Re.isEmpty()){
            rePassword.setError("Harus diisi !");
            valid = false;
        }
        else{
            rePassword.setError(null);
        }
        if(!Re.equals(New)){
            rePassword.setError("Password tidak sesuai");
            valid = false;
        }
        else{
            password.setError(null);
            rePassword.setError(null);
        }
        return valid;
    }
}
