package com.example.tugasakhir;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class editprofile extends AppCompatActivity {

    TextView etNamaProfile, tvEditProfileNama, tvEditProfileEmail;
    EditText etEmailProfile, etNomorHPProfile, etAlamatProfile, etTTLProfile;
    Button btnUpdate;
    ImageView btnEditBack, edtTTL;
    CircleImageView avaEditProfile;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    String namaUser;
    String emailUser, alamatUser, noHPUser, TTLUser;

//    ImageView avaEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // memperbarui profile
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");

        etNamaProfile = findViewById(R.id.etNamaProfile);
        etEmailProfile = findViewById(R.id.etEmailProfile);
        etNomorHPProfile = findViewById(R.id.etNomorHPProfile);
        etAlamatProfile = findViewById(R.id.etAlamatProfile);
        etTTLProfile = findViewById(R.id.etTTLProfile);
        tvEditProfileNama = findViewById(R.id.tvEditProfileNama);
        tvEditProfileEmail = findViewById(R.id.tvEditProfileEmail);
        btnUpdate = findViewById(R.id.btnUpdate);

        showData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
//                updateProfile();
                upload();
                Intent intent = new Intent(editprofile.this, Profile.class);
                startActivity(intent);
//                passDataProfile();
            }
        });

        // kembali ke halaman sebelumnya (profile)
        btnEditBack = findViewById(R.id.btnEditBack);
        btnEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editprofile.this.finish();
            }
        });

        // memilih tanggal lahir
        etTTLProfile = findViewById(R.id.etTTLProfile);
        edtTTL = findViewById(R.id.edtTTL);
        edtTTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(); // membuka dialog untuk memilih tanggal
            }
        });

        // mengubah foto profil
        avaEditProfile = findViewById(R.id.avaEditProfile);
        avaEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }

    // metode untuk upload gambar
    private void selectImage(){
        final CharSequence[] items = {"Ambil Gambar", "Pilih dari Galeri", "Batal"};
        AlertDialog.Builder builder = new AlertDialog.Builder(editprofile.this);
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

    // metode untuk menerima gambar yang diunggah
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // menerima data dari galeri
        if (requestCode == 20 && resultCode == RESULT_OK && data != null){
            final Uri path = data.getData();
            Thread thread = new Thread(()->{
                try {
                    InputStream inputStream = getContentResolver().openInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    avaEditProfile.post(()->{
                        avaEditProfile.setImageBitmap(bitmap);
                    });
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        // menerima data dari kamera
        if (requestCode == 10 && resultCode == RESULT_OK){
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(()->{
                Bitmap bitmap = (Bitmap) extras.get("data");
                avaEditProfile.post(()->{
                    avaEditProfile.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
    }

    // metode untuk mengupload gambar ke storage
    private String upload(){
        avaEditProfile.setDrawingCacheEnabled(true);
        avaEditProfile.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) avaEditProfile.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // upload
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("images").child("IMG"+new Date().getTime()+".jpeg");
        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata() != null){
                    if (taskSnapshot.getMetadata().getReference() != null){
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.getResult()!= null){
                                    String imageUrl = task.getResult().toString(); // Simpan URL gambar
                                    updateProfile(imageUrl); // Panggil metode updateProfile setelah mendapatkan URL gambar
                                } else {
                                    Toast.makeText(editprofile.this, "Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(editprofile.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(editprofile.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return null;
    }

    // metode untuk memilih waktu
    private void datePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // menampilkan tanggal yang dipilih
                etTTLProfile.setText(String.valueOf(day)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
            }
        }, 2023, 01, 20);

        datePickerDialog.show();
    }
//    private void passDataProfile() {
//        String namaUser = etNamaProfile.getText().toString().trim();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
//        Query checkDatabase = reference.orderByChild("nama").equalTo(namaUser);
//        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    String dbNama = snapshot.child(namaUser).child("nama").getValue(String.class);
//                    String dbEmail = snapshot.child(namaUser).child("email").getValue(String.class);
//                    String dbAlamat = snapshot.child(namaUser).child("alamat").getValue(String.class);
//                    String dbNoHP = snapshot.child(namaUser).child("noHP").getValue(String.class);
//                    String dbTTL = snapshot.child(namaUser).child("ttl").getValue(String.class);
//                    String dbPass = snapshot.child(namaUser).child("pass").getValue(String.class);
//                    String dbAva = snapshot.child(namaUser).child("avatar").getValue(String.class);
//
//                    Intent intent = new Intent(editprofile.this, Profile.class);
//                    intent.putExtra("source", "editProfile");
//                    intent.putExtra("nama", dbNama);
//                    intent.putExtra("pass", dbPass);
//                    intent.putExtra("email", dbEmail);
//                    intent.putExtra("alamat", dbAlamat);
//                    intent.putExtra("noHP", dbNoHP);
//                    intent.putExtra("TTL", dbTTL);
//                    intent.putExtra("avatar", dbAva);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


    // update data profile di database
    private void updateProfile(String imageUrl) {
        String userID = mAuth.getCurrentUser().getUid();
//        namaUser = getIntent().getStringExtra("nama");

        String nama = etNamaProfile.getText().toString().trim();
        String email = etEmailProfile.getText().toString().trim();
        String nomorHP = etNomorHPProfile.getText().toString().trim();
        String alamat = etAlamatProfile.getText().toString().trim();
        String ttl = etTTLProfile.getText().toString().trim();
//        String ava = upload();

        // update data pada database
        reference.child(userID).child("email").setValue(email);
        reference.child(userID).child("noHP").setValue(nomorHP);
        reference.child(userID).child("alamat").setValue(alamat);
        reference.child(userID).child("ttl").setValue(ttl);
        reference.child(userID).child("avatar").setValue(imageUrl);

        Toast.makeText(this, "Profile berhasil diperbarui", Toast.LENGTH_SHORT).show();
    }

    private void showData() {
        Intent intent = getIntent();

        String nama = intent.getStringExtra("nama");
        String pass = intent.getStringExtra("pass");
        String email = intent.getStringExtra("email");
        String alamat = intent.getStringExtra("alamat");
        String noHP = intent.getStringExtra("noHP");
        String TTL = intent.getStringExtra("ttl");
        String ava = intent.getStringExtra("avatar");

        tvEditProfileNama.setText(nama);
        etNamaProfile.setText(nama);
        tvEditProfileEmail.setText(email);
        etEmailProfile.setText(email);
        etAlamatProfile.setText(alamat);
        etNomorHPProfile.setText(noHP);
        etTTLProfile.setText(TTL);

//        //         Memuat foto profil menggunakan Glide
//        Glide.with(this)
//                .load(ava)
//                .apply(RequestOptions.circleCropTransform())
//                .into(avaEditProfile);
    }

}
