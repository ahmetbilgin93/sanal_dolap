package com.example.mobilproje;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class KiyafetAdapter2 extends RecyclerView.Adapter<KiyafetAdapter2.MyViewHolder> {

    Context context;
    ArrayList<KiyafetData> list;
    KiyafetAdapter2.onItemClickListener listener;

    public KiyafetAdapter2(Context context, ArrayList<KiyafetData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public KiyafetAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recv_k2,parent,false);
        return new KiyafetAdapter2.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        KiyafetData kiyafetData=list.get(position);
        holder.textTur.setText(kiyafetData.getTur());
        holder.textDesen.setText(kiyafetData.getDesen());
        holder.textFiyat.setText(kiyafetData.getFiyat());
        holder.textRenk.setText(kiyafetData.getFiyat());
        holder.textTarih.setText(kiyafetData.getTarih());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images").child(kiyafetData.getId());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).into(holder.imgid);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textTur, textDesen, textRenk, textTarih, textFiyat;
        Button kombineSec;
        ImageView imgid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textTur = itemView.findViewById(R.id.textTur);
            textDesen = itemView.findViewById(R.id.textDesen);
            textRenk = itemView.findViewById(R.id.textRenk);
            textTarih = itemView.findViewById(R.id.textTarih);
            textFiyat = itemView.findViewById(R.id.textFiyat);

            imgid = itemView.findViewById(R.id.imgid);


            kombineSec = itemView.findViewById(R.id.kombineSec);

            kombineSec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener!=null && position !=RecyclerView.NO_POSITION)
                        listener.onItemClick(list.get(position));
                }
            });



        }
    }
    public interface onItemClickListener{
        void onItemClick(KiyafetData kiyafetData);

    }




    public void setOnItemClickListener(onItemClickListener listener){
        this.listener=listener;

    }
}


