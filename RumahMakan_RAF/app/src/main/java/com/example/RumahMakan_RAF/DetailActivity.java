package com.example.RumahMakan_RAF;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    private DbMenu MyDatabase;
    private TextView ShowKode, ShowNamaMenu, ShowStok, ShowRating, ShowKategori, ShowDeskripsi, ShowHarga;
    private ImageView ShowImage, Back;
    private String Id ;
    private Button Update;
    private String KodeSend = "KODE";
    private String sendVal = "id";
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MyDatabase = new DbMenu(getBaseContext());

        Update =    findViewById(R.id.update);
        Back = findViewById(R.id.back);
        ShowKode = findViewById(R.id.KodeText);
        ShowImage = findViewById(R.id.imageDetail);
        ShowNamaMenu = findViewById(R.id.NamaMenuDetail);
        ShowStok = findViewById(R.id.BoxStok);
        ShowRating = findViewById(R.id.BoxRating);
        ShowKategori = findViewById(R.id.KategoriDetail);
        ShowDeskripsi = findViewById(R.id.DeskripsiText);
        ShowHarga = findViewById(R.id.HargaDetail);

        getData();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Kode = ShowKode.getText().toString();
                if (Kode != null && Kode != ""){
                    Intent i = new Intent(DetailActivity.this, UpdateActivity.class);
                    i.putExtra(KodeSend, Kode);
                    startActivity(i);

                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Kode = ShowKode.getText().toString();
                Intent intent = new Intent(DetailActivity.this,MainActivity.class);
                intent.putExtra(sendVal,Kode);
                startActivity(intent);
            }
        });


    }
    private void getData(){

        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();
        Intent intent = getIntent();
        String Id = intent.getStringExtra("SendKode");
        Cursor cursor = ReadData.rawQuery("SELECT * FROM " + DbMenu.MyColumns.NamaTabel + " WHERE " + DbMenu.MyColumns.KodeMenu + "=" + Id, null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal
        if(cursor.moveToFirst()){
            String Kode = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.KodeMenu));
            String NamaMenu = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.NamaMenu));
            String Stok = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Stok));
            String Rating = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Rating));
            String Kategori = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Kategori));
            String Deskripsi = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Deskripsi));
            Double Harga = cursor.getDouble(cursor.getColumnIndex(DbMenu.MyColumns.Harga));
            String Foto = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Foto));

            ShowKode.setText(Kode);
            ShowNamaMenu.setText(NamaMenu);
            ShowStok.setText(Stok);
            ShowRating.setText(Rating);
            ShowKategori.setText(Kategori);
            ShowDeskripsi.setText(Deskripsi);
            ShowHarga.setText(formatRupiah.format((double)Harga));
            ShowImage.setImageURI(Uri.parse(Foto));

        }
    }
}