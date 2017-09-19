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

public class TambahAnggota extends Fragment {
    EditText namaAnggota, noInduk,jabatan;
    TextView simpan;
    SessionManager session;
    private String NamaAnggota,NoInduk,Jabatan,IdAnggota,IdUser;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TambahAnggota() {

    }


    public static TambahAnggota newInstance(String idUser, String param2) {
        TambahAnggota fragment = new TambahAnggota();
        Bundle args = new Bundle();
        args.putString("id_user", idUser);
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
            IdUser = getArguments().getString("id_user");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tambah_anggota, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        namaAnggota = (EditText) view.findViewById(R.id.nama_anggota);
        noInduk = (EditText) view.findViewById(R.id.no_induk);
        jabatan = (EditText) view.findViewById(R.id.jabatan);
        simpan = (TextView) view.findViewById(R.id.simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.TambahAnggota,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                DataAnggota dataAnggota = new DataAnggota();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.flContent, dataAnggota).commit();
                                Snackbar.make(v, "Data terupdate", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                DataAnggota dataAnggota = new DataAnggota();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.flContent, dataAnggota).commit();
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
                        params.put("nama_anggota",namaAnggota.getText().toString());
                        params.put("jabatan",jabatan.getText().toString());
                        params.put("no_induk",noInduk.getText().toString());
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
}
