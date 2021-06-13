package com.example.mobilproje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CekmeceListesi extends AppCompatActivity {


    TextView cekmecenin;
    Button kiyafetAdd, cekmeceDelete;
    String ckm;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference myRef;
    DatabaseReference imgRef;
    RecyclerView recyclerView;
    ArrayList<KiyafetData> list;
    KiyafetAdapter kiyafetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cekmece_listesi);

        cekmeceDelete=findViewById(R.id.cekmeceDel);
        cekmecenin=findViewById(R.id.cekmecenin);
        kiyafetAdd=findViewById(R.id.kiyafetAdd);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            ckm = bundle.getString("cekmecen");
        }

        cekmecenin.setText(ckm);

        myRef = database.getReference("Cekmeceler");

        kiyafetAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CekmeceListesi.this, yeniKiyafetActivity.class);
                intent.putExtra("cekmecen", ckm);
                finish();
                startActivity(intent);
            }
        });

        cekmeceDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CekmeceListesi.this);

                builder.setTitle("Cekmece Silinecek!");
                builder.setMessage("Mevcut cekmece içindeki kıyafetlerle birlikte silinecek. Emin misin?");
                builder.setCancelable(true);
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myRef.child(ckm).removeValue();
                        Intent intent=new Intent(CekmeceListesi.this, CekmecelerActivity.class);
                        finish();
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Çekmece Silindi!  ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        recyclerView = findViewById(R.id.recKiyafet);
        imgRef = FirebaseDatabase.getInstance().getReference("Cekmeceler/"+ckm+"/Kiyafetler");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        kiyafetAdapter = new KiyafetAdapter(this, list);
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


        kiyafetAdapter.setOnItemClickListener(new KiyafetAdapter.onItemClickListener() {
            @Override
            public void onDelClick(KiyafetData kiyafetData) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CekmeceListesi.this);

                builder.setTitle("Kiyafet Silinecek!");
                builder.setMessage("Mevcut kiyafet kalıcı olarak silinecek. Emin misin?");
                builder.setCancelable(true);
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imgRef.child(kiyafetData.getId()).removeValue();
                        Intent intent = new Intent(CekmeceListesi.this, CekmeceListesi.class);
                        finish();
                        intent.putExtra("cekmecen", ckm);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), "Kıyafet Silindi!  ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


            }

            @Override
            public void onEditClick(KiyafetData kiyafetData) {
                imgRef.child(kiyafetData.getId()).removeValue();
                Intent intent=new Intent(CekmeceListesi.this, yeniKiyafetActivity.class);
                intent.putExtra("cekmecen", ckm);
                finish();
                startActivity(intent);
            }
        });

    }
}