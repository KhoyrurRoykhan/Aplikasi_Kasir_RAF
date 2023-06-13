package com.example.RumahMakan_RAF;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private TextView hitungPembayaran;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        hitungPembayaran = view.findViewById(R.id.HitungPembayaran);
        hitungPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity currentActivity = getActivity();
                Intent intent = new Intent(currentActivity, HitungPembayaran.class);
                currentActivity.startActivity(intent);
            }
        });

        TextView logoutTextView = view.findViewById(R.id.Logout);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        getActivity().finish();
    }
}
