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
import com.skripsi.rida.adapter.Adapter_Event;
import com.skripsi.rida.model.Model_Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Event extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView rv_dataEvent;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm_dataEvent;
    private List<Model_Event> model_events = new ArrayList<Model_Event>();
    private DataSekolah.OnFragmentInteractionListener mListener;
    SessionManager session;
    public Event() {
    }
    public static Event newInstance(String param1, String param2) {
        Event fragment = new Event();
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
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.Event+"?All=y",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            model_events.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Model_Event model_event = new Model_Event();
                                model_event.setIdEvent(jsonObject.getString("id_event"));
                                model_event.setIdUser(jsonObject.getString("id_user"));
                                model_event.setJudulEvent(jsonObject.getString("judul_event"));
                                model_event.setKeteranganEvent(jsonObject.getString("keterangan_event"));
                                model_event.setFotoEvent(jsonObject.getString("foto_event"));
                                model_events.add(model_event);
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

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBaru eventBaru = new EventBaru();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent,eventBaru).commit();

            }
        });

        rv_dataEvent = (RecyclerView) view.findViewById(R.id.rv_dataEvent);
        rv_dataEvent.setHasFixedSize(true);
        lm_dataEvent = new LinearLayoutManager(getActivity());
        rv_dataEvent.setLayoutManager(lm_dataEvent);
        adapter = new Adapter_Event(model_events);
        rv_dataEvent.setAdapter(adapter);
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
