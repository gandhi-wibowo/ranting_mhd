package com.skripsi.rida.adapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsi.rida.DetailDataPengguna;
import com.skripsi.rida.R;
import com.skripsi.rida.model.Model_DataPengguna;

import java.util.List;

/**
 * Created by gandhi on 9/16/17.
 */

public class Adapter_DataPengguna extends RecyclerView.Adapter<Adapter_DataPengguna.ViewHolder> {
    private List<Model_DataPengguna> model_dataPenggunas;

    public Adapter_DataPengguna(List<Model_DataPengguna> model_dataPenggunas){this.model_dataPenggunas = model_dataPenggunas;}


    @Override
    public Adapter_DataPengguna.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_datapengguna, parent, false);
        Adapter_DataPengguna.ViewHolder viewHolder = new Adapter_DataPengguna.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter_DataPengguna.ViewHolder holder, int position) {
        Model_DataPengguna model_dataPengguna = model_dataPenggunas.get(position);
        holder.NamaPengguna.setText(model_dataPengguna.getNamaUser());
        holder.NoInduk.setText(model_dataPengguna.getNoInduk());
        holder.NoHp.setText(model_dataPengguna.getHpUser());
    }

    @Override
    public int getItemCount() {
        return model_dataPenggunas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView NoInduk,NamaPengguna,NoHp;
        public ViewHolder(View itemView) {
            super(itemView);
            NoInduk = (TextView) itemView.findViewById(R.id.no_induk);
            NamaPengguna = (TextView) itemView.findViewById(R.id.nama_pengguna);
            NoHp = (TextView) itemView.findViewById(R.id.no_hp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Model_DataPengguna model_dataPengguna = model_dataPenggunas.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("id_pengguna",model_dataPengguna.getIdUser());
                    DetailDataPengguna detailDataPengguna = new DetailDataPengguna();
                    detailDataPengguna.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, detailDataPengguna).addToBackStack(null).commit();
                }
            });


        }
    }
}
