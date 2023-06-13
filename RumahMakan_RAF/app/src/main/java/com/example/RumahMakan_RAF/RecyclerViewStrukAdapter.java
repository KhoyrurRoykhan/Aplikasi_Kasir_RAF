package com.example.RumahMakan_RAF;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewStrukAdapter extends RecyclerView.Adapter<RecyclerViewStrukAdapter.ItemViewHolder> {

    private ArrayList<String> namaItem;
    private ArrayList<Double> hargaItem;
    private ArrayList<Integer> jumlahItem;

    public RecyclerViewStrukAdapter(ArrayList<String> namaItem, ArrayList<Double> hargaItem, ArrayList<Integer> jumlahItem) {
        this.namaItem = namaItem;
        this.hargaItem = hargaItem;
        this.jumlahItem = jumlahItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recycler_struk, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.namaItemTextView.setText(namaItem.get(position));
        holder.hargaItemTextView.setText(String.valueOf(hargaItem.get(position)));
        holder.jumlahItemTextView.setText(String.valueOf(jumlahItem.get(position)));
    }

    @Override
    public int getItemCount() {
        return namaItem.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView namaItemTextView, hargaItemTextView, jumlahItemTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            namaItemTextView = itemView.findViewById(R.id.namaItemTextView);
            hargaItemTextView = itemView.findViewById(R.id.hargaItemTextView);
            jumlahItemTextView = itemView.findViewById(R.id.jumlahItemTextView);
        }
    }
}
