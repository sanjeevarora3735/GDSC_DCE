package com.sanjeev.gdscdce;

import static android.view.View.inflate;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.sanjeev.gdscdce.DashBoard.SHARED_PREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanjeev.gdscdce.Model.AllProjects;
import com.sanjeev.gdscdce.Model.Registration_Model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

import pl.droidsonroids.gif.GifImageView;

public class UploadProject extends AppCompatActivity {

    private static final int ImageUpload = 0;
    private EditText ProjectUrl, ProjectTitle, BreifOverview;
    private ShapeableImageView ProjectPosterImage;
    private Bitmap bitmap;
    private String ProjectCounter;
    private GifImageView AnimationLoader;
    private MaterialButton Review;
    private ImageView imagebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_project);

        ProjectUrl = findViewById(R.id.GithubEditText);
        ProjectTitle = findViewById(R.id.TitleEditText);
        BreifOverview = findViewById(R.id.OverviewEditText);
        AnimationLoader = findViewById(R.id.AnimationLoader);
        Review = findViewById(R.id.ReviewButton);
        imagebutton = findViewById(R.id.imageButton);

        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadProject.super.onBackPressed();
            }
        });


        Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectToStateApproval();
            }
        });

        ProjectPosterImage = findViewById(R.id.ProjectPosterImage);

        ProjectPosterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Choose Landscape Image "), ImageUpload);
//                Intent PickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(PickImageIntent, ImageUpload);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageUpload && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ProjectPosterImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Registration_Model FetchUserBasicInformation() {
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String CollegeMail = sharedPref.getString("CMail", null);
        String ContactNumber = sharedPref.getString("ContactNumber", null);
        String InviteCode = sharedPref.getString("InviteCode", null);
        ProjectCounter = sharedPref.getString("TotalProjects", null);

        Registration_Model BasicInformation = new Registration_Model(CollegeMail, ContactNumber, InviteCode,"","","");
        return BasicInformation;
    }

    private boolean ProjectToStateApproval() {

        String GithubUrl = "";
        GithubUrl = ProjectUrl.getText().toString();
        String ProjectName = ProjectTitle.getText().toString();
        String ProjectDescription = BreifOverview.getText().toString();
        String DeveloperImage = "";
        String ProjectTags = "";
        String PosterImageUrl = "";
        String ProjectID = ProjectName + GithubUrl;

        try {
            DeveloperImage = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        } catch (Exception e) {
        }
        if (URLUtil.isValidUrl(GithubUrl) && GithubUrl.length() > 0 && ProjectName.length() > 0 && ProjectDescription.length() > 0) {

            AllProjects NewProject = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NewProject = new AllProjects(ProjectName, ProjectID, GithubUrl, ProjectDescription, DeveloperImage, ProjectTags, FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), "", LocalDate.now().toString() , "", "", false);
            }else{
                NewProject = new AllProjects(ProjectName, ProjectID, GithubUrl, ProjectDescription, DeveloperImage, ProjectTags, FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), "", "Min-SdkFalse" , "", "", false);
            }

            AnimationLoader.setVisibility(View.VISIBLE);
            Log.d(TAG, (NewProject.toString()));
            try {
                SendToReview(NewProject, ProjectCounter);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        } else {
            Toast.makeText(this, "Invalid Url", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void SendToReview(AllProjects newProject, String CurrentProjectCounter) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("/ProjectsInformation/AllProjects/");
        db.child(String.valueOf(CurrentProjectCounter)).setValue(newProject);

        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int TotalProjects = Integer.parseInt(CurrentProjectCounter);
        editor.putString("TotalProjects", String.valueOf(++TotalProjects));
        editor.apply();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationLoader.setVisibility(View.GONE);
                UploadProject.super.onBackPressed();
            }
        }, 1000);


    }

}