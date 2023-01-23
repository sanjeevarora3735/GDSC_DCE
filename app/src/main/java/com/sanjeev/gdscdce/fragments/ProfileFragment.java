package com.sanjeev.gdscdce.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.sanjeev.gdscdce.R;

public class ProfileFragment extends Fragment {


    private View view = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_profile, container, false);

        // initiating the tabhost
        TabHost tabhost = view.findViewById(R.id.FragmentTabHost);

        // setting up the tab host
        tabhost.setup();

        // Code for adding Tab 1 to the tabhost
        TabHost.TabSpec spec = tabhost.newTabSpec("Tab One");
        spec.setContent(R.id.Events);

        // setting the name of the tab 1 as "Tab One"
        spec.setIndicator("Events");

        // adding the tab to tabhost
        tabhost.addTab(spec);


        // Code for adding Tab 2 to the tabhost
        spec = tabhost.newTabSpec("Tab Two");
        spec.setContent(R.id.Couses);

        // setting the name of the tab 1 as "Tab Two"
        spec.setIndicator("Courses");
        tabhost.addTab(spec);

        // Code for adding Tab 3 to the tabhost
        spec = tabhost.newTabSpec("Tab Three");
        spec.setContent(R.id.Projects);

        spec.setIndicator("Projects", getResources().getDrawable(R.drawable.ic_outline_electric_bolt_24));
        tabhost.addTab(spec);

         return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getContext(), "Refreshed Profile Fragment", Toast.LENGTH_SHORT).show();
    }
}