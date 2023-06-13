package com.example.RumahMakan_RAF;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private DbMenu myDatabase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> namaMenuList;
    private ArrayList<String> kategoriList;
    private ArrayList<String> kodeList;
    private ArrayList<Double> hargaList;
    private ArrayList<String> fotoList;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recycler);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        namaMenuList = new ArrayList<>();
        kategoriList = new ArrayList<>();
        kodeList = new ArrayList<>();
        hargaList = new ArrayList<>();
        fotoList = new ArrayList<>();
        myDatabase = new DbMenu(getActivity().getBaseContext());

        getData();
        setupRecyclerView();
        return view;
    }

    private void refreshData() {
        namaMenuList.clear();
        kategoriList.clear();
        kodeList.clear();
        hargaList.clear();
        fotoList.clear();
        getData();
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(ListFragment.this, namaMenuList, kategoriList, kodeList, hargaList, fotoList);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

    @SuppressLint("Recycle")
    protected void getData() {
        SQLiteDatabase readData = myDatabase.getReadableDatabase();
        Cursor cursor = readData.rawQuery("SELECT * FROM " + DbMenu.MyColumns.NamaTabel, null);

        if (cursor.moveToFirst()) {
            do {
                String kode = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.KodeMenu));
                String namaMenu = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.NamaMenu));
                String kategori = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Kategori));
                double harga = cursor.getDouble(cursor.getColumnIndex(DbMenu.MyColumns.Harga));
                String foto = cursor.getString(cursor.getColumnIndex(DbMenu.MyColumns.Foto));

                kodeList.add(kode);
                namaMenuList.add(namaMenu);
                kategoriList.add(kategori);
                hargaList.add(harga);
                fotoList.add(foto);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
