package com.sanjeev.gdscdce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sanjeev.gdscdce.EventOverview;
import com.sanjeev.gdscdce.R;
import com.sanjeev.gdscdce.UpcomingEventAdapter;
import com.sanjeev.gdscdce.ViewPageAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ViewPager UpcomingEventsViewPager;
    ArrayList<Integer> UpcomingEventsPosters = new ArrayList<>();
    UpcomingEventAdapter upcomingEventAdapter;

    ConstraintLayout PastEventConstraintLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        UpcomingEventsViewPager = view.findViewById(R.id.UpComingEventsViewPager);
        PastEventConstraintLayout = view.findViewById(R.id.PastEvent_1);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        upcomingEventAdapter = new UpcomingEventAdapter(getContext(), UpcomingEventsPosters);
        UpcomingEventsViewPager.setAdapter(upcomingEventAdapter);

        int ChildCount = PastEventConstraintLayout.getChildCount();

        for(int i=0;i<ChildCount;i++){
            PastEventConstraintLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), EventOverview.class));
                }
            });
        }



        return view;
    }
}