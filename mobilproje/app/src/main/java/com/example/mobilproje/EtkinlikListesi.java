package com.example.mobilproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class EtkinlikListesi extends AppCompatActivity {

    String etkin;
    TextView tt1, tt2, tt3, tt4;
    Button addetlis, deletlis;
    FloatingActionButton etedit;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference;
    Et2Adapter et2Adapter;
    ArrayList<Et2Data> list;
    DatabaseReference myRef;
    DatabaseReference imgRef;
    RecyclerView recyclerView;
    Context context=this;
    SP sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlik_listesi);
        sp=new SP();
        tt1 = findViewById(R.id.tt1);
        tt2 = findViewById(R.id.tt2);
        tt3 = findViewById(R.id.tt3);
        tt4 = findViewById(R.id.tt4);

        addetlis = findViewById(R.id.addetlis);
        deletlis = findViewById(R.id.deletlis);

        etedit = findViewById(R.id.etedit);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            etkin = bundle.getString("etkin");
        }

        myRef = database.getReference("Etkinlikler/"+etkin);
        myRef.child("isim").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String isim = snapshot.getValue().toString();
                tt1.setText(isim);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myRef.child("tur").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String tur = snapshot.getValue().toString();
                tt2.setText(tur);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myRef.child("tarih").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String tarih = snapshot.getValue().toString();
                tt4.setText(tarih);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        myRef.child("lokasyon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String lok = snapshot.getValue().toString();
                tt3.setText(lok);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        addetlis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.saveInt(context, "kontrol", 2);
                sp.saveString(context, "aranan2", etkin);
                Intent intent=new Intent(EtkinlikListesi.this, Cek2Activity.class);
                startActivity(intent);
            }
        });
        deletlis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EtkinlikListesi.this);

                builder.setTitle("Etkinlik Silinecek!");
                builder.setMessage("Mevcut etkinlik içindeki kıyafetlerle birlikte silinecek. Emin misin?");
                builder.setCancelable(true);
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myRef.removeValue();
                        Intent intent=new Intent(EtkinlikListesi.this, EtkinliklerActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Etkinlik Silindi!  ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });



        etedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Etkinlik Düzenle!  ", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference("Etkinlikler/"+etkin+"/Kiyafetler");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        et2Adapter = new Et2Adapter(this, list);
        recyclerView.setAdapter(et2Adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Et2Data et2Data = dataSnapshot.getValue(Et2Data.class);
                    list.add(et2Data);
                }
                et2Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        et2Adapter.setOnItemClickListener(new Et2Adapter.onItemClickListener() {
            @Override
            public void onSilClick(Et2Data et2Data) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EtkinlikListesi.this);

                builder.setTitle("Kıyafet Silinecek!");
                builder.setMessage("Mevcut kıyafet etkinlikten silinecek. Emin misin?");
                builder.setCancelable(true);
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child(et2Data.getId()).removeValue();
                        Toast.makeText(getApplicationContext(), et2Data.getId(), Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(EtkinlikListesi.this, EtkinlikListesi.class);
                        intent.putExtra("etkin", etkin);
                        finish();
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Kıyafet Silindi!  ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });


    }
}