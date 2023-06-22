package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    EditText etNamaMobil, etHargaMobil, etJumlahPenumpang, etModel;
    Button btnAddMobil, btnUploadGambar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        etNamaMobil = findViewById(R.id.etNamaMobil);
        etHargaMobil = findViewById(R.id.etHargaMobil);
        etJumlahPenumpang = findViewById(R.id.etJumlahPenumpang);
        etModel = findViewById(R.id.etModel);
        btnUploadGambar = findViewById(R.id.btUploadGambar);
        btnAddMobil = findViewById(R.id.btnAddMobil);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference = firebaseDatabase.getReference();

        btnUploadGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnAddMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNamaMobil.getText().toString();
                String harga = etHargaMobil.getText().toString();
                String penumpang = etJumlahPenumpang.getText().toString();
                String model = etModel.getText().toString();
                Mobil baru = new Mobil(nama, harga, penumpang, model);

                databaseReference.child("destination").child(mAuth.getUid()).push().setValue(baru).addOnSuccessListener(AddPage.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddPage.this, "Mobil telah ditambahkan", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddPage.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(AddPage.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPage.this, "Gagal menambahkan", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Ambil Gambar", "Pilih dari Galeri", "Batal"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPage.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Ambil Gambar")){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            } else if (items[item].equals("Pilih dari Galeri")) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 20);
            } else if (items[item].equals("Batal")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}