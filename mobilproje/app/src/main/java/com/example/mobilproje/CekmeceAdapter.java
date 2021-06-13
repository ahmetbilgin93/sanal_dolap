package com.example.mobilproje;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CekmeceAdapter extends RecyclerView.Adapter<CekmeceAdapter.MyViewHolder> {

    Context context;
    ArrayList<CekmeceData> list;
    onItemClickListener listener;



    public CekmeceAdapter(Context context, ArrayList<CekmeceData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.recv_cekmece,parent,false);
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CekmeceData cekmeceData=list.get(position);
        holder.cekmeceAd.setText(cekmeceData.getId());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cekmeceAd;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cekmeceAd = itemView.findViewById(R.id.cekmeceAd);

            cekmeceAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(list.get(position));
                    }
                }
            });
        }


    }

    public interface  onItemClickListener{
        void onItemClick (CekmeceData cekmeceData);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener=listener;
    }
}
