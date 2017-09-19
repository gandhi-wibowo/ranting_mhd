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
import com.skripsi.rida.adapter.Adapter_Random;
import com.skripsi.rida.model.Model_Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 1. get by dcr berdasarkan level
public class Cabang extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView rv_randomt;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm_random;
    private List<Model_Random> model_randoms = new ArrayList<Model_Random>();
    SessionManager session;
    private OnFragmentInteractionListener mListener;

    public Cabang() {
    }
    public static Cabang newInstance(String param1, String param2) {
        Cabang fragment = new Cabang();
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
        View view = inflater.inflate(R.layout.fragment_cabang, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        GetDataCabang(); // cabang, file yg pertama di buka
        rv_randomt = (RecyclerView) view.findViewById(R.id.rv_random);
        rv_randomt.setHasFixedSize(true);
        lm_random = new LinearLayoutManager(getActivity());
        rv_randomt.setLayoutManager(lm_random);
        adapter = new Adapter_Random(model_randoms);
        rv_randomt.setAdapter(adapter);
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

    public void GetDataCabang(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.DataCabang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            model_randoms.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Model_Random model_random = new Model_Random();
                                model_random.setIdR(jsonObject.getString("id_user"));// id usernya
                                model_random.setGambarR(jsonObject.getString("foto_dcr"));
                                model_random.setNamaR(jsonObject.getString("nama_dcr"));
                                model_random.setAlamatR(jsonObject.getString("alamat_dcr"));
                                model_random.setOtherR(jsonObject.getString("nama_user"));
                                model_random.setLatR(jsonObject.getString("lat"));
                                model_random.setLngR(jsonObject.getString("lng"));
                                model_random.setOpsiR(jsonObject.getString("level"));
                                model_randoms.add(model_random);
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
                        Snackbar.make(getView(), "Gagal mengumpulkan data", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
