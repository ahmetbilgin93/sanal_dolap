package com.example.mobilproje;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class KombinAdapter extends RecyclerView.Adapter<KombinAdapter.MyViewHolder> {
    Context context;
    ArrayList<KombinData> list;
    KombinAdapter.onItemClickListener listener;

    public KombinAdapter(Context context, ArrayList<KombinData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recv_kombin,parent,false);
        return new KombinAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        KombinData kombinData=list.get(position);


                Picasso.with(context).load(kombinData.getBas()).into(holder.img1);
        Picasso.with(context).load(kombinData.getUst1()).into(holder.img2);
        Picasso.with(context).load(kombinData.getUst2()).into(holder.img3);
        Picasso.with(context).load(kombinData.getAlt()).into(holder.img4);
        Picasso.with(context).load(kombinData.getAyak()).into(holder.img5);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img1, img2, img3, img4, img5;
        FloatingActionButton cop;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img1=itemView.findViewById(R.id.img1);
            img2=itemView.findViewById(R.id.img2);
            img3=itemView.findViewById(R.id.img3);
            img4=itemView.findViewById(R.id.img4);
            img5=itemView.findViewById(R.id.img5);

            cop=itemView.findViewById(R.id.cop);

            cop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener!=null && position !=RecyclerView.NO_POSITION) {
                        listener.onCopClick(list.get(position));
                    }
                }
            });

        }
    }

    public interface onItemClickListener{
        void onCopClick(KombinData kombinData);
    }




    public void setOnItemClickListener(KombinAdapter.onItemClickListener listener){
        this.listener=listener;

    }
}
