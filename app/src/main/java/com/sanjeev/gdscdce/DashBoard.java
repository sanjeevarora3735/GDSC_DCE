package com.sanjeev.gdscdce;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.sanjeev.gdscdce.Model.AllProjects;
import com.sanjeev.gdscdce.Model.PastEvents;
import com.sanjeev.gdscdce.Model.Registration_Model;
import com.sanjeev.gdscdce.fragments.ExploreFragment;
import com.sanjeev.gdscdce.fragments.HomeFragment;
import com.sanjeev.gdscdce.fragments.ProfileFragment;
import com.sanjeev.gdscdce.fragments.StatsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kotlin.jvm.internal.PropertyReference0Impl;

@SuppressWarnings("all")

public class DashBoard extends AppCompatActivity implements ConnectionReceiver.ReceiverListener , FragmentManager.OnBackStackChangedListener, HomeFragment.OnButtonClicklistener {

    public final static String PAST_EVENTS_LIST = "PastEventLists";
    public final static String PROJECT_LIST = "ProjectList";
    public final static String SHARED_PREFERENCES = "SharedPreference";
    private static int Created = 0;
    private int SelectedTab = 1;
    private ConstraintLayout ModalLinearLayout;
    private ConstraintLayout DashboardInternetConnection;
    private TextView InternetStatus;
    private ArrayList<AllProjects> AllProjectsArrayList = new ArrayList<>();
    private ArrayList<PastEvents> PastEventsArrayList = new ArrayList<>();
    private LinearLayout ProfileLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Created = 1;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ModalLinearLayout = findViewById(R.id.LinearLayout_Modal);
        DashboardInternetConnection = findViewById(R.id.DashboardInternetConnection);
        InternetStatus = findViewById(R.id.InternetStatus);

        ProfileLinearLayout = findViewById(R.id.ProfileLayout);




        // Save Information To SharedPreferences...
        SaveFutureUseInformationToSharedPreferences();
        SaveUserBasicInformation();

        //Activate The Modal Please Run The Following Method
//        ActivateModal();

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
                .replace(R.id.FragmentContainerView, HomeFragment.class, null)
                .addToBackStack("HomeLayout")
                .commit();

        getSupportFragmentManager().addOnBackStackChangedListener(this);


        HomeLayout.setOnClickListener(v -> {

            if (SelectedTab != 1 || true) {

                getSupportFragmentManager().beginTransaction()
                        .addToBackStack("HomeLayout")
                        .replace(R.id.FragmentContainerView, HomeFragment.class, null)
                        .commit();
                ExploreLayoutTextView.setVisibility(View.GONE);
                ProfileLayoutTextView.setVisibility(View.GONE);
                StatsLayoutTextView.setVisibility(View.GONE);

//                StatsLayoutImage.setImageResource(R.drawable.ic_baseline_statistics_24);
//                ProfileLayoutImage.setImageResource(R.drawable.ic_round_person_4_24);
//                ExploreLayoutImage.setImageResource(R.drawable.ic_baseline_folder_24);

                ExploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                StatsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                HomeLayoutTextView.setVisibility(View.VISIBLE);
//                HomeLayoutImage.setImageResource(R.drawable.ic_round_home_selected);
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
                        .replace(R.id.FragmentContainerView, ExploreFragment.class, null)
                        .commit();

                HomeLayoutTextView.setVisibility(View.GONE);
                ProfileLayoutTextView.setVisibility(View.GONE);
                StatsLayoutTextView.setVisibility(View.GONE);

//                StatsLayoutImage.setImageResource(R.drawable.ic_baseline_statistics_24);
//                ProfileLayoutImage.setImageResource(R.drawable.ic_round_person_4_24);
//                HomeLayoutImage.setImageResource(R.drawable.ic_round_home_selected);

                HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                StatsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                ExploreLayoutTextView.setVisibility(View.VISIBLE);
//                ExploreLayoutImage.setImageResource(R.drawable.ic_baseline_folder_selected);
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
                        .replace(R.id.FragmentContainerView, StatsFragment.class, null)
                        .addToBackStack("StatsLayout")
                        .commit();

                HomeLayoutTextView.setVisibility(View.GONE);
                ProfileLayoutTextView.setVisibility(View.GONE);
                ExploreLayoutTextView.setVisibility(View.GONE);

//                ExploreLayoutImage.setImageResource(R.drawable.ic_baseline_folder_24);
//                ProfileLayoutImage.setImageResource(R.drawable.ic_round_person_4_24);
//                HomeLayoutImage.setImageResource(R.drawable.ic_round_home_selected);

                HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ExploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ProfileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                StatsLayoutTextView.setVisibility(View.VISIBLE);
//                StatsLayoutImage.setImageResource(R.drawable.ic_baseline_statistics_selected);
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
                        .replace(R.id.FragmentContainerView, ProfileFragment.class, null)
                        .addToBackStack("ProfileLayout")
                        .commit();

                HomeLayoutTextView.setVisibility(View.GONE);
                StatsLayoutTextView.setVisibility(View.GONE);
                ExploreLayoutTextView.setVisibility(View.GONE);

//                ExploreLayoutImage.setImageResource(R.drawable.ic_baseline_folder_24);
//                StatsLayoutImage.setImageResource(R.drawable.ic_baseline_statistics_24);
//                HomeLayoutImage.setImageResource(R.drawable.ic_round_home_24);

                HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                ExploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                StatsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                // Select Home Tab :

                ProfileLayoutTextView.setVisibility(View.VISIBLE);
//                ProfileLayoutImage.setImageResource(R.drawable.ic_round_person_4_selected);
                ProfileLayout.setBackgroundResource(R.drawable.round_profile_back_100);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                ProfileLayout.setAnimation(scaleAnimation);
                SelectedTab = 4;
            }
        });

    }

    private void SaveDataToSharedPrefrence(String PUT_STR) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = null;
        if (PUT_STR == PAST_EVENTS_LIST) {
            json = gson.toJson(PastEventsArrayList);
        } else if (PUT_STR == PROJECT_LIST) {
            json = gson.toJson(AllProjectsArrayList);
        }
        editor.putString(PUT_STR, json);
        editor.apply();
    }

    private void SaveFutureUseInformationToSharedPreferences() {
            SavePastEvents();
            SaveAllProjects();
    }

