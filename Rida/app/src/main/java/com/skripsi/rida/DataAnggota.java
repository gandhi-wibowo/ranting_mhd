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
import com.skripsi.rida.adapter.Adapter_DataAnggota;
import com.skripsi.rida.model.Model_DataAnggota;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataAnggota extends Fragment {
    private RecyclerView rv_dataAnggota;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm_dataAnggota;
    private List<Model_DataAnggota> model_dataAnggotas = new ArrayList<Model_DataAnggota>();

    SessionManager session;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DataAnggota() {

    }
    public static DataAnggota newInstance(String param1, String param2) {
        DataAnggota fragment = new DataAnggota();
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
        View view = inflater.inflate(R.layout.fragment_data_anggota, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        DataAnggota(user.get(SessionManager.KEY_ID));
        rv_dataAnggota = (RecyclerView) view.findViewById(R.id.rv_dataAnggota);
        rv_dataAnggota.setHasFixedSize(true);
        lm_dataAnggota = new LinearLayoutManager(getActivity());
        rv_dataAnggota.setLayoutManager(lm_dataAnggota);
        adapter = new Adapter_DataAnggota(model_dataAnggotas);
        rv_dataAnggota.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahAnggota tambahAnggota = new TambahAnggota();
                Bundle bundle = new Bundle();
                bundle.putString("id_user",user.get(SessionManager.KEY_ID));
                tambahAnggota.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent,tambahAnggota).commit();
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
    private void DataAnggota(String idUser){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.DataAnggota+idUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            model_dataAnggotas.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Model_DataAnggota model_dataAnggota = new Model_DataAnggota();
                                model_dataAnggota.setIdAnggota(jsonObject.getString("id_anggota"));
                                model_dataAnggota.setIdDcr(jsonObject.getString("id_user"));
                                model_dataAnggota.setNamaAnggota(jsonObject.getString("nama_anggota"));
                                model_dataAnggota.setJabatan(jsonObject.getString("jabatan"));
                                model_dataAnggota.setNoInduk(jsonObject.getString("no_induk"));
                                model_dataAnggotas.add(model_dataAnggota);
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
