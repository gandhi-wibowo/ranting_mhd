package com.skripsi.rida;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skripsi.rida.adapter.Adapter_Komentar;
import com.skripsi.rida.app.AppController;
import com.skripsi.rida.model.Model_Komentar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailEvent extends Fragment {
    private RecyclerView rv_komentar;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lmKomentar;
    private List<Model_Komentar> model_komentars = new ArrayList<Model_Komentar>();
    String idEvent,JudulEvent,FotoEvent,KetEvent,IdUser;
    NetworkImageView imageView;
    EditText Komentar;
    TextView judulEvent,ketEvent,Kirim;
    SessionManager session;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private OnFragmentInteractionListener mListener;
    public DetailEvent() { }
    public static DetailEvent newInstance(String idEvent, String JudulEvent, String FotoEvent, String KetEvent) {
        DetailEvent fragment = new DetailEvent();
        Bundle args = new Bundle();
        args.putString("id_event",idEvent);
        args.putString("nama_event", JudulEvent);
        args.putString("foto_event", FotoEvent);
        args.putString("keterangan_event", KetEvent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idEvent = getArguments().getString("id_event");
            JudulEvent = getArguments().getString("nama_event");
            FotoEvent = getArguments().getString("foto_event");
            KetEvent = getArguments().getString("keterangan_event");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_event, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        IdUser = user.get(SessionManager.KEY_ID);
        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
        Komentar = (EditText) view.findViewById(R.id.komentar);
        Kirim = (TextView) view.findViewById(R.id.kirim);
        imageView = (NetworkImageView) view.findViewById(R.id.image_event);
        judulEvent = (TextView) view.findViewById(R.id.nama_event);
        ketEvent = (TextView) view.findViewById(R.id.keterangan_event);
        imageView.setImageUrl(Constant.Image+FotoEvent,imageLoader);
        judulEvent.setText(JudulEvent);
        ketEvent.setText(KetEvent);
        GetKomentar(idEvent);
        rv_komentar = (RecyclerView) view.findViewById(R.id.rv_komentar);
        rv_komentar.setHasFixedSize(true);
        lmKomentar = new LinearLayoutManager(getActivity());
        rv_komentar.setLayoutManager(lmKomentar);
        adapter = new Adapter_Komentar(model_komentars);
        rv_komentar.setAdapter(adapter);

        Kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.Komentar,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Event event = new Event();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.flContent, event).commit();
                                Snackbar.make(v, "Komentar terkirim", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Event event = new Event();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.flContent, event).commit();
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
                        params.put("simpan","Y");
                        params.put("id_user",user.get(SessionManager.KEY_ID));
                        params.put("id_event",idEvent);
                        params.put("komentar",Komentar.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                requestQueue.add(stringRequest);
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

    public void GetKomentar(String idEvent){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.GetKomentar+"&id_event="+idEvent,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Model_Komentar model_komentar = new Model_Komentar();
                                model_komentar.setIdEvent(jsonObject.getString("id_event"));
                                model_komentar.setIdKomentar(jsonObject.getString("id_komentar"));
                                model_komentar.setNamaKomentator(jsonObject.getString("nama_user"));
                                model_komentar.setIsiKomentar(jsonObject.getString("komentar"));
                                model_komentar.setTglKomentar(jsonObject.getString("tgl_komentar"));
                                model_komentars.add(model_komentar);
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
