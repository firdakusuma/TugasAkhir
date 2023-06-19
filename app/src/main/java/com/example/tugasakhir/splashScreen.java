package com.example.tugasakhir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity implements View.OnClickListener {

    private Button btnDaftar, btnMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        btnDaftar = findViewById(R.id.btnDaftar);
        btnMasuk = findViewById(R.id.btnLogin);

        btnDaftar.setOnClickListener(this);
        btnMasuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDaftar) {
            Intent intent = new Intent(splashScreen.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(splashScreen.this, Login.class);
            startActivity(intent);
        }
    }
}
