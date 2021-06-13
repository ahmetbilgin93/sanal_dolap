package com.example.mobilproje;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Et2Adapter extends RecyclerView.Adapter<Et2Adapter.MyViewHolder> {
    Context context;
    ArrayList<Et2Data> list;
    Et2Adapter.onItemClickListener listener;

    public Et2Adapter(Context context, ArrayList<Et2Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recv_etk,parent,false);
        return new Et2Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Et2Data et2Data=list.get(position);


        Picasso.with(context).load(et2Data.getUrl()).into(holder.etkiyres);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView etkiyres;
        Button etressil;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            etkiyres=itemView.findViewById(R.id.etkiyres);

            etressil=itemView.findViewById(R.id.etressil);

            etressil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener!=null && position !=RecyclerView.NO_POSITION) {
                        listener.onSilClick(list.get(position));
                    }
                }
            });

        }
    }

    public interface onItemClickListener{
        void onSilClick(Et2Data et2Data);
    }




    public void setOnItemClickListener(Et2Adapter.onItemClickListener listener){
        this.listener=listener;

    }
}