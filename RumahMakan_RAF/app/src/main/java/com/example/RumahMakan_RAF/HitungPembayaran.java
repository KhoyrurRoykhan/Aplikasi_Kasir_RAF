package com.example.RumahMakan_RAF;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HitungPembayaran extends AppCompatActivity {

    private Context context;

    private DbMenu MyDatabase;
    private RecyclerView recyclerView;
    private RecyclerViewPembayaranAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    // Menggunakan Layout Manager, dan Membuat List Secara Vertical
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HitungPembayaran.this);
    private ArrayList<String> JudulList;
    private ArrayList<String> PenerbitList;
    private ArrayList<String> KodeList;
    private ArrayList<Double> HargaList;
    private ArrayList<String> FotoList;
    private ArrayList<String> namaItem = new ArrayList<>();
    private ArrayList<Double> hargaItem = new ArrayList<>();
    private ArrayList<Integer> jumlahItem = new ArrayList<>();
    private double total;
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_pembayaran);

        TextView btnBayar = findViewById(R.id.btn_bayar);
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HitungPembayaran.this, Struk.class);
                intent.putStringArrayListExtra("namaItem", namaItem);
                intent.putExtra("hargaItem", hargaItem);
                intent.putExtra("jumlahItem", jumlahItem);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });


        swipeRefreshLayout = findViewById(R.id.swipeRefreshPembayaran);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JudulList = new ArrayList<>();
                PenerbitList = new ArrayList<>();
                KodeList = new ArrayList<>();
                HargaList = new ArrayList<>();
                FotoList = new ArrayList<>();
                namaItem = new ArrayList<>();
                hargaItem = new ArrayList<>();
                jumlahItem = new ArrayList<>();
//                total = 0.0;

                tvTotal = findViewById(R.id.tv_total);
                adapter = new RecyclerViewPembayaranAdapter(HitungPembayaran.this, JudulList, PenerbitList, KodeList, HargaList, FotoList, total, tvTotal, namaItem, hargaItem, jumlahItem);
                MyDatabase = new DbMenu(getApplicationContext());
                recyclerView = findViewById(R.id.recycler);
                getData();
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
                recyclerView.addItemDecoration(itemDecoration);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        JudulList = new ArrayList<>();
        PenerbitList = new ArrayList<>();
        KodeList = new ArrayList<>();
        HargaList = new ArrayList<>();
        FotoList = new ArrayList<>();
        namaItem = new ArrayList<>();
        hargaItem = new ArrayList<>();
        jumlahItem = new ArrayList<>();
//        total = 0.0;

        tvTotal = findViewById(R.id.tv_total);
        adapter = new RecyclerViewPembayaranAdapter(HitungPembayaran.this, JudulList, PenerbitList, KodeList, HargaList, FotoList, total, tvTotal, namaItem, hargaItem, jumlahItem);
        MyDatabase = new DbMenu(getApplicationContext());
        recyclerView = findViewById(R.id.rv_produk);
        getData();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);

        TextView textViewTotal = findViewById(R.id.tv_total);

        // Mengatur nilai total pada TextView
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        textViewTotal.setText(formatRupiah.format(total));
        System.out.println("dihitungpemnayaran"+ total);


    }


    // Berisi Statement-Statement Untuk Mengambil Data dari Database
    @SuppressLint("Recycle")
    protected void getData() {
        // Mengambil Repository dengan Mode Membaca
        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM " + DbMenu.MyColumns.NamaTabel, null);

        cursor.moveToFirst(); // Memulai Cursor pada Posisi Awal

        // Melooping Sesuai dengan Jumlah Data (Count) pada cursor
        for (int count = 0; count < cursor.getCount(); count++) {
            cursor.moveToPosition(count); // Berpindah Posisi dari no index 0 hingga no index terakhir

            // Mengambil data sesuai kolom array
            KodeList.add(cursor.getString(0));
            JudulList.add(cursor.getString(1));
            PenerbitList.add(cursor.getString(4));
            HargaList.add(cursor.getDouble(6));
            FotoList.add(cursor.getString(7));

            // Menghitung total berdasarkan penjumlahan jumlah dan harga
//            total += cursor.getInt(2) * cursor.getDouble(6);
        }
    }
}
