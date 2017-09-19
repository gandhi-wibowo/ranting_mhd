package com.skripsi.rida;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DetailDataPengguna extends Fragment {

    private static final String ARG_PARAM1 = "id_pengguna";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    SessionManager session;
    EditText NoInduk,UserLogin,NamaUser,NoHp;
    private OnFragmentInteractionListener mListener;

    public DetailDataPengguna() {

    }


    public static DetailDataPengguna newInstance(String param1, String param2) {
        DetailDataPengguna fragment = new DetailDataPengguna();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_data_pengguna, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        GetData(mParam1);
        NoInduk = (EditText) view.findViewById(R.id.no_induk);
        UserLogin = (EditText)view.findViewById(R.id.user_login);
        NamaUser = (EditText) view.findViewById(R.id.nama_user);
        NoHp = (EditText) view.findViewById(R.id.hp_user);

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
    private void GetData(String idUser){
        System.out.println(Constant.DataSaya+idUser);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.DataSaya+idUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                NoInduk.setText(jsonObject.getString("no_induk"));
                                UserLogin.setText(jsonObject.getString("user_login"));
                                NamaUser.setText(jsonObject.getString("nama_user"));
                                NoHp.setText(jsonObject.getString("hp_user"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(getView(), "Periksa Koneksi", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
        ) {
            @Override
            public String getBodyContentType() { return "application/x-www-form-urlencoded";}
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
