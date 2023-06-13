package com.example.RumahMakan_RAF;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Struk extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewStrukAdapter recyclerViewStrukAdapter;

    private ArrayList<String> namaItem;
    private ArrayList<Double> hargaItem;
    private ArrayList<Integer> jumlahItem;
    private TextView textView;

    private double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struk);

        recyclerView = findViewById(R.id.recycler_view_struk);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        namaItem = intent.getStringArrayListExtra("namaItem");
        hargaItem = (ArrayList<Double>) intent.getSerializableExtra("hargaItem");
        jumlahItem = intent.getIntegerArrayListExtra("jumlahItem");

        // Menghitung total
        total = 0;
        for (int i = 0; i < hargaItem.size(); i++) {
            double subtotal = hargaItem.get(i) * jumlahItem.get(i);
            total += subtotal;
        }

        System.out.println(namaItem);
        System.out.println(hargaItem);
        System.out.println(jumlahItem);
        System.out.println(total);

        textView = findViewById(R.id.tv_total_price);
        textView.setText(String.valueOf(total));

        recyclerViewStrukAdapter = new RecyclerViewStrukAdapter(namaItem, hargaItem, jumlahItem);
        recyclerView.setAdapter(recyclerViewStrukAdapter);
    }
}
