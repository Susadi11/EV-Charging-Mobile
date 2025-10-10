package com.example.evcharging.data.repository;

import android.util.Log;
import com.example.evcharging.data.remote.ApiClient;
import com.example.evcharging.http.ApiResponse;
import com.example.evcharging.http.OperatorApi;
import com.example.evcharging.model.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationRepository {

    private static final String TAG = "ReservationRepository";

    private final OperatorApi operatorApi;

    public ReservationRepository() {
        operatorApi = ApiClient.getRetrofit().create(OperatorApi.class);
    }

    public interface ReservationCallback {
        void onSuccess(List<Reservation> reservations);
        void onError(String errorMessage);
    }

    public void getAssignedSessions(String status, ReservationCallback callback) {
        Log.d(TAG, "getAssignedSessions: called with status " + status);
        operatorApi.getAssignedSessions(status).enqueue(new Callback<ApiResponse<List<Reservation>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Reservation>>> call, Response<ApiResponse<List<Reservation>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Reservation>> apiResponse = response.body();
                    Log.d(TAG, "API Response: Success = " + apiResponse.isSuccess() + ", Message = " + apiResponse.getMessage() + ", Data size = " + (apiResponse.getData() != null ? apiResponse.getData().size() : 0));
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage());
                    }
                } else {
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing error body: " + e.getMessage());
                    }
                    Log.e(TAG, "onResponse: Error: " + response.code() + " " + response.message() + ", Error Body: " + errorBody);
                    callback.onError("Error: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Reservation>>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + (t.getMessage() != null ? t.getMessage() : "Unknown error"), t);
                callback.onError(t.getMessage() != null ? t.getMessage() : "Unknown error");
            }
        });
    }
}