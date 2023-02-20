package com.sanjeev.gdscdce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

public class Interest extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    private ImageView Forward, Backward;
    private LinearLayout Blog1, Blog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        imageSwitcher = findViewById(R.id.imageSwitcher);
        Forward = findViewById(R.id.Forward);
        Backward = findViewById(R.id.Backward);

        Blog1 = findViewById(R.id.Blog1);
        Blog2 = findViewById(R.id.Blog2);

        Blog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Blog2 = new Intent(Interest.this, ViewBlog.class);
                Blog2.putExtra("Which", "One");
                startActivity(Blog2);            }
        });

        Blog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Blog2 = new Intent(Interest.this, ViewBlog.class);
                Blog2.putExtra("Which", "Two");
                startActivity(Blog2);
            }
        });




        Forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSwitcher.setImageResource(R.drawable.samplethumbnail);
            }
        });


        Backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSwitcher.setImageResource(R.drawable.samplethumbnail);
            }
        });


        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });

        // Set an animation for transitioning between images
        imageSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
        imageSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);

        // Set the first image to be displayed
        imageSwitcher.setImageResource(R.drawable.samplethumbnail);




    }
}