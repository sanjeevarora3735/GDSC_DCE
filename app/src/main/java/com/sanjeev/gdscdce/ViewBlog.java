package com.sanjeev.gdscdce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewBlog extends AppCompatActivity {

    private TextView BlogHeading, BlogText;
    private ImageView BlogImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blog);

        ((ImageView) findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewBlog.super.onBackPressed();
            }
        });
        BlogHeading = findViewById(R.id.BlogHeading);
        BlogImage = findViewById(R.id.BlogImage);
        BlogText = findViewById(R.id.BlogText);

        Intent newIntent = getIntent();
        String InterestCategory = newIntent.getStringExtra("InterestCat");

        String Blog = newIntent.getStringExtra("Which");

        if (InterestCategory.contains("NoIssues")) {
            if (Blog.contains("One")) {
                BlogImage.setImageResource(R.drawable.blog1);
            } else {
                BlogHeading.setText(getResources().getString(R.string.BlogHeading));
                BlogText.setText(getResources().getString(R.string.Blog2));
                BlogImage.setImageResource(R.drawable.blog2);
            }
        } else if (InterestCategory.contains("Android Development") && Blog.contains("One")) {
            BlogHeading.setText("5 Best Practices for Android App Development");
            BlogText.setText(getResources().getString(R.string.AppDevBlog_1));
            BlogImage.setImageResource(R.drawable.android_1);
        } else if (InterestCategory.contains("Android Development")) {
            BlogHeading.setText("How to Build a Custom View in Android");
            BlogText.setText(getResources().getString(R.string.AppDevBlog_2));
            BlogImage.setImageResource(R.drawable.android_2);
        } else if (InterestCategory.contains("Web Development") && Blog.contains("One")) {
            BlogHeading.setText("5 Best Practices for Web Development");
            BlogText.setText(getResources().getString(R.string.WebDevBlog_1));
            BlogImage.setImageResource(R.drawable.webdev_1);
        } else if (InterestCategory.contains("Web Development")) {
            BlogHeading.setText("How to Create a Responsive Website");
            BlogText.setText(getResources().getString(R.string.WebDevBlog_2));
            BlogImage.setImageResource(R.drawable.webdev_2);
        } else if (InterestCategory.contains("Designing") && Blog.contains("One")) {
            BlogHeading.setText("5 Key Principles of Effective UI/UX Design");
            BlogText.setText(getResources().getString(R.string.DesignBlog_1));
            BlogImage.setImageResource(R.drawable.uiux2);
        } else if (InterestCategory.contains("Designing")) {
            BlogHeading.setText("How to Conduct User Research for UI/UX Design");
            BlogText.setText(getResources().getString(R.string.DesignBlog_2));
            BlogImage.setImageResource(R.drawable.uiux);
        }


    }
}