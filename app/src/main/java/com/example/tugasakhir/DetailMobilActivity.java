package com.example.tugasakhir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailMobilActivity extends AppCompatActivity {
    private Button btMobil;
    private TextView tvNamaMobil, tvHarga, tvModel, tvJumlahPenumpang;

    Mobil mobil;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mobil);

        btMobil = findViewById(R.id.btMobil);
        tvNamaMobil = findViewById(R.id.tvNamaMobil);
        tvHarga = findViewById(R.id.tvHarga);
        tvModel = findViewById(R.id.tvModel);
        tvJumlahPenumpang = findViewById(R.id.tvJumlahPenumpang);

        mobil = getIntent().getParcelableExtra("EXTRA_MOBIL");

        tvNamaMobil.setText(mobil.getNamaMobil());
        tvHarga.setText(mobil.getHargaSewa());
        tvModel.setText(mobil.getModel());
        tvJumlahPenumpang.setText(mobil.getjumlahPenumpang());

        btMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailMobilActivity.this, BookingActivity.class);
                intent.putExtra("EXTRA_MOBIL", mobil);
                startActivity(intent);

            }
        });
    }
}
