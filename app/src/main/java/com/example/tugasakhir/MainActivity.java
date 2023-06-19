package com.example.tugasakhir;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MobilAdapter mobilAdapter = new MobilAdapter(generateMobilList());


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Mobil> mobilList = generateMobilList();
        mobilAdapter = new MobilAdapter(mobilList);
        recyclerView.setAdapter(mobilAdapter);

        mobilAdapter.setOnItemClickListener((position, view) ->  {
            Intent intent = new Intent(MainActivity.this, DetailMobilActivity.class);
            startActivity(intent);
        });

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
