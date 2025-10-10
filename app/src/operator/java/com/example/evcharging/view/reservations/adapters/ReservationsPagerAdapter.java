package com.example.evcharging.view.reservations.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.evcharging.view.reservations.fragments.ActiveReservationsFragment;
import com.example.evcharging.view.reservations.fragments.CompletedReservationsFragment;
import com.example.evcharging.view.reservations.fragments.ReservationListFragment;

public class ReservationsPagerAdapter extends FragmentStateAdapter {

    public ReservationsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ReservationListFragment();
            case 1:
                return new ActiveReservationsFragment();
            case 2:
                return new CompletedReservationsFragment();
            default:
                return new ReservationListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
