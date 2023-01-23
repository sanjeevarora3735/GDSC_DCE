package com.sanjeev.gdscdce.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.res.ColorStateList;
import android.nfc.Tag;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sanjeev.gdscdce.Adapter.RecyclerViewAdapter;
import com.sanjeev.gdscdce.Model.PastEvents;
import com.sanjeev.gdscdce.R;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private View view;
    private RecyclerView PastEventsRecyclerView;
    private RecyclerViewAdapter PastEventRecyclerViewAdapter;
    private ArrayList<PastEvents> PastEventsArrayList;
    private LinearLayout TagsLinearLayout;
    private SwipeRefreshLayout SwipeToRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_explore, container, false);


        // Setup OnClickListener For The Tags :)
        TagsLinearLayout = view.findViewById(R.id.TagsLinearLayout);
        AddEventListenerToTags();


        // Initialization of RecyclerView
        PastEventsRecyclerView = view.findViewById(R.id.PastEventsRecyclerView);
        PastEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PastEventsRecyclerView.setHasFixedSize(true);
        PastEventsArrayList = new ArrayList<>();

        //Fetching ALL Events From Firebase
        getAllPastEventsFromFirebase();

        SwipeToRefreshLayout = view.findViewById(R.id.SwipeToRefreshLayout);
        SwipeToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "Bs... Ho Gaya Refresh", Toast.LENGTH_SHORT).show();
                getAllPastEventsFromFirebase();
                SwipeToRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void AddEventListenerToTags() {

        for(int ChildrenAt = 0; ChildrenAt < TagsLinearLayout.getChildCount();ChildrenAt++){
            int finalChildrenAt = ChildrenAt;
            TagsLinearLayout.getChildAt(ChildrenAt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView SelectedTag = (TextView) TagsLinearLayout.getChildAt(finalChildrenAt);
                    SelectedTag.setTextColor(getResources().getColor(R.color.white));
                    SelectedTag.requestFocus();
                    SelectedTag.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Google_Blue)));
                    for(int i=0;i<TagsLinearLayout.getChildCount();i++){
                        if(i == finalChildrenAt){
                            continue;
                        }else {
                            TextView tags = (TextView) TagsLinearLayout.getChildAt(i);
                            tags.setBackgroundTintList(null);
                            tags.setTextColor(getResources().getColor(R.color.black));
                        }
                    }
                }
            });
        }

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
                }

                PastEventRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), PastEventsArrayList);
                PastEventsRecyclerView.setAdapter(PastEventRecyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d(TAG, String.valueOf(PastEventsArrayList));
    }
}