package com.example.RumahMakan_RAF;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UpdateActivity extends AppCompatActivity {
    private DbMenu MyDatabase;
    private EditText NewKodeMenu, NewNamaMenu, NewStok, NewRating, NewKategori, NewDeskripsi, NewHarga;
    private CircularImageView NewImage;
    private String getNewKode;
    private Button Update, Open;
    private String KodeSend = "KODE";
    private ImageView Back;
    Uri resultUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        MyDatabase = new DbMenu(getBaseContext());

        NewKodeMenu = findViewById(R.id.NewformKodeMenu);
        NewNamaMenu = findViewById(R.id.NewformNamaMenu);
        NewStok = findViewById(R.id.NewformStok);
        NewRating = findViewById(R.id.NewformRating);
        NewKategori = findViewById(R.id.NewformKategori);
        NewHarga = findViewById(R.id.NewformHarga);
        NewDeskripsi = findViewById(R.id.NewformDeskripsi);
        NewImage = findViewById(R.id.Newimage_profile);

        Back = findViewById(R.id.back);

        Bundle extras = getIntent().getExtras();
        getNewKode = extras.getString(KodeSend);
        Update = findViewById(R.id.btnUpdate);
        Open = findViewById(R.id.NewbtnOpen);

        SQLiteDatabase ReadDb = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadDb.rawQuery("SELECT * FROM " + DbMenu.MyColumns.NamaTabel + " WHERE " + DbMenu.MyColumns.KodeMenu + "=" + getNewKode, null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal
        if (cursor.moveToFirst()) {
            String Kode = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.KodeMenu));
            String NamaMenu = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.NamaMenu));
            String Stok = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Stok));
            String Rating = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Rating));
            String Kategori = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Kategori));
            String Deskripsi = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Deskripsi));
            String Harga = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Harga));
            String Foto = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Foto));


            NewKodeMenu.setText(Kode);
            NewNamaMenu.setText(NamaMenu);
            NewStok.setText(Stok);
            NewRating.setText(Rating);
            NewKategori.setText(Kategori);
            NewDeskripsi.setText(Deskripsi);
            NewHarga.setText(Harga);
            NewImage.setImageURI(Uri.parse(Foto));

            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setUpdateData();
                    startActivity(new Intent(UpdateActivity.this, MainActivity.class));

                }
            });
            Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                finish();
                }
            });
            Open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CropImage.startPickImageActivity(UpdateActivity.this);
                }
            });
        }
    }

    private void setUpdateData() {
        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();

            String getKode = NewKodeMenu.getText().toString().trim();
            String getNamaMenu = NewNamaMenu.getText().toString().trim();
            String getStok = NewStok.getText().toString().trim();
            String getRating = NewRating.getText().toString().trim();
            String getKategori = NewKategori.getText().toString().trim();
            String getDeskripsi = NewDeskripsi.getText().toString().trim();
            String getHarga = NewHarga.getText().toString().trim();


            //Memasukan Data baru pada
            ContentValues values = new ContentValues();
            values.put(DbMenu.MyColumns.KodeMenu, getKode);
            values.put(DbMenu.MyColumns.NamaMenu, getNamaMenu);
            values.put(DbMenu.MyColumns.Stok, getStok);
            values.put(DbMenu.MyColumns.Rating, getRating);
            values.put(DbMenu.MyColumns.Kategori, getKategori);
            values.put(DbMenu.MyColumns.Deskripsi, getDeskripsi);
            values.put(DbMenu.MyColumns.Harga, getHarga);
            values.put(DbMenu.MyColumns.Foto, String.valueOf(resultUri));

            //Untuk Menentukan Data/Item yang ingin diubah, berdasarkan NIM
            String selection = DbMenu.MyColumns.KodeMenu + " LIKE ?";
            String[] selectionArgs = {getNewKode};
            ReadData.update(DbMenu.MyColumns.NamaTabel, values, selection, selectionArgs);
            Toast.makeText(getApplicationContext(), "Berhasil Diubah", Toast.LENGTH_SHORT).show();
        }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                resultUri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                        , 0);
            } else {
                startCrop(imageuri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                NewImage.setImageURI(result.getUri());
                resultUri = result.getUri();
            }
        }
    }

    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3, 4)
                .start(this);
        resultUri = imageuri;
    }
}