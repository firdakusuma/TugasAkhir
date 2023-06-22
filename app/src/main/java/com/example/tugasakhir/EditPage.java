package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    EditText etNamaMobil, etHargaMobil, etJumlahPenumpang, etModel;
    Button btnEditMobil, btnUploadGambar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        etNamaMobil = findViewById(R.id.etNamaMobil);
        etHargaMobil = findViewById(R.id.etHargaMobil);
        etJumlahPenumpang = findViewById(R.id.etJumlahPenumpang);
        etModel = findViewById(R.id.etModel);
        btnUploadGambar = findViewById(R.id.btUploadGambar);
        btnEditMobil = findViewById(R.id.btnEditMobil);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference = firebaseDatabase.getReference();

        Mobil mobil = getIntent().getParcelableExtra("EXTRA DESTINATION");

        etNamaMobil.setText(mobil.getNamaMobil());
        etHargaMobil.setText(mobil.getHargaSewa());
        etJumlahPenumpang.setText(mobil.getModel());

        btnEditMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNamaMobil.getText().toString();
                String harga = etHargaMobil.getText().toString();
                String penumpang = etJumlahPenumpang.getText().toString();
                String model = etModel.getText().toString();

                databaseReference.child("mobil").child(mAuth.getUid()).child(mobil.getKey()).setValue(new Mobil(nama, harga, penumpang, model)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditPage.this, "Berhasil Update Mobil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditPage.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditPage.this, "Update Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}