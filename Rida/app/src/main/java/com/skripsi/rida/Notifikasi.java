package com.skripsi.rida;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skripsi.rida.adapter.Adapter_Notifikasi;
import com.skripsi.rida.model.Model_Notifikasi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Notifikasi extends Fragment {
    private RecyclerView rv_Notifikasi;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm_Notifikasi;
    private List<Model_Notifikasi> model_notifikasis = new ArrayList<Model_Notifikasi>();
    SessionManager session;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Notifikasi() {
    }
    public static Notifikasi newInstance(String param1, String param2) {
        Notifikasi fragment = new Notifikasi();
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
        View view = inflater.inflate(R.layout.fragment_notifikasi, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        Notif(user.get(SessionManager.KEY_ID));

        rv_Notifikasi = (RecyclerView) view.findViewById(R.id.rv_notifikasi);
        rv_Notifikasi.setHasFixedSize(true);
        lm_Notifikasi = new LinearLayoutManager(getActivity());
        rv_Notifikasi.setLayoutManager(lm_Notifikasi);
        adapter = new Adapter_Notifikasi(model_notifikasis);
        rv_Notifikasi.setAdapter(adapter);

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

    private void Notif(String idUser){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.Notifikasi+"?One="+idUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            model_notifikasis.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Model_Notifikasi model_notifikasi = new Model_Notifikasi();
                                model_notifikasi.setIdEvent(jsonObject.getString("id_event"));
                                model_notifikasi.setIdUser(jsonObject.getString("id_user"));
                                model_notifikasi.setIdNotifikasi(jsonObject.getString("id_notifikasi"));
                                model_notifikasi.setIsiNotifikasi(jsonObject.getString("isi_notifikasi"));
                                model_notifikasi.setTglNotifikasi(jsonObject.getString("tgl_notifikasi"));
                                model_notifikasis.add(model_notifikasi);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(getView(), "Gagal mengambil data", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
