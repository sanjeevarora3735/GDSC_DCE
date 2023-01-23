package com.sanjeev.gdscdce.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.sanjeev.gdscdce.R;
import com.sanjeev.gdscdce.UpcomingEventAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private ViewPager UpcomingEventsViewPager;
    private final ArrayList<Integer> UpcomingEventsPosters = new ArrayList<>();
    private UpcomingEventAdapter upcomingEventAdapter;
    private ConstraintLayout PastEventConstraintLayout;
    private CircleImageView UserProfileImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //Setup ProfileImageView
        UserProfileImage = view.findViewById(R.id.UserProfileImage);
        try {
            Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(UserProfileImage);
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        // Static Version
        UpcomingEventsViewPager = view.findViewById(R.id.UpComingEventsViewPager);
        PastEventConstraintLayout = view.findViewById(R.id.PastEvent_1);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        UpcomingEventsPosters.add(R.drawable.samplethumbnail);
        upcomingEventAdapter = new UpcomingEventAdapter(getContext(), UpcomingEventsPosters);
        UpcomingEventsViewPager.setAdapter(upcomingEventAdapter);
        SetupAutomaticViewPagerSlider();


        return view;
    }

    private void SetupAutomaticViewPagerSlider() {

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UpcomingEventsViewPager.getCurrentItem() < upcomingEventAdapter.getCount() - 1) {
                    UpcomingEventsViewPager.setCurrentItem(UpcomingEventsViewPager.getCurrentItem() + 1, true);
                } else {
                    UpcomingEventsViewPager.setCurrentItem(0, true);
                }
                SetupAutomaticViewPagerSlider();
            }
        }, 10000);

    }
}