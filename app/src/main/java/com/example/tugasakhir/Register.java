package com.example.tugasakhir;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText et_email;
    EditText et_pass;
    EditText et_nama, et_alamat, et_noHP, et_ttl;
    TextView btn_masuk;
    Button btn_daftar;
    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_email = this.findViewById(R.id.et_email);
        et_pass = this.findViewById(R.id.et_pass);
        et_nama = this.findViewById(R.id.et_nama);
        et_alamat = this.findViewById(R.id.et_alamat);
        et_noHP = this.findViewById(R.id.et_noHP);
        et_ttl = this.findViewById(R.id.et_ttl);
        btn_masuk = this.findViewById(R.id.btn_masuk);
        btn_daftar = this.findViewById(R.id.btn_daftar);
        mAuth = FirebaseAuth.getInstance();

        // Deklarasi database
        database = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference("users");

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String password = et_pass.getText().toString();
                signUp(email,password);

//                String name = et_nama.getText().toString();
//                String email = et_email.getText().toString();
//                String pass = et_pass.getText().toString();
//                String alamat = et_alamat.getText().toString();
//                String noHP = et_noHP.getText().toString();
//                String ttl = et_ttl.getText().toString();
////                String avatar = null;
//
//                ModelUser user = new ModelUser(name, pass, email, ttl, noHP, alamat);
//                reference.child(name).setValue(user);
//
//                Toast.makeText(Register.this, "Berhasil melakukan register", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Register.this, Login.class);
//                startActivity(intent);
            }
        });
    }

    private void signUp(String email, String password) {
        if (!validateForm()){
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userId = user.getUid();
                    String nama = et_nama.getText().toString();
                    String email = et_email.getText().toString();
                    String alamat = et_alamat.getText().toString();
                    String noHP = et_noHP.getText().toString();
                    String ttl = et_ttl.getText().toString();
                    String pass = et_pass.getText().toString();

                    // menyimpan data ke realtime database
                    ModelUser modelUser = new ModelUser(nama, pass, email, ttl, noHP, alamat, "/profile.png");
                    reference.child(userId).setValue(modelUser);

                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                    Toast.makeText(Register.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(Register.this, "Register Gagal" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean hasil = true;
        if (TextUtils.isEmpty(et_nama.getText().toString())) {
            et_nama.setError("Tidak boleh kosong");
            hasil = false;
        } else {
            et_nama.setError(null);
        }
        if (TextUtils.isEmpty(et_email.getText().toString())) {
            et_email.setError("Tidak boleh kosong");
            hasil = false;
        } else {
            et_email.setError(null);
        }
        if (TextUtils.isEmpty(et_alamat.getText().toString())) {
            et_alamat.setError("Tidak boleh kosong");
            hasil = false;
        } else {
            et_alamat.setError(null);
        }
        if (TextUtils.isEmpty(et_ttl.getText().toString())) {
            et_ttl.setError("Tidak boleh kosong");
            hasil = false;
        } else {
            et_ttl.setError(null);
        }
        if (TextUtils.isEmpty(et_pass.getText().toString())) {
            et_pass.setError("Tidak boleh kosong");
            hasil = false;
        } else {
            et_pass.setError(null);
        }
        if (TextUtils.isEmpty(et_noHP.getText().toString())) {
            et_noHP.setError("Tidak boleh kosong");
            hasil = false;
        } else {
            et_noHP.setError(null);
        }
        return hasil;
    }
}
