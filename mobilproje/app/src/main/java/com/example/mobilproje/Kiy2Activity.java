package com.example.mobilproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.UUID;

public class Kiy2Activity extends AppCompatActivity {

    String ckm;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference myRef;
    DatabaseReference myRef2;
    DatabaseReference imgRef;
    RecyclerView recyclerView;
    ArrayList<KiyafetData> list;
    KiyafetAdapter2 kiyafetAdapter;
    SP sp;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiy2);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            ckm = bundle.getString("cekmecen");
        }

        myRef = database.getReference("Cekmeceler");
        sp=new SP();
        recyclerView = findViewById(R.id.recKiyafet2);
        imgRef = FirebaseDatabase.getInstance().getReference("Cekmeceler/"+ckm+"/Kiyafetler");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        kiyafetAdapter = new KiyafetAdapter2(this, list);
        recyclerView.setAdapter(kiyafetAdapter);

        imgRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    KiyafetData kiyafetData = dataSnapshot.getValue(KiyafetData.class);
                    list.add(kiyafetData);

                }
                kiyafetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        int kontrol = sp.getIntValue(context, "kontrol");
        if (kontrol ==1) {
            kiyafetAdapter.setOnItemClickListener(new KiyafetAdapter2.onItemClickListener() {
                @Override
                public void onItemClick(KiyafetData kiyafetData) {
                    String aranan = sp.getStringValue(context, "aranan");
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("images").child(kiyafetData.getId());
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            sp.saveString(context, aranan, uri.toString());
                            Toast.makeText(getApplicationContext(), "Eklendi!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Kiy2Activity.this, KabinActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });
                }
            });
        }
        if (kontrol==2){
            kiyafetAdapter.setOnItemClickListener(new KiyafetAdapter2.onItemClickListener() {
                @Override
                public void onItemClick(KiyafetData kiyafetData) {
                    String aranan2 = sp.getStringValue(context, "aranan2");
                    myRef2 = database.getReference("Etkinlikler/"+aranan2+"/Kiyafetler");
                    final String randomKey = UUID.randomUUID().toString();

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("images").child(kiyafetData.getId());
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myRef2.child(randomKey).child("url").setValue(uri.toString());
                            myRef2.child(randomKey).child("id").setValue(randomKey);
                            Toast.makeText(getApplicationContext(), "Eklendi!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Kiy2Activity.this, EtkinlikListesi.class);
                            intent.putExtra("etkin", aranan2);
                            finish();
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }

}