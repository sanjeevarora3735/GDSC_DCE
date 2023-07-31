package com.sanjeev.gdscdce;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
                    Log.d("FCM TOKEN", token);
                });

        FirebaseMessaging.getInstance().subscribeToTopic("GDSCDCE_USERS");

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            SharedPreferences sharedPref = getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getEmail().toLowerCase(), Context.MODE_PRIVATE);
            boolean registrationIncomplete = sharedPref.getBoolean("registration_incomplete", false);

            if (registrationIncomplete) {
                Intent registrationIntent = new Intent(this, Walkthrough.class);
                startActivity(registrationIntent);
            }else {
                startActivity(new Intent(SplashScreen.this, DashBoard.class));
            }
        } else {
            startActivity(new Intent(SplashScreen.this, Walkthrough.class));
//            startActivity(new Intent(SplashScreen.this, DashBoard.class));
        }
    }
}