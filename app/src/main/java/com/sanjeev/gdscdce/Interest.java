package com.sanjeev.gdscdce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class Interest extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    private ImageView Forward, Backward;
    private LinearLayout Blog1, Blog2;
    String InterestCategory ="";
    String[] DevelopmentImages = new String[4];
    String[] AndroidDevImages = new String[4];
    String[] WebDevImages = new String[4];
    String[] DesigningImages = new String[4];

    private LinearLayout CoursesLinearLayout;

    private TextView Heading, Heading2, Category, Category2, Body, Body2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        CoursesLinearLayout = findViewById(R.id.CoursesLinearLayout);

        Heading = findViewById(R.id.Heading);
        Heading2 = findViewById(R.id.Heading_2);
        Category = findViewById(R.id.Category);
        Category2 = findViewById(R.id.Category_2);
        Body = findViewById(R.id.Body_1);
        Body2 = findViewById(R.id.Body_2);


        InterestCategory = getIntent().getStringExtra("InterestCat");

        if(InterestCategory.contains("Android Development")){
            Heading.setText("5 Best Practices for Android App Development");
            Category.setText("App Development");
            Body.setText(R.string.AppDevBlog_1);
            Heading2.setText("How to Build a Custom View in Android");
            Category2.setText("App Development");
            Body2.setText(R.string.AppDevBlog_2);
        }else if(InterestCategory.contains("Web Development")){
            Heading.setText("5 Best Practices for Web Development");
            Category.setText("Web Development");
            Body.setText(R.string.WebDevBlog_1);
            Heading2.setText("How to Create a Responsive Website");
            Category2.setText("Web Development");
            Body2.setText(R.string.WebDevBlog_2);
        }else if(InterestCategory.contains("Designing")){
            Heading.setText("5 Key Principles of Effective UI/UX Design");
            Category.setText("Design");
            Body.setText(R.string.DesignBlog_1);
            Heading2.setText("How to Conduct User Research for UI/UX Design");
            Category2.setText("Design");
            Body2.setText(R.string.DesignBlog_2);
        }

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
                Blog2.putExtra("InterestCat", InterestCategory);
                startActivity(Blog2);            }
        });

        Blog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Blog2 = new Intent(Interest.this, ViewBlog.class);
                Blog2.putExtra("InterestCat", InterestCategory);
                Blog2.putExtra("Which", "Two");
                startActivity(Blog2);
            }
        });




        Forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSwitcher.setImageResource(R.drawable.coming_soon);
            }
        });


        Backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSwitcher.setImageResource(R.drawable.coming_soon);
            }
        });


        imageSwitcher.setFactory(() -> {
            ImageView imageView = new ImageView(getApplicationContext());
            return imageView;
        });

        CoursesLinearLayout.getChildAt(0).setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://learndigital.withgoogle.com/digitalgarage/courses?category=data"));
            startActivity(browserIntent);
        });
        CoursesLinearLayout.getChildAt(1).setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://learndigital.withgoogle.com/digitalgarage/courses?category=data"));
            startActivity(browserIntent);
        });
        CoursesLinearLayout.getChildAt(2).setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://learndigital.withgoogle.com/digitalgarage/courses?category=data"));
            startActivity(browserIntent);
        });

        // Set an animation for transitioning between images
        imageSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
        imageSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);

        // Set the first image to be displayed
        imageSwitcher.setImageResource(R.drawable.coming_soon);




    }
}