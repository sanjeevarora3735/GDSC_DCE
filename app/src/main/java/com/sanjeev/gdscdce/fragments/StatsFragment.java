package com.sanjeev.gdscdce.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sanjeev.gdscdce.Adapter.ProjectShowcase_ViewPageAdapter;
import com.sanjeev.gdscdce.R;
import com.sanjeev.gdscdce.UpcomingEventAdapter;

public class StatsFragment extends Fragment {

    private View view;
    private ViewPager ProjectShowCaseViewPager;
    private ProjectShowcase_ViewPageAdapter projectShowcase_viewPageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stats, container, false);

        ProjectShowCaseViewPager = view.findViewById(R.id.ProjectShowcaseCard);

        projectShowcase_viewPageAdapter = new ProjectShowcase_ViewPageAdapter(getContext());
        ProjectShowCaseViewPager.setAdapter(projectShowcase_viewPageAdapter);

        return view;
    }
}