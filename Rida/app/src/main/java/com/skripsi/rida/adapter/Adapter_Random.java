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
import com.skripsi.rida.DetailRandom;
import com.skripsi.rida.DetailSekolah;
import com.skripsi.rida.R;
import com.skripsi.rida.app.AppController;
import com.skripsi.rida.model.Model_Random;

import java.util.List;

public class Adapter_Random extends RecyclerView.Adapter<Adapter_Random.ViewHolder> {
    private List<Model_Random> model_randoms;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public Adapter_Random(List<Model_Random> model_randoms){
        this.model_randoms = model_randoms;
    }
    @Override
    public Adapter_Random.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_random, parent, false);// xmlnya blm di buat
        Adapter_Random.ViewHolder viewHolder = new Adapter_Random.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter_Random.ViewHolder holder, int position) {
        Model_Random model_random = model_randoms.get(position);
        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
        holder.NamaR.setText(model_random.getNamaR());
        holder.GambarR.setImageUrl(Constant.Image+model_random.getGambarR(),imageLoader);
        holder.AlamatR.setText(model_random.getAlamatR());
        holder.OtherR.setText(model_random.getOtherR());
    }

    @Override
    public int getItemCount() {
        return model_randoms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView GambarR;
        public TextView IdR,NamaR,AlamatR,OpsiR,OtherR;
        public ViewHolder(View itemView) {
            super(itemView);
            NamaR = (TextView) itemView.findViewById(R.id.namaR);
            GambarR = (NetworkImageView) itemView.findViewById(R.id.gambarR);
            OtherR = (TextView) itemView.findViewById(R.id.otherR);
            AlamatR = (TextView) itemView.findViewById(R.id.alamatR);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Model_Random model_random = model_randoms.get(position);
                    DetailRandom detailRandom = new DetailRandom();
                    DetailSekolah detailSekolah = new DetailSekolah();
                    Bundle bundle = new Bundle();
                    bundle.putString("idR",model_random.getIdR());
                    bundle.putString("gambarR",model_random.getGambarR());
                    bundle.putString("namaR",model_random.getNamaR());
                    bundle.putString("alamatR",model_random.getAlamatR());
                    bundle.putString("nama_ketua",model_random.getOtherR());
                    bundle.putString("lat",model_random.getLatR());
                    bundle.putString("lng",model_random.getLngR());
                    bundle.putString("level",model_random.getOpsiR());
                    detailRandom.setArguments(bundle);
                    detailSekolah.setArguments(bundle);
                    if(model_random.getOpsiR().equals("5")){
                        // detail sekolah
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flContent, detailSekolah)
                                .addToBackStack(null)
                                .commit();
                    }
                    else{
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flContent, detailRandom)
                                .addToBackStack(null)
                                .commit();

                    }


                }
            });
        }
    }
}
