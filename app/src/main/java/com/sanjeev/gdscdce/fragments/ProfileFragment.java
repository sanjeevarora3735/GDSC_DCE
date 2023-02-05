package com.sanjeev.gdscdce.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.sanjeev.gdscdce.fragments.ExploreFragment.SHARED_PREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjeev.gdscdce.EditProfile;
import com.sanjeev.gdscdce.Model.CTMembers;
import com.sanjeev.gdscdce.Model.Registration_Model;
import com.sanjeev.gdscdce.R;
import com.sanjeev.gdscdce.Walkthrough;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {


    private View view = null;
    private ShapeableImageView UserProfileImage;
    private TextView EditProfileTextView, AboutMeTagLine, LogOutText, UserProfileName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        EditProfileTextView = view.findViewById(R.id.EditProfileTextView);
        AboutMeTagLine = view.findViewById(R.id.AboutMeTagLine);
        LogOutText = view.findViewById(R.id.LogOutText);
        UserProfileName = view.findViewById(R.id.UserProfileName);


        LogOutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), Walkthrough.class));
            }
        });

        SetupAboutMeTagLine();

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

        UserProfileImage = view.findViewById(R.id.UserProfileImage);

        // Settingup the UserProfile Data To Profile Activity
        try {
            Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(UserProfileImage);
            UserProfileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        EditProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfile.class));
            }
        });
        return view;
    }

    private void SetupAboutMeTagLine() {
        String Username = FetchUserBasicInformation().getUsername().split(".")[0];
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference AllProjectsDatabaseReference = database.getReference("/OrganizersInformation/Members/");
        // Read from the database
        AllProjectsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CTMembers Member = postSnapshot.getValue(CTMembers.class);

                    if (FirebaseAuth.getInstance().getCurrentUser().getDisplayName().contains(Username)) {
                        UserProfileName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        AboutMeTagLine.setText(Member.getTitle() + " at GDSC DCE ");
                        break;
                    }
                }
                if(AboutMeTagLine.getText().toString() == null || AboutMeTagLine.getText().toString() == ""){
                    AboutMeTagLine.setText("I'm enjoying this GDSC DCE, hey!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private Registration_Model FetchUserBasicInformation() {
        SharedPreferences sharedPref = getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String CollegeMail = sharedPref.getString("CMail", null);
        String ContactNumber = sharedPref.getString("ContactNumber", null);
        String InviteCode = sharedPref.getString("InviteCode", null);

        Registration_Model BasicInformation = new Registration_Model(CollegeMail, ContactNumber, InviteCode ,"","","");
        return BasicInformation;
    }


    @Override
    public void onStart() {
        super.onStart();
//        Toast.makeText(getContext(), "Refreshed Profile Fragment", Toast.LENGTH_SHORT).show();
    }
}