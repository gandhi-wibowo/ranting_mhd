package com.skripsi.rida;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import com.skripsi.rida.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class TambahDataSekolah extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    NetworkImageView imageView;
    String idSekolah;
    Bitmap bitmap, decoded;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60;
    EditText NamaSekolah,AlamatSekolah;
    TextView Maps,Simpan,Update;
    SessionManager session;
    ProgressDialog progressDialog;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private Uri filePath;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private OnFragmentInteractionListener mListener;
    public TambahDataSekolah() {}
    public static TambahDataSekolah newInstance(String param1, String param2, String param3) {
        TambahDataSekolah fragment = new TambahDataSekolah();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString("id_sekolah",param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            idSekolah = getArguments().getString("id_sekolah");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_data_sekolah, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        System.out.println(idSekolah);
        final HashMap<String, String> user = session.getUserDetails();
        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
        imageView = (NetworkImageView) view.findViewById(R.id.gambar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        NamaSekolah = (EditText) view.findViewById(R.id.namaSekolah);
        AlamatSekolah = (EditText) view.findViewById(R.id.alamatSekolah);
        Simpan = (TextView) view.findViewById(R.id.simpan);
        Update = (TextView) view.findViewById(R.id.update);
        Maps = (TextView) view.findViewById(R.id.maps);
        progressDialog = ProgressDialog.show(getContext(),"","Loading . .",true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        if (idSekolah != null){
            Update.setVisibility(View.GONE);
            Maps.setVisibility(View.GONE);
            Simpan.setVisibility(View.GONE);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SekolahOne+idSekolah,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if(jsonObject.length() > 1){ // kalau datanya ada
                                        // tampilin button edit sama maps
                                        Update.setVisibility(View.VISIBLE);
                                        Maps.setVisibility(View.VISIBLE);
                                        Simpan.setVisibility(View.GONE);
                                        imageView.setImageUrl(Constant.Image+jsonObject.getString("foto_sekolah"),imageLoader);
                                        NamaSekolah.setText(jsonObject.getString("nama_sekolah"));
                                        AlamatSekolah.setText(jsonObject.getString("alamat_sekolah"));
                                        progressDialog.dismiss();

                                    }
                                    else{
                                        Simpan.setVisibility(View.VISIBLE);
                                        Update.setVisibility(View.GONE);
                                        Maps.setVisibility(View.GONE);
                                        progressDialog.dismiss();

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Snackbar.make(getView(), "Gagal mengambil data", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }
            ) {
                @Override
                public String getBodyContentType() { return "application/x-www-form-urlencoded";}
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
            progressDialog.dismiss();
        }
        else{
            Update.setVisibility(View.GONE);
            Maps.setVisibility(View.GONE);
            progressDialog.dismiss();
        }
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update
                if(getStringImage(decoded) != null){
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.Sekolah,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            if(jsonObject.getString("status").equals("sukses")){
                                                DataSekolah dataSekolah = new DataSekolah();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataSekolah).commit();
                                                Snackbar.make(getView(), "Data terupdate", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            }
                                            else{
                                                DataSekolah dataSekolah = new DataSekolah();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataSekolah).commit();
                                                Snackbar.make(getView(), "Gagal update data", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                                            }                            }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar.make(getView(), "Gagal ! Periksa koneksi internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }){
                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded";
                        }
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("sekolah", "y");
                            params.put("id_sekolah",idSekolah);
                            params.put("image", getStringImage(decoded));
                            params.put("nama_sekolah", NamaSekolah.getText().toString());
                            params.put("alamat_sekolah",AlamatSekolah.getText().toString());
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRequest);
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.Sekolah,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            if(jsonObject.getString("status").equals("sukses")){
                                                DataSekolah dataSekolah = new DataSekolah();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataSekolah).commit();
                                                Snackbar.make(getView(), "Data terupdate", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            }
                                            else{
                                                DataSekolah dataSekolah = new DataSekolah();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataSekolah).commit();
                                                Snackbar.make(getView(), "Gagal update data", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                                            }                            }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar.make(getView(), "Gagal ! Periksa koneksi internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }){
                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded";
                        }
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("sekolah", "y");
                            params.put("id_sekolah",idSekolah);
                            params.put("nama_sekolah", NamaSekolah.getText().toString());
                            params.put("alamat_sekolah",AlamatSekolah.getText().toString());
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRequest);
                }
            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tambah
                if(getStringImage(decoded) != null){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.Sekolah,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            if(jsonObject.getString("status").equals("sukses")){
                                                DataSekolah dataSekolah = new DataSekolah();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataSekolah).commit();
                                                Snackbar.make(getView(), "Data tersimpan", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            }
                                            else{
                                                DataSekolah dataSekolah = new DataSekolah();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataSekolah).commit();
                                                Snackbar.make(getView(), "Data tidak tersimpan", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                                            }

                                        }
                                    } catch (JSONException e) {
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Snackbar.make(getView(), "Gagal ! Periksa koneksi internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id_user",user.get(SessionManager.KEY_ID));
                            params.put("image", getStringImage(decoded));
                            params.put("nama_sekolah", NamaSekolah.getText().toString());
                            params.put("alamat_sekolah",AlamatSekolah.getText().toString());
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRequest);
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.Sekolah,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            if(jsonObject.getString("status").equals("sukses")){
                                                DataSekolah dataSekolah = new DataSekolah();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataSekolah).commit();
                                                Snackbar.make(getView(), "Data tersimpan", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            }
                                            else{
                                                DataSekolah dataSekolah = new DataSekolah();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataSekolah).commit();
                                                Snackbar.make(getView(), "Data tidak tersimpan", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                                            }

                                        }
                                    } catch (JSONException e) {
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Snackbar.make(getView(), "Gagal ! Periksa koneksi internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id_user",user.get(SessionManager.KEY_ID));
                            params.put("nama_sekolah", NamaSekolah.getText().toString());
                            params.put("alamat_sekolah",AlamatSekolah.getText().toString());
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRequest);
                }
            }
        });
        Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("id_sekolah",idSekolah);
                PosisiBangunan posisiBangunan = new PosisiBangunan();
                posisiBangunan.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, posisiBangunan).addToBackStack(null).commit();

            }
        });

        return  view;
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
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public String getStringImage(Bitmap bmp) {
        String encodedImage = null;
        if(bmp == null){

        }
        else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }

        return encodedImage;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 1024));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imageView.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
