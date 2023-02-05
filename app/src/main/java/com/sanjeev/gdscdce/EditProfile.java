package com.sanjeev.gdscdce;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.sanjeev.gdscdce.DashBoard.SHARED_PREFERENCES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjeev.gdscdce.Model.Registration_Model;

import java.util.HashMap;
import java.util.Locale;

public class EditProfile extends AppCompatActivity {

    private MaterialButton SubmitButton;
    private TextInputEditText Username, AboutMe, Branch , Semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Username = findViewById(R.id.UsernameEditText);
        AboutMe = findViewById(R.id.AboutmeEditText);
        AboutMe.setText("I'm enjoying this GDSC DCE, hey!");
        Branch = findViewById(R.id.BranchEditText);
        Semester = findViewById(R.id.SemesterEditText);

        ApplyBackendInformation();
        FetchProfileInformation();

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap AdditionalDetails = new HashMap<>();
                AdditionalDetails.put("Branch", Branch.getText().toString());
                AdditionalDetails.put("Semester", Semester.getText().toString());
                AdditionalDetails.put("AboutMe", AboutMe.getText().toString());
                FirebaseFirestore.getInstance().collection("GDSC_DCE").document("Users_Information")
                        .collection("Registration_Details").document(FirebaseAuth.getInstance().getCurrentUser().getEmail().toLowerCase()).update(AdditionalDetails).addOnSuccessListener(unused -> EditProfile.super.onBackPressed())
                        .addOnFailureListener(e -> Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show());

            }
        });
    }

    private void FetchProfileInformation() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("/GDSC_DCE/Users_Information/Registration_Details/").document(FirebaseAuth.getInstance().getCurrentUser().getEmail().toLowerCase());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Registration_Model UpdateModel = documentSnapshot.toObject(Registration_Model.class);
                Username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                AboutMe.setText(UpdateModel.getAboutMe());
                Branch.setText(UpdateModel.getBranch());
                Semester.setText(UpdateModel.getSemester());

                Username.setEnabled(false);
                AboutMe.setEnabled(false);
                Branch.setEnabled(false);
                Semester.setEnabled(false);
            }
        });

    }

    private void ApplyBackendInformation() {

        try {
            Username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        }
        catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
    }

    private Registration_Model FetchUserBasicInformation() {
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String CollegeMail = sharedPref.getString("CMail", null);
        String ContactNumber = sharedPref.getString("ContactNumber", null);
        String InviteCode = sharedPref.getString("InviteCode", null);
        Registration_Model BasicInformation = new Registration_Model(CollegeMail, ContactNumber, InviteCode,"","","");
        return BasicInformation;
    }
}