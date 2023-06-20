package com.example.tugasakhir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText et_email;
    private EditText et_pass;
    private EditText et_nama, et_alamat, et_noHP, et_ttl;
    private TextView btn_masuk;
    private Button btn_daftar;
    FirebaseDatabase database;
    DatabaseReference reference;

    //    private FirebaseAuth mAuth;
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
//        mAuth = FirebaseAuth.getInstance();

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
                database = FirebaseDatabase.getInstance("https://finalproject-carrent-default-rtdb.asia-southeast1.firebasedatabase.app/");
                reference = database.getReference("users");

                String name = et_nama.getText().toString();
                String email = et_email.getText().toString();
                String pass = et_pass.getText().toString();
                String alamat = et_alamat.getText().toString();
                String noHP = et_noHP.getText().toString();
                String ttl = et_ttl.getText().toString();
//                String avatar = null;

                ModelUser user = new ModelUser(name, pass, email, ttl, noHP, alamat);
                reference.child(name).setValue(user);

                Toast.makeText(Register.this, "Berhasil melakukan register", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
