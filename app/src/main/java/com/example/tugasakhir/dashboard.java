package com.example.tugasakhir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MobilAdapter mobilAdapter = new MobilAdapter(generateMobilList());
    TextView tvName;
    ImageView imgPhoto;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        imgPhoto = findViewById(R.id.imgPhoto);
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromLogin();
                passData();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Mobil> mobilList = generateMobilList();
        mobilAdapter = new MobilAdapter(mobilList);
        recyclerView.setAdapter(mobilAdapter);

        mobilAdapter.setOnItemClickListener((position, view) ->  {
            Intent intent = new Intent(dashboard.this, DetailMobilActivity.class);
            startActivity(intent);
        });

    }

    private void passData() {
        String namaUser = tvName.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        Query checkDatabase = reference.orderByChild("nama").equalTo(namaUser);
        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String dbNama = snapshot.child(namaUser).child("nama").getValue(String.class);
                    String dbEmail = snapshot.child(namaUser).child("email").getValue(String.class);
                    String dbAlamat = snapshot.child(namaUser).child("alamat").getValue(String.class);
                    String dbNoHP = snapshot.child(namaUser).child("noHP").getValue(String.class);
                    String dbTTL = snapshot.child(namaUser).child("ttl").getValue(String.class);
                    String dbPass = snapshot.child(namaUser).child("pass").getValue(String.class);
                    String dbAva = snapshot.child(namaUser).child("avatar").getValue(String.class);

                    Intent intent = new Intent(dashboard.this, Profile.class);
                    intent.putExtra("source", "Login");
                    intent.putExtra("nama", dbNama);
                    intent.putExtra("pass", dbPass);
                    intent.putExtra("email", dbEmail);
                    intent.putExtra("alamat", dbAlamat);
                    intent.putExtra("noHP", dbNoHP);
                    intent.putExtra("TTL", dbTTL);
                    intent.putExtra("avatar", dbAva);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataFromLogin() {
        Intent intent = getIntent();

        String source = intent.getStringExtra("source");
        String namaUser = intent.getStringExtra("nama");
        String passUser = intent.getStringExtra("pass");
        String emailUser = intent.getStringExtra("email");
        String alamatUser = intent.getStringExtra("alamat");
        String noHPUser = intent.getStringExtra("noHP");
        String TTLUser = intent.getStringExtra("TTL");
        String ava = intent.getStringExtra("avatar");

        tvName.setText(namaUser);
    }

    private List<Mobil> generateMobilList() {
        List<Mobil> mobilList = new ArrayList<>();
        mobilList.add(new Mobil("Honda Civic", "Automatic", "Rp.200.000/hari"));
        mobilList.add(new Mobil("Honda Civic", "Manual", "Rp.150.000/hari"));
        mobilList.add(new Mobil("Honda Civic", "Automativ", "Rp.350.000/hari"));
        mobilList.add(new Mobil("Honda Civic", "Manual", "Rp.200.000/hari"));
        mobilList.add(new Mobil("Honda Civic", "Manual", "Rp.200.000/hari"));
        // Tambahkan data mobil lainnya sesuai kebutuhan
        return mobilList;
    }
}