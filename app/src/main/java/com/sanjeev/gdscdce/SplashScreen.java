package com.sanjeev.gdscdce;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Button Start = findViewById(R.id.Start);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
//                    Toast.makeText(SplashScreen.this, "Hi", Toast.LENGTH_SHORT).show();
                    String token = task.getResult();
                });

        FirebaseMessaging.getInstance().subscribeToTopic("GDSCDCE_USERS");

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(SplashScreen.this, GoogleSignin.class));

            FirebaseFirestore.getInstance().collection("GDSC_DCE")
                    .document("Users_Information")
                    .collection("Registration_Details")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                Log.d("Debugging", task.getResult().toString());
                                startActivity(new Intent(SplashScreen.this, DashBoard.class));
                            } else {
                                FirebaseAuth.getInstance().signOut();
//                                startActivity(new Intent(getApplicationContext(), GoogleSignin.class));
                            }
                        } else {
                            Toast.makeText(SplashScreen.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

//            startActivity(new Intent(SplashScreen.this, DashBoard.class));
        } else {
            startActivity(new Intent(SplashScreen.this, Walkthrough.class));
//            startActivity(new Intent(SplashScreen.this, DashBoard.class));
        }
    }
}