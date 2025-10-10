package com.example.evcharging.view.reservations.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evcharging.R;
import com.example.evcharging.view.reservations.adapters.ReservationAdapter;
import com.example.evcharging.viewmodel.ReservationViewModel;

import java.util.ArrayList;

public class ReservationListFragment extends Fragment {

    private static final String TAG = "ReservationListFragment";

    private ReservationViewModel reservationViewModel;
    private RecyclerView recyclerView;
    private ReservationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view = inflater.inflate(R.layout.fragment_pending_reservations, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPending);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReservationAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: called");

        reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);
        reservationViewModel.getAssignedReservations().observe(getViewLifecycleOwner(), reservations -> {
            if (reservations != null) {
                Log.d(TAG, "Assigned Reservations LiveData changed: " + reservations.size() + " reservations");
                adapter.setReservations(reservations);
            }
        });

        reservationViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Log.e(TAG, "Error Message LiveData changed: " + errorMessage);
                Toast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        reservationViewModel.fetchAssignedReservations("Approved");
    }
}