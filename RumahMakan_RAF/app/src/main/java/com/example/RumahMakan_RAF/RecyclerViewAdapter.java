package com.example.RumahMakan_RAF;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<String> NamaMenuList;
    private ArrayList<String> KategoriList;
    private ArrayList<String> kodeList;
    private ArrayList<Double> hargaList;
    private ArrayList<String> fotoList;
    private Context context;
    RecyclerView mRecyclerView;

    RecyclerViewAdapter(ListFragment listFragment, ArrayList<String> NamaMenuList, ArrayList<String> KategoriList, ArrayList<String> kodeList, ArrayList<Double> hargaList, ArrayList<String> fotoList) {
        this.NamaMenuList = NamaMenuList;
        this.KategoriList = KategoriList;
        this.kodeList = kodeList;
        this.hargaList = hargaList;
        this.fotoList = fotoList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Detail, NamaMenu, Kategotri, Kode, Harga;
        private CircularImageView Foto;
        private ImageButton Delete;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            mRecyclerView = itemView.findViewById(R.id.recycler);
            NamaMenu = itemView.findViewById(R.id.NamaMenu);
            Kategotri = itemView.findViewById(R.id.Kategori);
            Kode = itemView.findViewById(R.id.KodeMenu);
            Harga = itemView.findViewById(R.id.Harga);
            Detail = itemView.findViewById(R.id.Detail);
            Foto = itemView.findViewById(R.id.image);
            Delete = itemView.findViewById(R.id.delete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recycler, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        final String NamaMenu = NamaMenuList.get(position);
        final String Kategori = KategoriList.get(position);
        final String Kode = kodeList.get(position);
        final double Harga = hargaList.get(position);
        final String Foto = fotoList.get(position);

        holder.NamaMenu.setText(NamaMenu);
        holder.Kategotri.setText(Kategori);
        holder.Kode.setText(Kode);
        holder.Harga.setText(formatRupiah.format(Harga));
        holder.Foto.setImageURI(Uri.parse(Foto));

        // Mengatur aksi klik untuk tombol hapus
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Hapus Menu");
                builder.setMessage("Apakah Anda yakin ingin menghapus menu ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Panggil metode hapusData() untuk menghapus data dari database
                        hapusData(position);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //onklik see detail
        holder.Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent dataForm = new Intent(view.getContext(), DetailActivity.class);
                dataForm.putExtra("SendKode", holder.Kode.getText().toString());
                context.startActivity(dataForm);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return NamaMenuList.size();
    }

    private void hapusData(int position) {
        // Ambil kode menu yang akan dihapus berdasarkan posisi dalam list
        String kodeMenu = kodeList.get(position);

        // Hapus data dari database
        DbMenu myDatabase = new DbMenu(context);
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.delete(DbMenu.MyColumns.NamaTabel, DbMenu.MyColumns.KodeMenu + " = ?", new String[]{kodeMenu});
        db.close();

        // Hapus data dari list
        NamaMenuList.remove(position);
        KategoriList.remove(position);
        kodeList.remove(position);
        hargaList.remove(position);
        fotoList.remove(position);

        // Refresh tampilan RecyclerView
        notifyDataSetChanged();

        Toast.makeText(context, "Menu berhasil dihapus", Toast.LENGTH_SHORT).show();
    }
}
