package com.example.RumahMakan_RAF;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewPembayaranAdapter extends RecyclerView.Adapter<RecyclerViewPembayaranAdapter.ViewHolder> {

    private ArrayList<String> judulList;
    private ArrayList<String> penerbitList;
    private ArrayList<String> kodeList;
    private ArrayList<Double> hargaList;
    private ArrayList<String> fotoList;
    private  ArrayList<String> namaItem;
    private ArrayList<Double> hargaItem;
    private ArrayList<Integer> jumlahItem;
    private Context context;
    private double total = 0.0;

    private TextView tvTotal;
    private ArrayList<ViewHolder> holderList = new ArrayList<>();

    public double getTotal() {
        return total;
    }

    RecyclerViewPembayaranAdapter(Context context, ArrayList<String> judulList, ArrayList<String> penerbitList, ArrayList<String> kodeList, ArrayList<Double> hargaList, ArrayList<String> fotoList, double total, TextView tvTotal, ArrayList<String> namaItem, ArrayList<Double> hargaItem, ArrayList<Integer> jumlahItem) {
        this.context = context;
        this.judulList = judulList;
        this.penerbitList = penerbitList;
        this.kodeList = kodeList;
        this.hargaList = hargaList;
        this.fotoList = fotoList;
        this.total = total;
        this.tvTotal = tvTotal;
        this.namaItem = namaItem;
        this.hargaItem = hargaItem;
        this.jumlahItem = jumlahItem;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvJudul, tvJumlah, tvHarga;
        private ImageView ivFoto;
        private CheckBox checkBox;
        private ImageView btnKurang, btnTambah;

        ViewHolder(View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tv_nama);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            ivFoto = itemView.findViewById(R.id.img_produk);
            tvJumlah = itemView.findViewById(R.id.tv_jumlah);
            btnKurang = itemView.findViewById(R.id.btn_kurang);
            btnTambah = itemView.findViewById(R.id.btn_tambah);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recycler_pembayaran, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        final String judul = judulList.get(position);
        final Double harga = hargaList.get(position);
        final String foto = fotoList.get(position);

        holder.tvJudul.setText(judul);
        holder.tvHarga.setText(formatRupiah.format(harga));
        holder.ivFoto.setImageURI(Uri.parse(foto));

        final TextView tvJumlah = holder.tvJumlah;
        tvJumlah.setText(tvJumlah.getText().toString());

        holder.btnKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jumlah = Integer.parseInt(tvJumlah.getText().toString());
                if (jumlah > 0) {
                    jumlah--;
                    tvJumlah.setText(String.valueOf(jumlah));
                    calculateTotal();
                }
            }
        });

        holder.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jumlah = Integer.parseInt(tvJumlah.getText().toString());
                jumlah++;
                tvJumlah.setText(String.valueOf(jumlah));
                calculateTotal();
            }
        });

        // Tambahkan ViewHolder saat melakukan binding
        holderList.add(holder);
    }


    private void calculateTotal() {
        double newTotal = 0.0;

        for (int i = 0; i < holderList.size(); i++) {
            if (i < hargaList.size()) { // Pengecekan ukuran ArrayList
                TextView tvJumlah = holderList.get(i).tvJumlah;
                int jumlah = Integer.parseInt(tvJumlah.getText().toString());
                double hargaitem = hargaList.get(i);
                newTotal += jumlah * hargaitem;
            }
        }

        total = newTotal;
        notifyDataSetChanged();
        tvTotal.setText(String.valueOf(total));
        saveDataWithQuantityGreaterThanZero();
        System.out.println(namaItem);
        System.out.println(hargaItem);
        System.out.println(jumlahItem);
        System.out.println(total);

    }

    private void saveDataWithQuantityGreaterThanZero() {
        namaItem.clear();
        hargaItem.clear();
        jumlahItem.clear();

        for (int i = 0; i < holderList.size(); i++) {
            if (i < judulList.size() && i < hargaList.size() && i < holderList.size()) {
                ViewHolder holder = holderList.get(i);
                int jumlah = Integer.parseInt(holder.tvJumlah.getText().toString());

                if (jumlah > 0) {
                    namaItem.add(judulList.get(i));
                    hargaItem.add(hargaList.get(i));
                    jumlahItem.add(jumlah);
                }
            }
        }
    }




    @Override
    public int getItemCount() {
        return kodeList.size();
    }
}
