package com.example.evcharging.view.reservations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.viewpager2.widget.ViewPager2;

import com.example.evcharging.R;
import com.example.evcharging.view.base.OperatorBaseActivity;
import com.example.evcharging.view.reservations.adapters.ReservationsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ReservationsActivity extends OperatorBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Load the reservations content into the content container
        loadReservationsContent();
    }
    
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_base;
    }
    
    @Override
    protected void setSelectedNavigationItem() {
        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.nav_reservations);
        }
    }
    
    private void loadReservationsContent() {
        // Inflate the reservations layout into the content container
        FrameLayout contentContainer = findViewById(R.id.content_container);
        LayoutInflater.from(this).inflate(R.layout.activity_reservations, contentContainer, true);

        // Set up ViewPager2 and TabLayout
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ReservationsPagerAdapter adapter = new ReservationsPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Pending");
                    break;
                case 1:
                    tab.setText("Active");
                    break;
                case 2:
                    tab.setText("@string/completed");
                    break;
            }
        }).attach();
    }
}
