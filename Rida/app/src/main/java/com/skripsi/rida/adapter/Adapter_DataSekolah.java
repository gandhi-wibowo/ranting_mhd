package com.skripsi.rida.adapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.skripsi.rida.Constant;
import com.skripsi.rida.R;
import com.skripsi.rida.TambahDataSekolah;
import com.skripsi.rida.app.AppController;
import com.skripsi.rida.model.Model_DataSekolah;

import java.util.List;

public class Adapter_DataSekolah extends RecyclerView.Adapter<Adapter_DataSekolah.ViewHolder> {
    private List<Model_DataSekolah> model_dataSekolahs;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public Adapter_DataSekolah(List<Model_DataSekolah> model_dataSekolahs){
        this.model_dataSekolahs = model_dataSekolahs;
    }

    @Override
    public Adapter_DataSekolah.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_datasekolah, parent, false);
        Adapter_DataSekolah.ViewHolder viewHolder = new Adapter_DataSekolah.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter_DataSekolah.ViewHolder holder, int position) {
        Model_DataSekolah model_dataSekolah = model_dataSekolahs.get(position);
        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
        holder.FotoSekolah.setImageUrl(Constant.Image+model_dataSekolah.getFotoSekolah(),imageLoader);
        holder.NamaSekolah.setText(model_dataSekolah.getNamaSekolah());
        holder.AlamatSekolah.setText(model_dataSekolah.getAlamatSekolah());
    }

    @Override
    public int getItemCount() {
        return this.model_dataSekolahs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView NamaSekolah,AlamatSekolah,IdSekolah,Lat,Lng;
        public NetworkImageView FotoSekolah;
        public ViewHolder(View itemView) {
            super(itemView);
            FotoSekolah = (NetworkImageView) itemView.findViewById(R.id.image_sekolah);
            NamaSekolah = (TextView) itemView.findViewById(R.id.nama_sekolah);
            AlamatSekolah = (TextView) itemView.findViewById(R.id.alamat_sekolah);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Model_DataSekolah model_dataSekolah = model_dataSekolahs.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("id_sekolah",model_dataSekolah.getIdSekolah());
                    TambahDataSekolah tambahDataSekolah = new TambahDataSekolah();
                    tambahDataSekolah.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, tambahDataSekolah).addToBackStack(null).commit();
                }
            });
        }
    }
}
