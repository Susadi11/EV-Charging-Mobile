package com.example.evcharging.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.evcharging.data.repository.ReservationRepository;
import com.example.evcharging.model.Reservation;

import java.util.List;

public class ReservationViewModel extends ViewModel {

    private static final String TAG = "ReservationViewModel";

    private ReservationRepository reservationRepository;
    private MutableLiveData<List<Reservation>> assignedReservations = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public ReservationViewModel() {
        reservationRepository = new ReservationRepository();
    }

    public LiveData<List<Reservation>> getAssignedReservations() {
        return assignedReservations;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchAssignedReservations(String status) {
        Log.d(TAG, "fetchAssignedReservations: called with status " + status);
        reservationRepository.getAssignedSessions(status, new ReservationRepository.ReservationCallback() {
            @Override
            public void onSuccess(List<Reservation> reservations) {
                Log.d(TAG, "onSuccess: fetched " + reservations.size() + " reservations");
                assignedReservations.setValue(reservations);
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, "onError: " + message);
                errorMessage.setValue(message);
            }
        });
    }
}