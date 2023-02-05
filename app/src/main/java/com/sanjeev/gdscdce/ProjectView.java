package com.sanjeev.gdscdce;

import static com.sanjeev.gdscdce.DashBoard.PROJECT_LIST;
import static com.sanjeev.gdscdce.DashBoard.SHARED_PREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sanjeev.gdscdce.Model.AllProjects;
import com.sanjeev.gdscdce.Model.Registration_Model;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectView extends AppCompatActivity {
    private String ProjectID;
    private ImageButton BackButton;
    private CircleImageView DeveloperUserProfileImage;
    private ShapeableImageView ProjectPosterImage;
    private TextView ProjectTitle, ProjectDesc, DeveloperName, Approve, MentorName;
    private LinearLayout ProjectTagsLinearLayout;
    private MaterialButton SuccessButton;
    private RelativeLayout AnimatedLoader_Rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);

        FetchUserBasicInformation();

        ProjectPosterImage = findViewById(R.id.PosterImage);
        ProjectTitle = findViewById(R.id.ProjectTitle);
        ProjectDesc = findViewById(R.id.ProjectDescription);
        DeveloperName = findViewById(R.id.DeveloperName);
        Approve = findViewById(R.id.Approve);
        BackButton = findViewById(R.id.imageButton);
        MentorName = findViewById(R.id.Name_Mentor);
        ProjectTagsLinearLayout = findViewById(R.id.ProjectTagsLinearLayout);
        SuccessButton = findViewById(R.id.SuccessButton);
        DeveloperUserProfileImage = findViewById(R.id.DeveloperUserProfileImage);
        AnimatedLoader_Rl = findViewById(R.id.AnimatedLoader_Rl);



        BackButton.setOnClickListener(v -> {
            super.onBackPressed();
        });

        Intent GetProjectIntent = getIntent();
        ProjectID = GetProjectIntent.getStringExtra("AccessedUrl");

        SetupProjectOverview();
    }

    private Registration_Model FetchUserBasicInformation() {
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String CollegeMail = sharedPref.getString("CMail", null);
        String ContactNumber = sharedPref.getString("ContactNumber", null);
        String InviteCode = sharedPref.getString("InviteCode", null);

        Registration_Model BasicInformation = new Registration_Model(CollegeMail, ContactNumber, InviteCode);
        return BasicInformation;
    }


    private void SetupProjectOverview() {
        ArrayList<AllProjects> AllProjectsArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String Json = sharedPreferences.getString(PROJECT_LIST, null);
        Type type = new TypeToken<ArrayList<AllProjects>>() {
        }.getType();
        AllProjectsArrayList = gson.fromJson(Json, type);
        int ProjectCounter = AllProjectsArrayList.size();
        for (AllProjects Project : AllProjectsArrayList) {

            if (Project.getProjectID().contains(ProjectID)) {
                Log.d("ProjectView", Project.getProjectID() + "  :::   " + ProjectID);

                ProjectDesc.setText(Project.getProjectDescriptionBody());
                ProjectTitle.setText(Project.getProjectName());
                DeveloperName.setText(Project.getProjectOwner());

                //Setting up the UserImage & PosterImage
                try {
                    Picasso.get().load(Project.getProfileImageUrl()).into(DeveloperUserProfileImage);
                    Picasso.get().load(Project.getProjectPosterImageUrl()).into(ProjectPosterImage);
                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                String[] Tags = Project.getProjectTags().split(",");

                ProjectTagsLinearLayout.removeAllViews();
                for (String Tag : Tags) {
                    TextView tag = new TextView(this);
                    tag.setText(Tag);
                    tag.setBackground(getDrawable(R.drawable.actionbaricon_light));
                    LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    Params.setMarginStart(5);
                    tag.setLayoutParams(Params);
                    float scale = getResources().getDisplayMetrics().density;
                    tag.setPadding((int) (15 * scale + 0.5f), (int) (8 * scale + 0.5f), (int) (15 * scale + 0.5f), (int) (8 * scale + 0.5f));
                    tag.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tag.setTextColor(getResources().getColor(R.color.black));
                    tag.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (12 * scale + 0.5f));
                    ProjectTagsLinearLayout.addView(tag);
                }
                if (Project.getProjectApprovalDate().length() == 0) {
                    SuccessButton.setText("Approve Project");
                    Approve.setText("Mentor Required:");
                    MentorName.setText("App Dev Lead");
                } else {
                    SuccessButton.setText("View Project");
                    Approve.setText("Approved by:");
                    MentorName.setText(Project.getProjectApprovalMentor());
                }

                int finalProjectCounter = ProjectCounter;
                if(SuccessButton.getText().toString().contains("Approve")) {
                    SuccessButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AnimatedLoader_Rl.setVisibility(View.VISIBLE);
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("/ProjectsInformation/AllProjects/");
                            HashMap ProjectDetails = new HashMap();
                            ProjectDetails.put("ProjectApprovalMentor", FetchUserBasicInformation().getUsername());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                ProjectDetails.put("ProjectApprovalDate", String.valueOf(LocalDate.now()));
                            }else{
                                ProjectDetails.put("ProjectApprovalDate", String.valueOf(Calendar.getInstance().getTime()));
                            }
                            db.child(String.valueOf(finalProjectCounter)).updateChildren(ProjectDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ProjectView.this, "Success", Toast.LENGTH_SHORT).show();
                                    AnimatedLoader_Rl.setVisibility(View.GONE);
                                    SuccessButton.setText("View Project");
                                    ProjectView.super.onBackPressed();
                                    SaveUpdatedProjectsToSharedPreferences();
                                }
                            });
                        }
                    });
                }else{
                    SuccessButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String ProjectUrl = Project.getProjectSourceUrl();
                            Uri uri;
                            if(ProjectUrl.contains("http")) {
                                 uri = Uri.parse(ProjectUrl);
                            }else{
                                ProjectUrl = "https://"+ProjectUrl;
                                uri = Uri.parse(ProjectUrl); // missing 'http://' will cause crashed

                            }
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });
                }
                ProjectCounter++;
                break;
            }
        }
    }

    private void SaveUpdatedProjectsToSharedPreferences() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference AllProjectsDatabaseReference = database.getReference("/ProjectsInformation/AllProjects/");

        ArrayList<AllProjects> AllProjectsArrayList  = new ArrayList<>();
        // Read from the database

        AllProjectsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    AllProjects projects = postSnapshot.getValue(AllProjects.class);
                    AllProjectsArrayList.add(projects);
                }
                SaveDataToSharedPrefrence(PROJECT_LIST, AllProjectsArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SaveDataToSharedPrefrence(String projectList, ArrayList<AllProjects> AllProjectsArrayList) {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AllProjectsArrayList);
        editor.putString(PROJECT_LIST, json);
        editor.apply();
    }
}
