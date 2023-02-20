package com.sanjeev.gdscdce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewBlog extends AppCompatActivity {

    private TextView BlogHeading, BlogText;
    private ImageView BlogImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blog);

        ((ImageView)findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewBlog.super.onBackPressed();
            }
        });
        BlogHeading = findViewById(R.id.BlogHeading);
        BlogImage = findViewById(R.id.BlogImage);
        BlogText = findViewById(R.id.BlogText);

        Intent newIntent = getIntent();
        String Blog = newIntent.getStringExtra("Which");

        if(Blog.contains("One")){
             BlogImage.setImageResource(R.drawable.blog1);
        }else {
            BlogHeading.setText(getResources().getString(R.string.BlogHeading));
            BlogText.setText(getResources().getString(R.string.Blog2));
            BlogImage.setImageResource(R.drawable.blog2);
        }




    }
}