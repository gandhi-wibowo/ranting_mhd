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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class EditDataAnggota extends Fragment {
    EditText namaAnggota, noInduk,jabatan;
    TextView simpan;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String NamaAnggota,NoInduk,Jabatan,IdAnggota;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditDataAnggota() {

    }
    public static EditDataAnggota newInstance(String idAnggota, String namaAnggota, String jabatan, String noInduk) {
        EditDataAnggota fragment = new EditDataAnggota();
        Bundle args = new Bundle();
        args.putString("no_induk",noInduk);
        args.putString("id_data_anggota", idAnggota);
        args.putString("nama_anggota", namaAnggota);
        args.putString("jabatan", jabatan);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            NamaAnggota = getArguments().getString("nama_anggota");
            IdAnggota = getArguments().getString("id_data_anggota");
            NoInduk = getArguments().getString("no_induk");
            Jabatan = getArguments().getString("jabatan");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_data_anggota, container, false);
        namaAnggota = (EditText) view.findViewById(R.id.nama_anggota);
        noInduk = (EditText) view.findViewById(R.id.no_induk);
        jabatan = (EditText) view.findViewById(R.id.jabatan);
        simpan = (TextView) view.findViewById(R.id.simpan);
        namaAnggota.setText(NamaAnggota);
        noInduk.setText(NoInduk);
        jabatan.setText(Jabatan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.UpdateAnggota, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if(jsonObject.getString("status").equals("sukses")){
                                    DataAnggota dataAnggota = new DataAnggota();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.flContent, dataAnggota).commit();
                                    Snackbar.make(v, "Data terupdate", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                }
                                else {
                                    DataAnggota dataAnggota = new DataAnggota();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.flContent, dataAnggota).commit();
                                    Snackbar.make(v, "Data tidak terupdate", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DataAnggota dataAnggota = new DataAnggota();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.flContent, dataAnggota).commit();
                        Snackbar.make(v, "Data tidak terupdate", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("update", "y");
                        params.put("id_anggota", IdAnggota);
                        params.put("nama_anggota",namaAnggota.getText().toString());
                        params.put("jabatan", jabatan.getText().toString());
                        params.put("no_induk", noInduk.getText().toString());
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
