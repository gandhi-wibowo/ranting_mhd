package com.skripsi.rida.adapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsi.rida.EditDataAnggota;
import com.skripsi.rida.R;
import com.skripsi.rida.model.Model_DataAnggota;

import java.util.List;

/**
 * Created by gandhi on 9/15/17.
 */

public class Adapter_DataAnggota extends RecyclerView.Adapter<Adapter_DataAnggota.ViewHolder> {
    private List<Model_DataAnggota> model_dataAnggotas;

    public Adapter_DataAnggota(List<Model_DataAnggota> model_dataAnggotas){this.model_dataAnggotas = model_dataAnggotas;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dataanggota, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model_DataAnggota model_dataAnggota = model_dataAnggotas.get(position);
        holder.namaAnggota.setText(model_dataAnggota.getNamaAnggota());
        holder.jabatan.setText(model_dataAnggota.getJabatan());
        holder.noInduk.setText(model_dataAnggota.getNoInduk());
    }

    @Override
    public int getItemCount() {
        return model_dataAnggotas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView namaAnggota,jabatan,noInduk;
        public ViewHolder(View itemView) {
            super(itemView);
            namaAnggota = (TextView) itemView.findViewById(R.id.nama_anggota);
            jabatan = (TextView) itemView.findViewById(R.id.jabatan);
            noInduk = (TextView) itemView.findViewById(R.id.no_induk);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Model_DataAnggota model_dataAnggota = model_dataAnggotas.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("id_data_anggota",model_dataAnggota.getIdAnggota());
                    bundle.putString("nama_anggota",model_dataAnggota.getNamaAnggota());
                    bundle.putString("jabatan",model_dataAnggota.getJabatan());
                    bundle.putString("no_induk",model_dataAnggota.getNoInduk());
                    EditDataAnggota editDataAnggota = new EditDataAnggota();
                    editDataAnggota.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, editDataAnggota).addToBackStack(null).commit();
                }
            });
        }
    }
}
