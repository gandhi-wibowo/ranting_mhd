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


public class DataKantor extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String idDcr;
    Bitmap bitmap, decoded;
    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60;
    NetworkImageView imageView;
    EditText NamaKantor,AlamatKantor;
    TextView Maps,Simpan,Update;
    SessionManager session;
    ProgressDialog progressDialog;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private Uri filePath;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private OnFragmentInteractionListener mListener;
    public DataKantor() {}
    public static DataKantor newInstance(String param1, String param2) {
        DataKantor fragment = new DataKantor();
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
        View view = inflater.inflate(R.layout.fragment_data_kantor, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
        imageView = (NetworkImageView) view.findViewById(R.id.gambar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        NamaKantor = (EditText) view.findViewById(R.id.namaKantor);
        AlamatKantor = (EditText) view.findViewById(R.id.alamatKantor);
        Simpan = (TextView) view.findViewById(R.id.simpan);
        Update = (TextView) view.findViewById(R.id.update);
        Maps = (TextView) view.findViewById(R.id.maps);
        progressDialog = ProgressDialog.show(getContext(),"","Loading . .",true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.KantorBy+user.get(SessionManager.KEY_ID),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if(jsonObject.getString("status").equals("data_kosong")){
                                    progressDialog.dismiss();
                                    Update.setVisibility(View.GONE);
                                    Maps.setVisibility(View.GONE);
                                }
                                else{
                                    progressDialog.dismiss();
                                    Simpan.setVisibility(View.GONE);
                                    idDcr = jsonObject.getString("id_dcr");
                                    imageView.setImageUrl(Constant.Image+jsonObject.getString("foto_dcr"),imageLoader);
                                    NamaKantor.setText(jsonObject.getString("nama_dcr"));
                                    AlamatKantor.setText(jsonObject.getString("alamat_dcr"));
                                }
                            }
                        } catch (JSONException e) {
                            System.out.println(e);
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
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getStringImage(decoded) != null){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.Kantor,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            if(jsonObject.getString("status").equals("sukses")){
                                                DataKantor dataKantor = new DataKantor();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataKantor).commit();
                                                Snackbar.make(getView(), "Data tersimpan", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            }
                                            else{
                                                DataKantor dataKantor = new DataKantor();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataKantor).commit();
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
                            params.put("dcr_baru","y");
                            params.put("id_user",user.get(SessionManager.KEY_ID));
                            params.put("image", getStringImage(decoded));
                            params.put("nama_dcr", NamaKantor.getText().toString());
                            params.put("alamat_dcr",AlamatKantor.getText().toString());
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRequest);
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.Kantor,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            if(jsonObject.getString("status").equals("sukses")){
                                                DataKantor dataKantor = new DataKantor();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataKantor).commit();
                                                Snackbar.make(getView(), "Data tersimpan", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            }
                                            else{
                                                DataKantor dataKantor = new DataKantor();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataKantor).commit();
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
                            params.put("dcr_baru","y");
                            params.put("id_user",user.get(SessionManager.KEY_ID));
                            params.put("nama_dcr", NamaKantor.getText().toString());
                            params.put("alamat_dcr",AlamatKantor.getText().toString());
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRequest);
                }

            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(getStringImage(decoded) != null){
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.Kantor,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            if(jsonObject.getString("status").equals("sukses")){
                                                DataKantor dataKantor = new DataKantor();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataKantor).commit();
                                                Snackbar.make(getView(), "Data terupdate", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            }
                                            else{
                                                DataKantor dataKantor = new DataKantor();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataKantor).commit();
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
                            params.put("editDcr", "y");
                            params.put("id_dcr", idDcr);
                            params.put("image", getStringImage(decoded));
                            params.put("nama_dcr", NamaKantor.getText().toString());
                            params.put("alamat_dcr",AlamatKantor.getText().toString());
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRequest);
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.Kantor,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            if(jsonObject.getString("status").equals("sukses")){
                                                DataKantor dataKantor = new DataKantor();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataKantor).commit();
                                                Snackbar.make(getView(), "Data terupdate", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                            }
                                            else{
                                                DataKantor dataKantor = new DataKantor();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.flContent,dataKantor).commit();
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
                            params.put("editDcr", "y");
                            params.put("id_dcr", idDcr);
                            params.put("nama_dcr", NamaKantor.getText().toString());
                            params.put("alamat_dcr",AlamatKantor.getText().toString());
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
                bundle.putString("id_dcr",idDcr);
                PosisiBangunan posisiBangunan = new PosisiBangunan();
                posisiBangunan.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent,posisiBangunan).commit();
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