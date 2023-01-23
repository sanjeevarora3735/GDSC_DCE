package com.sanjeev.gdscdce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.sanjeev.gdscdce.R;

public class ViewPageAdapter extends PagerAdapter {

    // Creating the Context Variable to manage the Views
    Context context;

    // Some Important Arrays of the Walk through Pages
    int[] Images = {R.drawable.walkthrough_003, R.drawable.walkthrough_000, R.drawable.walkthrough_002};
    int[] Heading = {R.string.lorem_isupum, R.string.lorem_isupum, R.string.lorem_isupum};
    int[] Description = {R.string.DemoString, R.string.DemoString, R.string.DemoString};

    // Constructor
    public ViewPageAdapter(Context context) {
        this.context = context;
    }


    // Function To Get that how many pages are there...
    @Override
    public int getCount() {
        return Heading.length;
    }


    // Function To Pass The Object to the View...
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    // Replacing the position for the next images & other views
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.walkthrough_slider, container, false);

        ImageView WalkthroughImage = view.findViewById(R.id.WalkthroughImage);
        TextView slideHeading = view.findViewById(R.id.Heading);
        TextView slideDescription = view.findViewById(R.id.Description);

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
