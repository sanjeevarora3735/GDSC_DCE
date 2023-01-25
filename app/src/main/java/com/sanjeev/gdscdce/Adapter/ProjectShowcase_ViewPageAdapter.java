package com.sanjeev.gdscdce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.sanjeev.gdscdce.R;

public class ProjectShowcase_ViewPageAdapter extends PagerAdapter {

    public ProjectShowcase_ViewPageAdapter(Context context) {
        this.context = context;
    }

    Context context;
    // Some Important Arrays of the Walk through Pages
    int[] Images = {R.drawable.gdsc_dce, R.drawable.android_developer, R.drawable.web_development};
    String[] Heading = {"College Bus Tracker", "Library Management System", "Blockchain Project"};
    int[] Description = {R.string.DemoString, R.string.DemoString, R.string.DemoString};

    @Override
    public int getCount() {
        return Images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.projectshowcase_card, container, false);

        ImageView WalkthroughImage = view.findViewById(R.id.Poster);
        TextView slideHeading = view.findViewById(R.id.ProjectTitle);
        TextView slideDescription = view.findViewById(R.id.ProjectDescription);

        WalkthroughImage.setImageResource(Images[position]);
        slideHeading.setText(Heading[position]);
        slideDescription.setText(Description[position]);

        container.addView(view);

        return view;
    }


//    Function to remove the views

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout) object);

    }
}
