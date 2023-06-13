package com.example.RumahMakan_RAF;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.NumberFormat;
import java.util.Locale;


public class TambahFragment extends Fragment {

    private EditText KodeMenu, NamaMenu, Stok, Rating, Kategori, Deskripsi, Harga;
    private String setKodeMenu, setNamaMenu, setStok, setRating, setKategori, setDeskripsi, setHarga;
    private DbMenu dbMenu;
    private Button Open;
    CircularImageView imageView;
    Uri resultUri;

    public TambahFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah, container, false);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        Button simpan       = view.findViewById(R.id.btnSubmit);
        Open                = view.findViewById(R.id.btnOpen);
        imageView           = (CircularImageView)view.findViewById(R.id.image_profile);
        KodeMenu            = view.findViewById(R.id.formKode);
        NamaMenu            = view.findViewById(R.id.formNamaMenu);
        Stok                = view.findViewById(R.id.formStok);
        Rating              = view.findViewById(R.id.formRating);
        Harga               = view.findViewById(R.id.formHarga);
        Deskripsi           = view.findViewById(R.id.formDeskripsi);
        Kategori            = view.findViewById(R.id.formKategori);

        //Inisialisasi dan Mendapatkan Konteks dari DbMenu
        dbMenu = new DbMenu(getActivity().getBaseContext());
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                if (setKodeMenu.equals("") || setNamaMenu.equals("") || setStok.equals("") || setRating.equals("") || setKategori.equals("") || setDeskripsi.equals("") || setHarga.equals("")){
                    Toast.makeText(getActivity().getApplicationContext(),"Data Menu Belum Lengkap atau Belum diisi, Lengkapi Dahulu!", Toast.LENGTH_SHORT).show();
                }else {
                    setData();
                    saveData();
                    Toast.makeText(getActivity().getApplicationContext(),"Data Menu Tersimpan", Toast.LENGTH_SHORT).show();
                    clearData();
                }
                }
        });
        //intent eksternal untuk masuk kedalam folder atau galeri
        Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CropImage.activity().setAspectRatio(3,4).getIntent(getContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        return view;
    }

    //Berisi Statement-Statement Untuk Mendapatkan Input Dari User
    private void setData(){
        setKodeMenu = KodeMenu.getText().toString();
        setNamaMenu = NamaMenu.getText().toString();
        setStok = Stok.getText().toString();
        setRating = Rating.getText().toString();
        setKategori = Kategori.getText().toString();
        setDeskripsi = Deskripsi.getText().toString();
        setHarga = Harga.getText().toString();
    }

    //Berisi Statement-Statement Untuk Menyimpan Data Pada Database
    private void saveData() {
        //Mendapatkan Repository dengan Mode Menulis
        SQLiteDatabase create = dbMenu.getWritableDatabase();

        //Membuat Map Baru, Yang Berisi Judul Kolom dan Data Yang Ingin Dimasukan
        ContentValues values = new ContentValues();
        values.put(DbMenu.MyColumns.KodeMenu, setKodeMenu);
        values.put(DbMenu.MyColumns.NamaMenu, setNamaMenu);
        values.put(DbMenu.MyColumns.Stok, setStok);
        values.put(DbMenu.MyColumns.Rating, setRating);
        values.put(DbMenu.MyColumns.Kategori, setKategori);
        values.put(DbMenu.MyColumns.Deskripsi, setDeskripsi);
        values.put(DbMenu.MyColumns.Harga, setHarga);
        values.put(DbMenu.MyColumns.Foto,String.valueOf(resultUri));

        create.insert(DbMenu.MyColumns.NamaTabel, null, values);
        }

    private void clearData(){
        KodeMenu.setText("");
        NamaMenu.setText("");
        Stok.setText("");
        Rating.setText("");
        Kategori.setText("");
        Deskripsi.setText("");
        Harga.setText("");
        imageView.setImageResource(R.drawable.ic_picimg);
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode ==  Activity.RESULT_OK) {
                resultUri = result.getUri();
                Log.e("resultUri ->", String.valueOf(resultUri));
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("error ->", String.valueOf(error));
            }
        }
    }



}
