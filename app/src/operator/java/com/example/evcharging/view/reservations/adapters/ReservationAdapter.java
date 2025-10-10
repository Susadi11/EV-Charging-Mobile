package com.example.evcharging.view.reservations.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evcharging.R;
import com.example.evcharging.model.Reservation;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private static final String TAG = "ReservationAdapter";

    private List<Reservation> reservations;

    public ReservationAdapter(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        Log.d(TAG, "setReservations: received " + reservations.size() + " reservations");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);
        Log.d(TAG, "onBindViewHolder: binding reservation at position " + position + ", Booking ID: " + reservation.getBookingId());

        holder.textReservationId.setText("#" + reservation.getBookingId().substring(0, 8));
        holder.textStatus.setText(reservation.getStatus());
        holder.textCustomerName.setText(reservation.getUserId()); // Replace with actual customer name if available
        holder.textStationName.setText(reservation.getStationId()); // Replace with actual station name if available
        holder.textDate.setText(reservation.getReservationDateTime()); // Format this date
        holder.textTime.setText(reservation.getStartTime() + " - " + reservation.getEndTime()); // Format this time

        // Hide all action buttons by default
        holder.btnApprove.setVisibility(View.GONE);
        holder.btnReject.setVisibility(View.GONE);
        holder.btnFinish.setVisibility(View.GONE);
        holder.btnViewDetails.setVisibility(View.GONE);
        holder.btnEndOperation.setVisibility(View.GONE);

        // Show buttons based on status
        if ("Approved".equals(reservation.getStatus())) {
            // No buttons for approved status in pending tab
        } else if ("Active".equals(reservation.getStatus())) {
            holder.btnFinish.setVisibility(View.VISIBLE);
            holder.btnEndOperation.setVisibility(View.VISIBLE);
        } else if ("Completed".equals(reservation.getStatus())) {
            holder.btnViewDetails.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textReservationId;
        TextView textStatus;
        TextView textCustomerName;
        TextView textStationName;
        TextView textDate;
        TextView textTime;
        View btnApprove;
        View btnReject;
        View btnFinish;
        View btnViewDetails;
        View btnEndOperation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textReservationId = itemView.findViewById(R.id.textReservationId);
            textStatus = itemView.findViewById(R.id.textStatus);
            textCustomerName = itemView.findViewById(R.id.textCustomerName);
            textStationName = itemView.findViewById(R.id.textStationName);
            textDate = itemView.findViewById(R.id.textDate);
            textTime = itemView.findViewById(R.id.textTime);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);
            btnFinish = itemView.findViewById(R.id.btnFinish);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btnEndOperation = itemView.findViewById(R.id.btnEndOperation);
        }
    }
}