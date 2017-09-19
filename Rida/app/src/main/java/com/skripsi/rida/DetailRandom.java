package com.skripsi.rida;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skripsi.rida.adapter.Adapter_Random;
import com.skripsi.rida.app.AppController;
import com.skripsi.rida.model.Model_Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailRandom extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView rv_random;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lmRandom;
    NetworkImageView imageView;
    TextView NamaR,AlamatR,Nama_ketua,Maps;
    private List<Model_Random> model_randoms = new ArrayList<Model_Random>();
    String idR,gambarR,namaR,alamatR,nama_ketua,lat,lng,Level;
    SessionManager session;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private OnFragmentInteractionListener mListener;

    public DetailRandom() { }

    public static DetailRandom newInstance(String param1, String param2) {
        DetailRandom fragment = new DetailRandom();
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
            idR = getArguments().getString("idR");// id user
            gambarR = getArguments().getString("gambarR");
            namaR = getArguments().getString("namaR");
            alamatR = getArguments().getString("alamatR");
            nama_ketua = getArguments().getString("nama_ketua");
            lat = getArguments().getString("lat");
            lng = getArguments().getString("lng");
            Level = getArguments().getString("level");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_random, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        imageView = (NetworkImageView) view.findViewById(R.id.image_random);
        NamaR = (TextView) view.findViewById(R.id.nama_random);
        AlamatR = (TextView) view.findViewById(R.id.alamat_random);
        Nama_ketua = (TextView) view.findViewById(R.id.other_random);
        Maps = (TextView) view.findViewById(R.id.maps);
        Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("latitude",lat);
                bundle.putString("longitude",lng);
                bundle.putString("title",namaR);
                bundle.putString("snippet",alamatR);
                Maps maps = new Maps();
                maps.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContent, maps)
                        .addToBackStack(null)
                        .commit();

            }
        });
        imageView.setImageUrl(Constant.Image+gambarR,imageLoader);
        NamaR.setText(namaR);
        AlamatR.setText(alamatR);
        Nama_ketua.setText(nama_ketua);
        if(Level.equals("4")){
            DataSekolah(idR);
        }
        else {
            DataRanting(idR);
        }
        rv_random = (RecyclerView) view.findViewById(R.id.rv_random);
        rv_random.setHasFixedSize(true);
        lmRandom = new LinearLayoutManager(getActivity());
        rv_random.setLayoutManager(lmRandom);
        adapter = new Adapter_Random(model_randoms);
        rv_random.setAdapter(adapter);
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

    public void DataRanting(String idUser){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.DataRanting+idUser,
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

    public void DataSekolah(String idUser){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.Sekolah+"?getBy="+idUser,
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
                                model_random.setGambarR(jsonObject.getString("foto_sekolah"));
                                model_random.setNamaR(jsonObject.getString("nama_sekolah"));
                                model_random.setAlamatR(jsonObject.getString("alamat_sekolah"));
                                model_random.setOtherR("-");
                                model_random.setLatR(jsonObject.getString("lat"));
                                model_random.setLngR(jsonObject.getString("lng"));
                                model_random.setOpsiR("5");
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
