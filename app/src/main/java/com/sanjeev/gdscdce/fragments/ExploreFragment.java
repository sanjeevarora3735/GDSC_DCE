package com.sanjeev.gdscdce.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sanjeev.gdscdce.Adapter.RecyclerViewAdapter_PastEvents;
import com.sanjeev.gdscdce.Model.PastEvents;
import com.sanjeev.gdscdce.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class ExploreFragment extends Fragment {


    public final static String PAST_EVENTS_LIST = "PastEventLists";
    public final static String SHARED_PREFERENCES = "SharedPreference";
    private View view;
    private RecyclerView PastEventsRecyclerView;
    private RecyclerViewAdapter_PastEvents PastEventRecyclerViewAdapter;
    private ArrayList<PastEvents> PastEventsArrayList;
    private LinearLayout TagsLinearLayout;
    private SwipeRefreshLayout SwipeToRefreshLayout;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_explore, container, false);

        searchView= view.findViewById(R.id.searchView);

        // Setup OnClickListener For The Tags :)
        TagsLinearLayout = view.findViewById(R.id.TagsLinearLayout);
        AddEventListenerToTags();


        // Initialization of RecyclerView
        PastEventsRecyclerView = view.findViewById(R.id.PastEventsRecyclerView);
        PastEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PastEventsRecyclerView.setHasFixedSize(true);
        PastEventsArrayList = new ArrayList<>();

        //Fetching ALL Events From Firebase
        TryToLoadDataFromSharedPreferences();


        SwipeToRefreshLayout = view.findViewById(R.id.SwipeToRefreshLayout);
        SwipeToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Toast.makeText(getContext(), "Bs... Ho Gaya Refresh", Toast.LENGTH_SHORT).show();
                getAllPastEventsFromFirebase();
                SwipeToRefreshLayout.setRefreshing(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });

        return view;
    }

    private void filterList(String s) {

        ArrayList<PastEvents> FilteredPastEvents = new ArrayList<>();
        for (PastEvents pastEvent : PastEventsArrayList) {

            if(pastEvent.getEventTitle() .toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))){
                FilteredPastEvents.add(pastEvent);
            }else if(pastEvent.getEventDescription().toLowerCase().contains(s.toLowerCase())){
                FilteredPastEvents.add(pastEvent);
            }

        }
        if(FilteredPastEvents.isEmpty()){
            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }else{
            PastEventRecyclerViewAdapter.filterlist(FilteredPastEvents);
        }
    }


    private void AddEventListenerToTags() {

        for (int ChildrenAt = 0; ChildrenAt < TagsLinearLayout.getChildCount(); ChildrenAt++) {
            int finalChildrenAt = ChildrenAt;
            TagsLinearLayout.getChildAt(ChildrenAt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView SelectedTag = (TextView) TagsLinearLayout.getChildAt(finalChildrenAt);
                    SelectedTag.setTextColor(getResources().getColor(R.color.white));
                    SelectedTag.requestFocus();
                    SelectedTag.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Google_Blue)));
                    for (int i = 0; i < TagsLinearLayout.getChildCount(); i++) {
                        if (i == finalChildrenAt) {
                            continue;
                        } else {
                            TextView tags = (TextView) TagsLinearLayout.getChildAt(i);
                            tags.setBackgroundTintList(null);
                            tags.setTextColor(getResources().getColor(R.color.black));
                        }
                    }
                }
            });
        }

    }


    private boolean TryToLoadDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String Json = sharedPreferences.getString(PAST_EVENTS_LIST, null);
        Type type = new TypeToken<ArrayList<PastEvents>>() {
        }.getType();
        PastEventsArrayList = gson.fromJson(Json, type);
        if (PastEventsArrayList == null) {
            PastEventsArrayList = new ArrayList<>();
            getAllPastEventsFromFirebase();
//            Toast.makeText(getContext(), "Null hai ...", Toast.LENGTH_SHORT).show();
            return false;
        }



        PastEventRecyclerViewAdapter = new RecyclerViewAdapter_PastEvents(getContext(), PastEventsArrayList);
        PastEventsRecyclerView.setAdapter(PastEventRecyclerViewAdapter);
//        Toast.makeText(getContext(), "Pta nhi kya ho rha hai ,,,", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void SaveDataToSharedPrefrence() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(PastEventsArrayList);
        editor.putString(PAST_EVENTS_LIST, json);
        editor.apply();
    }


    private void getAllPastEventsFromFirebase() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference PastEventsDatabaseReference = database.getReference("/EventsInformation/PastEvents/");
        // Read from the database

        PastEventsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    PastEvents events = postSnapshot.getValue(PastEvents.class);
                    PastEventsArrayList.add(events);
                    SaveDataToSharedPrefrence();

                }
                PastEventRecyclerViewAdapter = new RecyclerViewAdapter_PastEvents(getContext(), PastEventsArrayList);
                PastEventsRecyclerView.setAdapter(PastEventRecyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d(TAG, String.valueOf(PastEventsArrayList));
    }
}