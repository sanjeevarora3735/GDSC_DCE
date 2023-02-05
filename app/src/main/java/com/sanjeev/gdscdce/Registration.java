package com.sanjeev.gdscdce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjeev.gdscdce.Model.Registration_Model;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    // Declaring Input Fields To Store Registrations in Firebase
    TextInputEditText UserNameEditText, TelEditText, InviteCodeEditText;
    TextInputLayout UserNameInputLayout, TelInputLayout, InviteCodeInputLayout;

    // Error TextView
    private TextView ErrorTextView;

    // Declaring Some Important Instances of Layout File
    private ImageView UserProfileImageView;
    private Button RegistrationButton;
    private ImageButton BackImageButton;

    String LoggedEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        try {
            // Finding The Current Logged-in Email
             LoggedEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // Assigning Values to  Some Important View
        UserProfileImageView = findViewById(R.id.CurrenUserImage);
        BackImageButton = findViewById(R.id.BackButton);
        RegistrationButton = findViewById(R.id.Registration);

        // Assigning the inputFields To the Respected Object
        UserNameEditText = findViewById(R.id.UsernameEditText);
        TelEditText = findViewById(R.id.ContactEditText);
        InviteCodeEditText = findViewById(R.id.InviteCodeEditText);

        // Assigning the inputFieldsLayout To the Respected Object
        UserNameInputLayout = findViewById(R.id.Username);
        TelInputLayout = findViewById(R.id.Contact);
        InviteCodeInputLayout = findViewById(R.id.InviteCodeInputLayout);

        // Error TextView Initialisation
        ErrorTextView = findViewById(R.id.ErrorField);


        // Onclick Listener for the BackImageButton
        BackImageButton.setOnClickListener(v -> {
            super.onBackPressed();
        });
try {
    // Load the user profile image
    Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(UserProfileImageView);
}catch (Exception e){
    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
}

        // Onclick Listener for the Registration Button
        RegistrationButton.setOnClickListener(v -> {
            boolean _isErrorOccurs = false;
            ErrorTextView.setVisibility(View.GONE);

            String Name = String.valueOf(((TextInputEditText) findViewById(R.id.UsernameEditText)).getText()).trim();
            String ContactNumber = String.valueOf(((TextInputEditText) findViewById(R.id.ContactEditText)).getText()).trim();
            String Invite_RoleCode = String.valueOf(((TextInputEditText) findViewById(R.id.InviteCodeEditText)).getText()).trim();


            // Empty Text Verification
            if (!(Name.length() > 0)) {
                ErrorTextView.setVisibility(View.VISIBLE);
                ErrorTextView.setText("* Please enter your email using standard format.");
                _isErrorOccurs = true;
            } else if ((ContactNumber.length() < 10)) {
                ErrorTextView.setVisibility(View.VISIBLE);
                ErrorTextView.setText("* Please enter your contact using standard format.");
                _isErrorOccurs = true;
            }if(!(isValid(Name) && Name.contains("dronacharya.info"))){

                ErrorTextView.setVisibility(View.VISIBLE);
                ErrorTextView.setText("* Please enter your college email in standard format.");
                _isErrorOccurs = true;
            }

            // For Name Verification
//            char[] NameArr = Name.toCharArray();
//            for (char character : NameArr) {
//                if (Character.isDigit(character)) {
//                    ErrorTextView.setVisibility(View.VISIBLE);
//                    ErrorTextView.setText(R.string.name_error);
//                    _isErrorOccurs = true;
//                    break;
//                }
//            }

            // For Telephone Verification
            char[] ContactArr = ContactNumber.toCharArray();
            for (char character : ContactArr) {
                if (Character.isLetter(character)) {
                    ErrorTextView.setVisibility(View.VISIBLE);
                    ErrorTextView.setText("* Please enter your contact number using standard format.");
                    _isErrorOccurs = true;
                    break;
                }
            }

            if (!_isErrorOccurs) {
                if (null == LoggedEmail) throw new AssertionError();
                FirebaseFirestore.getInstance().collection("GDSC_DCE").document("Users_Information")
                        .collection("Registration_Details").document(LoggedEmail).set(new Registration_Model(Name, ContactNumber, Invite_RoleCode, "", "", "")).addOnSuccessListener(unused -> startActivity(new Intent(Registration.this, DashBoard.class)))
                        .addOnFailureListener(e -> Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }


        });



    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}