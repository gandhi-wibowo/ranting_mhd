package com.skripsi.rida;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.skripsi.rida.app.AppController;

public class DetailSekolah extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    NetworkImageView imageView;
    TextView NamaR,AlamatR,Nama_ketua,Maps;
    String idR,gambarR,namaR,alamatR,nama_ketua,lat,lng,Level;
    SessionManager session;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private OnFragmentInteractionListener mListener;

    public DetailSekolah() {
    }
    public static DetailSekolah newInstance(String param1, String param2) {
        DetailSekolah fragment = new DetailSekolah();
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
        View view = inflater.inflate(R.layout.fragment_detail_sekolah, container, false);
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
