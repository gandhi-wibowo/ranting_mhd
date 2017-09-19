package com.skripsi.rida.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsi.rida.R;
import com.skripsi.rida.model.Model_Komentar;

import java.util.List;

/**
 * Created by gandhi on 9/19/17.
 */

public class Adapter_Komentar extends RecyclerView.Adapter<Adapter_Komentar.ViewHolder> {
    private List<Model_Komentar> model_komentars;
    public Adapter_Komentar(List<Model_Komentar> model_komentars){
        this.model_komentars = model_komentars;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_komentar, parent, false);// xmlnya blm di buat
        Adapter_Komentar.ViewHolder viewHolder = new Adapter_Komentar.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model_Komentar model_komentar = model_komentars.get(position);
        holder.tglKomentar.setText(model_komentar.getTglKomentar());
        holder.namaKomentator.setText(model_komentar.getNamaKomentator());
        holder.isiKomentar.setText(model_komentar.getIsiKomentar());
    }

    @Override
    public int getItemCount() {
        return model_komentars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView namaKomentator,tglKomentar,isiKomentar;

        public ViewHolder(View itemView) {
            super(itemView);
            namaKomentator = (TextView) itemView.findViewById(R.id.nama_komentator);
            tglKomentar = (TextView) itemView.findViewById(R.id.tgl_komentar);
            isiKomentar = (TextView) itemView.findViewById(R.id.isi_komentar);
        }
    }
}
