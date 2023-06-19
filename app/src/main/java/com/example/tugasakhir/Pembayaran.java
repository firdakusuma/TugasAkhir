package com.example.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pembayaran extends AppCompatActivity implements View.OnClickListener{

    private Button backButton;
    private Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        button1 = findViewById(R.id.btn_lacak);
        backButton = findViewById(R.id.button4);

        button1.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    /* backButton.setOnClickListener(new View.OnClickListener()*/
    @Override
    public void onClick(View v) {
              /*  // Kembali ke MainActivity
                Intent intent = new Intent(pembayaran.this, MainActivity.class);
                Intent intent2 = new Intent(pembayaran.this, MapsActivity.class);
                startActivity(intent);
                startActivity(intent2);
                finish();
            }

        });*/
        if (v == button1) {
            Intent intent = new Intent(Pembayaran.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(Pembayaran.this, MapsActivity.class);
            startActivity(intent);
        }
    }
}