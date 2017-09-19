package com.skripsi.rida.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsi.rida.R;
import com.skripsi.rida.model.Model_Notifikasi;

import java.util.List;

/**
 * Created by gandhi on 9/19/17.
 */

public class Adapter_Notifikasi extends RecyclerView.Adapter<Adapter_Notifikasi.ViewHolder> {
    private  List<Model_Notifikasi> model_notifikasis;
    public  Adapter_Notifikasi(List<Model_Notifikasi> model_notifikasis){
        this.model_notifikasis = model_notifikasis;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifikasi, parent, false);// xmlnya blm di buat
        Adapter_Notifikasi.ViewHolder viewHolder = new Adapter_Notifikasi.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model_Notifikasi model_notifikasi = model_notifikasis.get(position);
        holder.isiNotifikasi.setText(model_notifikasi.getIsiNotifikasi());
        holder.tglNotifikasi.setText(model_notifikasi.getTglNotifikasi());
    }

    @Override
    public int getItemCount() {
        return model_notifikasis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView  idNotifikasi,isiNotifikasi,idUser,idEvent,tglNotifikasi;

        public ViewHolder(View itemView) {
            super(itemView);
            isiNotifikasi = (TextView) itemView.findViewById(R.id.isi_notifikasi);
            tglNotifikasi = (TextView) itemView.findViewById(R.id.tgl_notifikasi);
        }
    }
}
