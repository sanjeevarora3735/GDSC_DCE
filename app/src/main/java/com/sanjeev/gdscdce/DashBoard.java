package com.sanjeev.gdscdce;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.sanjeev.gdscdce.fragments.ExploreFragment;
import com.sanjeev.gdscdce.fragments.HomeFragment;
import com.sanjeev.gdscdce.fragments.ProfileFragment;
import com.sanjeev.gdscdce.fragments.StatsFragment;

@SuppressWarnings("all")

public class DashBoard extends AppCompatActivity {

    private int SelectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        final Fragment HomeFragment = new HomeFragment();
        final Fragment ExploreFragment = new ExploreFragment();
        final Fragment StatsFragment = new StatsFragment();
        final Fragment ProfileFragment = new ProfileFragment();
        Fragment ActiveFragment = HomeFragment;

        // FindViewByID @ LinearLayouts :: 1 To 4
        final LinearLayout HomeLayout = findViewById(R.id.HomeLayout);
        final LinearLayout ExploreLayout = findViewById(R.id.ExploreLayout);
        final LinearLayout StatsLayout = findViewById(R.id.StatsLayout);
        final LinearLayout ProfileLayout = findViewById(R.id.ProfileLayout);

        // FindViewByID @ LinearLayouts_Images :: 1 To 4
        final ImageView HomeLayoutImage = findViewById(R.id.HomeLayoutImage);
        final ImageView ExploreLayoutImage = findViewById(R.id.ExploreLayoutImage);
        final ImageView StatsLayoutImage = findViewById(R.id.StatsLayoutImage);
        final ImageView ProfileLayoutImage = findViewById(R.id.ProfileLayoutImage);

        // FindViewByID @ LinearLayouts_TextView :: 1 To 4
        final TextView HomeLayoutTextView = findViewById(R.id.HomeLayoutTextView);
        final TextView ExploreLayoutTextView = findViewById(R.id.ExploreLayoutTextView);
        final TextView StatsLayoutTextView = findViewById(R.id.StatsLayoutTextView);
        final TextView ProfileLayoutTextView = findViewById(R.id.ProfileLayoutTextView);


        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.FragmentTabHost, HomeFragment.class, null)
                .addToBackStack("HomeLayout")
                .commit();

        HomeLayout.setOnClickListener(v -> {

            if (SelectedTab != 1) {

                getSupportFragmentManager().beginTransaction()
                        .addToBackStack("HomeLayout")
                        .replace(R.id.FragmentTabHost, HomeFragment.class, null)
                        .commit();
                ExploreLayoutTextView.setVisibility(View.GONE);
                ProfileLayoutTextView.setVisibility(View.GONE);
                StatsLayoutTextView.setVisibility(View.GONE);

                StatsLayoutImage.setImageResource(R.drawable.ic_baseline_statistics_24);
                ProfileLayoutImage.setImageResource(R.drawable.ic_round_person_4_24);
                ExploreLayoutImage.setImageResource(R.drawable.ic_baseline_folder_24);

                ExploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                StatsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                HomeLayoutTextView.setVisibility(View.VISIBLE);
                HomeLayoutImage.setImageResource(R.drawable.ic_round_home_selected);
                HomeLayout.setBackgroundResource(R.drawable.round_home_back_100);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                HomeLayout.setAnimation(scaleAnimation);
                SelectedTab = 1;
            }
        });

        ExploreLayout.setOnClickListener(v -> {

            if (SelectedTab != 2) {


                getSupportFragmentManager().beginTransaction()
                        .addToBackStack("ExploreLayout")
                        .replace(R.id.FragmentTabHost, ExploreFragment.class, null)
                        .commit();

                HomeLayoutTextView.setVisibility(View.GONE);
                ProfileLayoutTextView.setVisibility(View.GONE);
                StatsLayoutTextView.setVisibility(View.GONE);

                StatsLayoutImage.setImageResource(R.drawable.ic_baseline_statistics_24);
                ProfileLayoutImage.setImageResource(R.drawable.ic_round_person_4_24);
                HomeLayoutImage.setImageResource(R.drawable.ic_round_home_selected);

                HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                StatsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                ExploreLayoutTextView.setVisibility(View.VISIBLE);
                ExploreLayoutImage.setImageResource(R.drawable.ic_baseline_folder_selected);
                ExploreLayout.setBackgroundResource(R.drawable.round_explore_back_100);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                ExploreLayout.setAnimation(scaleAnimation);
                SelectedTab = 2;
            }
        });

        StatsLayout.setOnClickListener(v -> {

            if (SelectedTab != 3) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FragmentTabHost, StatsFragment.class, null)
                        .addToBackStack("StatsLayout")
                        .commit();

                HomeLayoutTextView.setVisibility(View.GONE);
                ProfileLayoutTextView.setVisibility(View.GONE);
                ExploreLayoutTextView.setVisibility(View.GONE);

                ExploreLayoutImage.setImageResource(R.drawable.ic_baseline_folder_24);
                ProfileLayoutImage.setImageResource(R.drawable.ic_round_person_4_24);
                HomeLayoutImage.setImageResource(R.drawable.ic_round_home_selected);

                HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ExploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                StatsLayoutTextView.setVisibility(View.VISIBLE);
                StatsLayoutImage.setImageResource(R.drawable.ic_baseline_statistics_selected);
                StatsLayout.setBackgroundResource(R.drawable.round_stats_back_100);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                StatsLayout.setAnimation(scaleAnimation);
                SelectedTab = 3;
            }
        });

        ProfileLayout.setOnClickListener(v -> {

            if (SelectedTab != 4) {


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FragmentTabHost, ProfileFragment.class, null)
                        .addToBackStack("ProfileLayout")
                        .commit();

                HomeLayoutTextView.setVisibility(View.GONE);
                StatsLayoutTextView.setVisibility(View.GONE);
                ExploreLayoutTextView.setVisibility(View.GONE);

                ExploreLayoutImage.setImageResource(R.drawable.ic_baseline_folder_24);
                StatsLayoutImage.setImageResource(R.drawable.ic_baseline_statistics_24);
                HomeLayoutImage.setImageResource(R.drawable.ic_round_home_24);

                HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ExploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                StatsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                ProfileLayoutTextView.setVisibility(View.VISIBLE);
                ProfileLayoutImage.setImageResource(R.drawable.ic_round_person_4_selected);
                ProfileLayout.setBackgroundResource(R.drawable.round_profile_back_100);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                ProfileLayout.setAnimation(scaleAnimation);
                SelectedTab = 4;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (SelectedTab != 1) {
            findViewById(R.id.HomeLayout).performClick();
        }
    }
}