package com.sanjeev.gdscdce.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanjeev.gdscdce.R;
import com.sanjeev.gdscdce.UpcomingEventAdapter;
import com.sanjeev.gdscdce.ViewPageAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ViewPager UpcomingEventsViewPager;
    ArrayList<Integer> UpcomingEventsPosters = new ArrayList<>();
    UpcomingEventAdapter upcomingEventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        UpcomingEventsViewPager = view.findViewById(R.id.UpComingEventsViewPager);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);

        upcomingEventAdapter = new UpcomingEventAdapter(getContext(), UpcomingEventsPosters);

        UpcomingEventsViewPager.setAdapter(upcomingEventAdapter);



        return view;
    }
}