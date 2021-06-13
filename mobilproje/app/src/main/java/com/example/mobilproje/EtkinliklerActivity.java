package com.example.mobilproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EtkinliklerActivity extends AppCompatActivity {

    Button etkinlikEkle;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    EtkinlikAdapter etkinlikAdapter;
    ArrayList<EtkinlikData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlikler);

        etkinlikEkle = findViewById(R.id.etkinlikEkle);

        recyclerView = findViewById(R.id.recEtkinlik);
        databaseReference = FirebaseDatabase.getInstance().getReference("Etkinlikler");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        etkinlikAdapter = new EtkinlikAdapter(this, list);
        recyclerView.setAdapter(etkinlikAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    EtkinlikData etkinlikData = dataSnapshot.getValue(EtkinlikData.class);
                    list.add(etkinlikData);
                }
                etkinlikAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        etkinlikAdapter.setOnItemClickListener(new EtkinlikAdapter.onItemClickListener() {
            @Override
            public void onEtkClick(EtkinlikData etkinlikData) {
                //Toast.makeText(getApplicationContext(), etkinlikData.getId(), Toast.LENGTH_LONG).show();
                Intent intent=new Intent(EtkinliklerActivity.this, EtkinlikListesi.class);
                intent.putExtra("etkin", etkinlikData.getId());
                startActivity(intent);
            }
        });


        etkinlikEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EtkinliklerActivity.this, yeniEtkinlikActivity.class);
                startActivity(intent);
            }
        });






    }
}