package com.skripsi.rida;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.skripsi.rida.adapter.Adapter_DataSekolah;
import com.skripsi.rida.model.Model_DataSekolah;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataSekolah extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView rv_dataSekolah;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm_dataSekolah;
    private List<Model_DataSekolah> model_dataSekolahs = new ArrayList<Model_DataSekolah>();
    private OnFragmentInteractionListener mListener;
    SessionManager session;

    public DataSekolah() {
    }
    public static DataSekolah newInstance(String param1, String param2) {
        DataSekolah fragment = new DataSekolah();
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
        View view = inflater.inflate(R.layout.fragment_data_sekolah, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.Sekolah+"?getBy="+user.get(SessionManager.KEY_ID),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            model_dataSekolahs.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Model_DataSekolah model_dataSekolah = new Model_DataSekolah();
                                model_dataSekolah.setIdSekolah(jsonObject.getString("id_sekolah"));
                                model_dataSekolah.setAlamatSekolah(jsonObject.getString("alamat_sekolah"));
                                model_dataSekolah.setFotoSekolah(jsonObject.getString("foto_sekolah"));
                                model_dataSekolah.setNamaSekolah(jsonObject.getString("nama_sekolah"));
                                model_dataSekolahs.add(model_dataSekolah);
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
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahDataSekolah tambahDataSekolah = new TambahDataSekolah();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent,tambahDataSekolah).commit();
            }
        });
        rv_dataSekolah = (RecyclerView) view.findViewById(R.id.rv_dataSekolah);
        rv_dataSekolah.setHasFixedSize(true);
        lm_dataSekolah = new LinearLayoutManager(getActivity());
        rv_dataSekolah.setLayoutManager(lm_dataSekolah);
        adapter = new Adapter_DataSekolah(model_dataSekolahs);
        rv_dataSekolah.setAdapter(adapter);
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
}
