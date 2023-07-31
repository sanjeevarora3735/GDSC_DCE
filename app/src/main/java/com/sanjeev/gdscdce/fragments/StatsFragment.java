package com.sanjeev.gdscdce.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionButton;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sanjeev.gdscdce.Adapter.ProjectShowcase_ViewPageAdapter;
import com.sanjeev.gdscdce.Adapter.RecyclerViewAdapter_AllProjects;
import com.sanjeev.gdscdce.Model.AllProjects;
import com.sanjeev.gdscdce.Model.Registration_Model;
import com.sanjeev.gdscdce.R;
import com.sanjeev.gdscdce.UploadProject;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatsFragment extends Fragment {
    public final static String PROJECT_LIST = "ProjectList";
    public final static String SHARED_PREFERENCES = "SharedPreference";
    private View view;
    private String UserType = null;
    private ViewPager ProjectShowCaseViewPager;
    private ProjectShowcase_ViewPageAdapter projectShowcase_viewPageAdapter;
    private FloatingActionButton GithubFloatingActionButton, ResourcesFloatingActionButton;
    private RecyclerView ProjectSectionRecyclerView;
    private RecyclerViewAdapter_AllProjects recyclerViewAdapter_allProjects;
    private ArrayList<AllProjects> AllProjectsArrayList;
    private CircleImageView UserCircleImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stats, container, false);

        ProjectShowCaseViewPager = view.findViewById(R.id.ProjectShowcaseCard);
        GithubFloatingActionButton = view.findViewById(R.id.UploadProject);
        ResourcesFloatingActionButton = view.findViewById(R.id.Resources);
        UserCircleImageView = view.findViewById(R.id.UserProfileImage);

        UserType = FetchUserBasicInformation().getInviteCode();
        Log.d(TAG,"UserType "+ UserType);

//        if(UserType.contains("CTMember")){
//            ResourcesFloatingActionButton.setVisibility(View.VISIBLE);
//            ResourcesFloatingActionButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getContext(), CustomEvent.class));
//                }
//            });
//        }

        //Setting up the UserImage
        try {
            Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(UserCircleImageView);
        } catch (Exception e) {
//            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Initialization of RecyclerView
        ProjectSectionRecyclerView = view.findViewById(R.id.ProjectSectionRecyclerView);
        ProjectSectionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProjectSectionRecyclerView.setHasFixedSize(true);
        AllProjectsArrayList = new ArrayList<>();


        TryToLoadDataFromSharedPreferences();
//        GetAllProjectsFromFirebase();


        GithubFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UploadProject.class));
            }
        });

        SetupAutomaticViewPagerSlider();

        return view;
    }

    private Registration_Model FetchUserBasicInformation() {
        SharedPreferences sharedPref = getContext().getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE);
        String CollegeMail = sharedPref.getString("CMail", null);
        String ContactNumber = sharedPref.getString("ContactNumber", null);
        String InviteCode = sharedPref.getString("InviteCode", null);

        Registration_Model BasicInformation = new Registration_Model(CollegeMail, ContactNumber, InviteCode,"","","");
        return BasicInformation;
    }



    private void SetupAutomaticViewPagerSlider() {

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ProjectShowCaseViewPager.getCurrentItem() < projectShowcase_viewPageAdapter.getCount() - 1) {
                    ProjectShowCaseViewPager.setCurrentItem(ProjectShowCaseViewPager.getCurrentItem() + 1, true);
                } else {
                    ProjectShowCaseViewPager.setCurrentItem(0, true);
                }
                SetupAutomaticViewPagerSlider();
            }
        }, 10000);

    }


    private boolean TryToLoadDataFromSharedPreferences() {
        AllProjectsArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String Json = sharedPreferences.getString(PROJECT_LIST, null);
        Type type = new TypeToken<ArrayList<AllProjects>>() {
        }.getType();
        AllProjectsArrayList.clear();
        AllProjectsArrayList = gson.fromJson(Json, type);
        if (AllProjectsArrayList == null || AllProjectsArrayList.size() == 0) {
            AllProjectsArrayList = new ArrayList<>();
            GetAllProjectsFromFirebase();
            return false;
        }
        Log.d(TAG, "Size..........."+AllProjectsArrayList.size());
        UpdateProjectShowcase(AllProjectsArrayList);
        recyclerViewAdapter_allProjects = new RecyclerViewAdapter_AllProjects(getContext(), AllProjectsArrayList, UserType);
        ProjectSectionRecyclerView.setAdapter(recyclerViewAdapter_allProjects);
        return true;
    }

    private void UpdateProjectShowcase(ArrayList<AllProjects> ShowcaseProjectList) {
        Log.d(TAG, "ShowcaseProjectList Array Length"+ShowcaseProjectList.size());
        SharedPreferences sharedPref = getContext().getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TotalProjects", String.valueOf(ShowcaseProjectList.size()));
        editor.apply();
        projectShowcase_viewPageAdapter = new ProjectShowcase_ViewPageAdapter(getContext(), ShowcaseProjectList);
        ProjectShowCaseViewPager.setAdapter(projectShowcase_viewPageAdapter);

    }


    private void SaveDataToSharedPrefrence() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AllProjectsArrayList);
        editor.putString(PROJECT_LIST, json);
        editor.apply();
    }


    private void GetAllProjectsFromFirebase() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference AllProjectsDatabaseReference = database.getReference("/ProjectsInformation/AllProjects/");

        // Read from the database

        AllProjectsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                AllProjectsArrayList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    AllProjects projects = postSnapshot.getValue(AllProjects.class);
                    AllProjectsArrayList.add(projects);
                }
                Log.d("BoundErrorSolving", "After Firebase Fetching :  "+String.valueOf(AllProjectsArrayList.size()));
                SaveDataToSharedPrefrence();
                UpdateProjectShowcase(AllProjectsArrayList);
                recyclerViewAdapter_allProjects = new RecyclerViewAdapter_AllProjects(getContext(), AllProjectsArrayList, UserType);
                ProjectSectionRecyclerView.setAdapter(recyclerViewAdapter_allProjects);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}