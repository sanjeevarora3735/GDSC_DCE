package com.sanjeev.gdscdce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class GoogleSignin extends AppCompatActivity {

    // Object to communicate with the GoogleSignIn Client
    private GoogleSignInClient googleSignInClient;

    // Declaring the login button
    private Button LoginButton;

    // Our Animated Loader ....
    private GifImageView AnimatedLoaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlesignin);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Initialization some important view from our objects...
        AnimatedLoaderView = findViewById(R.id.AnimatedLoader);
        LoginButton = findViewById(R.id.LoginButton);

        // Google Signin Setup
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Getting the google_client to perform the signin
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Setting up the on ClickListener for the login button
        LoginButton.setOnClickListener(v -> {
            AnimatedLoaderView.setVisibility(View.VISIBLE);
            startActivityForResult(googleSignInClient.getSignInIntent(), 777);
        });

    }

 // Override the function to verify the account signin
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777) {
            Task<GoogleSignInAccount> googleSignInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = googleSignInAccountTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(authCredential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                AnimatedLoaderView.setVisibility(View.GONE);
                                FirebaseFirestore.getInstance().collection("GDSC_DCE").document("Users_Information")
                                        .collection("Registration_Details").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    if(task.getResult().exists()){
                                                        startActivity(new Intent(GoogleSignin.this, DashBoard.class));
                                                    }else{
                                                        startActivity(new Intent(getApplicationContext(), Registration.class));
//                                                        Toast.makeText(GoogleSignin.this, "Tu Nhi Jayega", Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    Toast.makeText(GoogleSignin.this, "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                AnimatedLoaderView.setVisibility(View.GONE);
                                Toast.makeText(GoogleSignin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (ApiException e) {
                AnimatedLoaderView.setVisibility(View.GONE);
                Toast.makeText(this, "Exit :" + Objects.requireNonNull(e.getMessage()).toLowerCase(Locale.ROOT) , Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    // Override the onStart() for the automatic signin function...

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(GoogleSignin.this, DashBoard.class));
        }
    }
}