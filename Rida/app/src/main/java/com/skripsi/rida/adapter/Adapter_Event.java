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
import com.skripsi.rida.DetailEvent;
import com.skripsi.rida.R;
import com.skripsi.rida.app.AppController;
import com.skripsi.rida.model.Model_Event;

import java.util.List;

public class Adapter_Event extends RecyclerView.Adapter<Adapter_Event.ViewHolder> {
    private List<Model_Event> model_events;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public Adapter_Event(List<Model_Event> model_events){
        this.model_events = model_events;
    }

    @Override
    public Adapter_Event.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        Adapter_Event.ViewHolder viewHolder = new Adapter_Event.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter_Event.ViewHolder holder, int position) {
        Model_Event model_event = model_events.get(position);
        if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader();
        holder.FotoEvent.setImageUrl(Constant.Image+model_event.getFotoEvent(),imageLoader);
        holder.NamaEvent.setText(model_event.getJudulEvent());
        holder.KeteranganEvent.setText(model_event.getKeteranganEvent());
    }

    @Override
    public int getItemCount() {
        return this.model_events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView NamaEvent,KeteranganEvent;
        public NetworkImageView FotoEvent;
        public ViewHolder(View itemView) {
            super(itemView);
            FotoEvent = (NetworkImageView) itemView.findViewById(R.id.image_event);
            NamaEvent = (TextView) itemView.findViewById(R.id.nama_event);
            KeteranganEvent = (TextView) itemView.findViewById(R.id.keterangan_event);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Model_Event model_event = model_events.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("id_event",model_event.getIdEvent());
                    bundle.putString("nama_event",model_event.getJudulEvent());
                    bundle.putString("foto_event",model_event.getFotoEvent());
                    bundle.putString("keterangan_event",model_event.getKeteranganEvent());
                    DetailEvent detailEvent = new DetailEvent();
                    detailEvent.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.flContent, detailEvent).addToBackStack(null).commit();
                }
            });
        }
    }
}
