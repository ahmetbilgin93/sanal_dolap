package com.example.mobilproje;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EtkinlikAdapter extends RecyclerView.Adapter<EtkinlikAdapter.MyViewHolder> {
    Context context;
    ArrayList<EtkinlikData> list;
    EtkinlikAdapter.onItemClickListener listener;

    public EtkinlikAdapter(Context context, ArrayList<EtkinlikData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recv_etkinlik,parent,false);
        return new EtkinlikAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        EtkinlikData etkinlikData=list.get(position);

        holder.t1.setText(etkinlikData.getIsim());
        holder.t2.setText(etkinlikData.getTur());
        holder.t3.setText(etkinlikData.getLokasyon());
        holder.t4.setText(etkinlikData.getTarih());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView t1, t2, t3, t4, tikla;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);
            tikla = itemView.findViewById(R.id.tikla);

            tikla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onEtkClick(list.get(position));
                    }
                }
            });

        }
    }
    public interface  onItemClickListener{
        void onEtkClick (EtkinlikData etkinlikData);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener=listener;
    }
}
