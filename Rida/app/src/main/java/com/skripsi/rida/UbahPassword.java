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


public class UbahPassword extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    SessionManager session;
    EditText OldPassword,NewPassword,RePassword;
    TextView Simpan;

    private OnFragmentInteractionListener mListener;

    public UbahPassword() {
    }
    public static UbahPassword newInstance(String param1, String param2) {
        UbahPassword fragment = new UbahPassword();
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
        View view = inflater.inflate(R.layout.fragment_ubah_password, container, false);
        session = new SessionManager(view.getContext());
        session.checkLogin();
        final HashMap<String, String> user = session.getUserDetails();
        OldPassword = (EditText) view.findViewById(R.id.oldPassword);
        NewPassword = (EditText) view.findViewById(R.id.newPassword);
        RePassword = (EditText) view.findViewById(R.id.rePassword);
        Simpan = (TextView) view.findViewById(R.id.simpan);
        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(validate()){
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.UpdatePassword, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if(jsonObject.getString("status").equals("sukses")){
                                        UbahPassword ubahPassword = new UbahPassword();
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.flContent, ubahPassword).commit();
                                        Snackbar.make(v, "Silahkan Login dengan password yang baru", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                    }
                                    else {

                                        UbahPassword ubahPassword = new UbahPassword();
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.flContent, ubahPassword).commit();
                                        Snackbar.make(v, "Password tidak terupdate", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            UbahPassword ubahPassword = new UbahPassword();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.flContent, ubahPassword).commit();
                            Snackbar.make(v, "Periksa koneksi", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                        }
                    }){
                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded";
                        }
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Password", "y");
                            params.put("OldPassword", OldPassword.getText().toString());
                            params.put("NewPassword",NewPassword.getText().toString());
                            params.put("idUser", user.get(SessionManager.KEY_ID));
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                    requestQueue.add(stringRequest);

                }

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

    public boolean validate(){
        boolean valid = true;
        String Old = OldPassword.getText().toString();
        String New = NewPassword.getText().toString();
        String Re = RePassword.getText().toString();


        if(Old.isEmpty()){
            OldPassword.setError("Harus diisi !");
            valid = false;
        }
        else{
            OldPassword.setError(null);
        }
        if(New.isEmpty()){
            NewPassword.setError("Harus diisi !");
            valid = false;
        }
        else{
            NewPassword.setError(null);
        }
        if(Re.isEmpty()){
            RePassword.setError("Harus diisi !");
            valid = false;
        }
        else{
            RePassword.setError(null);
        }
        if(!Re.equals(New)){
            RePassword.setError("Password tidak sesuai");
            valid = false;
        }
        else{
            NewPassword.setError(null);
            RePassword.setError(null);
        }
        return valid;
    }
}