//    private void SaveLatestTwoPastEvents(PastEvents First, PastEvents Second) {
//        ArrayList<pa>
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = null;
//        json = gson.toJson(PastEventsArrayList);
//        editor.putString("LATEST_PASTEVENTS_TWO", json);
//        editor.apply();
//    }

    private void SaveUserBasicInformation() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String Email = "sanjeevarora3735@gmail.com";
        try {
            Email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }catch (Exception e){
        }
        DocumentReference docRef = db.collection("/GDSC_DCE/Users_Information/Registration_Details/").document(Email);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Registration_Model BasicInformation = documentSnapshot.toObject(Registration_Model.class);
                SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("CMail",BasicInformation.getUsername() );
                editor.putString("ContactNumber",BasicInformation.getContact_Number() );
                editor.putString("InviteCode",BasicInformation.getInviteCode() );
                editor.apply();
            }
        });


    }

    private void SavePastEvents() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference PastEventsDatabaseReference = database.getReference("/EventsInformation/PastEvents/");
        // Read from the database

        PastEventsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    PastEvents events = postSnapshot.getValue(PastEvents.class);
                    PastEventsArrayList.add(events);

                }
                SaveDataToSharedPrefrence(PAST_EVENTS_LIST);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void SaveAllProjects() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference AllProjectsDatabaseReference = database.getReference("/ProjectsInformation/AllProjects/");

        // Read from the database

        AllProjectsArrayList.clear();

        AllProjectsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    AllProjects projects = postSnapshot.getValue(AllProjects.class);
                    AllProjectsArrayList.add(projects);
                }
                SaveDataToSharedPrefrence(PROJECT_LIST);

                SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("TotalProjects", String.valueOf(AllProjectsArrayList.size()));
                editor.apply();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ActivateModal() {
        ModalLinearLayout.setVisibility(View.VISIBLE);
        Picasso.get().load("https://marketplace.canva.com/EAFBBVEyPMs/1/0/1131w/canva-beige-modern-illustration-fundraiser-charity-event-flyer-7VL3uRMWz48.jpg").into((ImageView) findViewById(R.id.ModalImage));
    }

    @Override
    public void onBackPressed() {
        if (SelectedTab != 1) {
            findViewById(R.id.HomeLayout).performClick();
        }
    }

    private void checkConnection() {
        Toast.makeText(this, "check conn called", Toast.LENGTH_SHORT).show();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new ConnectionReceiver(), intentFilter);
        ConnectionReceiver.Listener = this;
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        if (isConnected) {
            InternetAvailable();
        } else {
            InternetNotAvailable();
        }
    }

    private void InternetNotAvailable() {
        DashboardInternetConnection.setVisibility(View.VISIBLE);
        InternetStatus.setText("Internet Not Available");
        InternetStatus.setBackgroundResource(R.color.NoInternet);
        Toast.makeText(this, String.valueOf(DashboardInternetConnection.getVisibility()), Toast.LENGTH_SHORT).show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DashboardInternetConnection.setVisibility(View.GONE);
            }
        }, 3000);
    }

    private void InternetAvailable() {

        DashboardInternetConnection.setVisibility(View.VISIBLE);
        InternetStatus.setText("Connected To Internet");
        InternetStatus.setBackgroundResource(R.color.BackOnline);
        Toast.makeText(this, String.valueOf(DashboardInternetConnection.getVisibility()), Toast.LENGTH_SHORT).show();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DashboardInternetConnection.setVisibility(View.GONE);
            }
        }, 3000);
    }


    @Override
    public void onNetworkChange(boolean isConnected) {
        checkConnection();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    @Override
    public void onBackStackChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.FragmentContainerView);

        if (currentFragment != null) {
            String fragmentName = currentFragment.getClass().getSimpleName();
            Log.d("FragmentNames", fragmentName);

        }
    }

    @Override
    public void onButtonClick() {
        ProfileLinearLayout.performClick();
    }

}