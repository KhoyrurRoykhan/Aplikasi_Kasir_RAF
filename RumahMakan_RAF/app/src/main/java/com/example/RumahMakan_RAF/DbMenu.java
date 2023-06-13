package com.example.RumahMakan_RAF;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbMenu extends SQLiteOpenHelper {
    private static SQLiteDatabase db;

    static abstract class MyColumns implements BaseColumns {
        static final String NamaTabel = "DataMenu";
        static final String KodeMenu = "KodeMenu";
        static final String NamaMenu = "NamaMenu";
        static final String Stok = "Stok";
        static final String Rating = "Rating";
        static final String Kategori = "Kategori";
        static final String Deskripsi = "Deskripsi";
        static final String Harga = "HargaSatuan";
        static final String Foto = "GambarPic";
    }

    private static final String NamaDatabase = "dbmymenu.db";
    private static final int VersiDatabase = 1;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MyColumns.NamaTabel +
            "(" + MyColumns.KodeMenu + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + MyColumns.NamaMenu + " TEXT NOT NULL, "
            + MyColumns.Stok + " TEXT NOT NULL, "
            + MyColumns.Rating + " TEXT NOT NULL, "
            + MyColumns.Kategori + " TEXT NOT NULL, "
            + MyColumns.Deskripsi + " TEXT NOT NULL, "
            + MyColumns.Harga + " TEXT NOT NULL, "
            + MyColumns.Foto + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MyColumns.NamaTabel;

    DbMenu(Context context) {
        super(context, NamaDatabase, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
