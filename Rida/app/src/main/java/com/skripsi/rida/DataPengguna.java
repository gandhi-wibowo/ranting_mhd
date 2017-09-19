package com.skripsi.rida;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.skripsi.rida.adapter.Adapter_DataPengguna;
import com.skripsi.rida.model.Model_DataPengguna;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataPengguna extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView rv_dataPengguna;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm_dataPengguna;
    private List<Model_DataPengguna> model_dataPenggunas = new ArrayList<Model_DataPengguna>();
    SessionManager session;
    private OnFragmentInteractionListener mListener;

    public DataPengguna() {    }
    public static DataPengguna newInstance(String param1, String param2) {
        DataPengguna fragment = new DataPengguna();
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
        View view = inflater.inflate(R.layout.fragment_data_pengguna, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        GetPengguna(user.get(SessionManager.KEY_ID));
        rv_dataPengguna = (RecyclerView) view.findViewById(R.id.rv_dataPengguna);
        rv_dataPengguna.setHasFixedSize(true);
        lm_dataPengguna = new LinearLayoutManager(getActivity());
        rv_dataPengguna.setLayoutManager(lm_dataPengguna);
        adapter = new Adapter_DataPengguna(model_dataPenggunas);
        rv_dataPengguna.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahPengguna tambahPengguna = new TambahPengguna();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent,tambahPengguna).commit();
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

    private void GetPengguna(String idUser){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.DataPengguna+idUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            model_dataPenggunas.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Model_DataPengguna model_dataPengguna = new Model_DataPengguna();
                                model_dataPengguna.setIdUser(jsonObject.getString("id_user"));
                                model_dataPengguna.setParentId(jsonObject.getString("parent_id"));
                                model_dataPengguna.setNoInduk(jsonObject.getString("no_induk"));
                                model_dataPengguna.setUserLogin(jsonObject.getString("user_login"));
                                model_dataPengguna.setNamaUser(jsonObject.getString("nama_user"));
                                model_dataPengguna.setHpUser(jsonObject.getString("hp_user"));
                                model_dataPenggunas.add(model_dataPengguna);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Snackbar.make(getView(), "Data tidak ditemukan", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
