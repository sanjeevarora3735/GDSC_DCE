package com.sanjeev.gdscdce.Adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.sanjeev.gdscdce.Model.AllProjects;
import com.sanjeev.gdscdce.ProjectView;
import com.sanjeev.gdscdce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class ProjectShowcase_ViewPageAdapter extends PagerAdapter {
    private final Context context;
    private final ArrayList<AllProjects> ShowcaseProjectList;
    public ProjectShowcase_ViewPageAdapter(Context context, List<AllProjects> showcaseProjectList) {
        ShowcaseProjectList = new ArrayList<>();
        this.context = context;
        int ShowcaseIndex[] = new int[showcaseProjectList.size()];
        Arrays.fill(ShowcaseIndex, 999);
        for (int i = 0; i < ShowcaseIndex.length; i++) {
            if(showcaseProjectList.get(i).getisShowcase()){
                ShowcaseIndex[i] = 555;
            }
        }
        for (int i = 0; i < ShowcaseIndex.length; i++) {
            if(ShowcaseIndex[i] == 555){
                ShowcaseProjectList.add(showcaseProjectList.get(i));
            }
        }

    }

    @Override
    public int getCount() {
        return ShowcaseProjectList.size();
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



        Picasso.get().load(ShowcaseProjectList.get(position).getProjectPosterImageUrl()).into(WalkthroughImage);
        slideHeading.setText(ShowcaseProjectList.get(position).getProjectName());
        slideDescription.setText(ShowcaseProjectList.get(position).getProjectDescriptionBody());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ProjectIntent = new Intent(context, ProjectView.class);
                ProjectIntent.putExtra("AccessedUrl", ShowcaseProjectList.get(position).getProjectID());
                context.startActivity(ProjectIntent);
            }
        });

        container.addView(view);

        return view;
    }


//    Function to remove the views

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout) object);

    }
}
