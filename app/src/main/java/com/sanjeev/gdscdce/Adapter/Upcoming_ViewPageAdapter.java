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

public class Upcoming_ViewPageAdapter extends PagerAdapter {

    // Creating the Context Variable to manage the Views
    Context context;

    // Some Important Arrays of the Walk through Pages
    int[] Images = {R.drawable.walkthrough_003, R.drawable.walkthrough_000, R.drawable.walkthrough_002};
    String[] Heading = {"Welcome", "Join the Network", "Begin with GDSC-DCE"};
    String[] Description = {"Welcome to the official app of GDSC-DCE! Your gateway to staying connected and informed about all the latest events, news, and updates from our community.\n", "We're excited for you to start using the app and staying connected with our community.\n" +
            "If you come across any problem or bug do contact us for further assistance.\n" +
            "\n", "Thanks for downloading the GDSC-DCE app! We're here to help you get set up and start using the app in no time. \nIf you have any questions or issues, please don't hesitate to reach out to us for support. Enjoy!."};

    // Constructor
    public Upcoming_ViewPageAdapter(Context context) {
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
        View view2 = layoutInflater.inflate(R.layout.activity_walkthrough, container, false);

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
