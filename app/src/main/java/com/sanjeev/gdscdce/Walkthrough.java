package com.sanjeev.gdscdce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import com.sanjeev.gdscdce.Adapter.Upcoming_ViewPageAdapter;

public class Walkthrough extends AppCompatActivity {

    // Declaring the Adapter for the Slider To Manage The Images & Everything
    Upcoming_ViewPageAdapter viewPageAdapter;
    // Declaring the instance for the ViewPager For Slider
    private ViewPager mSLideViewPager;
    // Declaring the Next Button For The Slider
    private ImageButton NextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Initialization of the Previously Declared Values...
        mSLideViewPager = findViewById(R.id.WalkthroughCard);
        viewPageAdapter = new Upcoming_ViewPageAdapter(this);
        mSLideViewPager.setAdapter(viewPageAdapter);
        NextButton = findViewById(R.id.NextButton);

        // Setting-up The OnClick Listener for The Next Button
        NextButton.setOnClickListener(v -> {

            if (getitem(0) < 2) {
                mSLideViewPager.setCurrentItem(getitem(1), true);
            } else {
                startActivity(new Intent(Walkthrough.this, GoogleSignin.class));
            }
        });
    }

    // Function To Get The Current Pager_ID
    private int getitem(int i) {
        return mSLideViewPager.getCurrentItem() + i;
    }

}