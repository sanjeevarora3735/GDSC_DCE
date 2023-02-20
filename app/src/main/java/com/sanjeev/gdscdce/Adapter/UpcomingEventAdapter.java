package com.sanjeev.gdscdce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.sanjeev.gdscdce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UpcomingEventAdapter extends PagerAdapter {

    Context context;
    String[] URLS;

    public UpcomingEventAdapter(Context context, String[] URLS) {
        this.context = context;
        this.URLS = URLS;
        Toast.makeText(context, "Length of Provided Urls are : "+URLS.length, Toast.LENGTH_SHORT).show();
    }



    @Override
    public int getCount() {
        return URLS.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.upcoming_events, null);

        ImageView imageView = view.findViewById(R.id.UpComingEventsPoster);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(imageView.getContext(), "Hey My ID is "+ position, Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.get().load(URLS[position]).into(imageView);

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
