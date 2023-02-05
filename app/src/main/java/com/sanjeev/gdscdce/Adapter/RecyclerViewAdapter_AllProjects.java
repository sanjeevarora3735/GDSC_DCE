package com.sanjeev.gdscdce.Adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.sanjeev.gdscdce.Model.AllProjects;
import com.sanjeev.gdscdce.ProjectView;
import com.sanjeev.gdscdce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapter_AllProjects extends RecyclerView.Adapter<RecyclerViewAdapter_AllProjects.ViewHolder> {
    private final Context context;
    private final ArrayList<AllProjects> AllProjectsList = new ArrayList<>();

    public RecyclerViewAdapter_AllProjects(Context context, List<AllProjects> AllProjectsList, String UserType) {
        this.AllProjectsList.clear();
        this.context = context;
        int[] normal_ind = new int[AllProjectsList.size()];
        Arrays.fill(normal_ind, 999);
        for (int i = 0; i < AllProjectsList.size(); i++) {
            if ((AllProjectsList.get(i).getisShowcase()) || AllProjectsList.get(i).getProjectApprovalDate().length() == 0) {
                normal_ind[i] = 888;
                Log.d(TAG, UserType);
                if (UserType.contains("CTMember") && AllProjectsList.get(i).getisShowcase() == false) {
                    normal_ind[i] = 999;
                }
            }
        }


        Log.d(TAG, "AllProjects :   " + Arrays.toString(normal_ind));
        for (int i = 0; i < normal_ind.length; i++) {
            if (normal_ind[i] != 888) {
                this.AllProjectsList.add(AllProjectsList.get(i));
            }
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_AllProjects.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.normalprojects, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_AllProjects.ViewHolder holder, int position) {

        AllProjects Project = AllProjectsList.get(position);

        // SettingUpThe Project Image To The ShapeableImageView
        try {
            Picasso.get().load(Project.getProjectPosterImageUrl()).into(holder.ProjectImage);
        } catch (Exception e) {
            Picasso.get().load("shorturl.at/hMPQ6").into(holder.ProjectImage);
        }

        // Linear Layout BackgroundColor
        if (position % 2 == 0) {
            holder.LinearLayoutProject.setBackgroundColor(holder.itemView.getResources().getColor(R.color.EvenProjectColor));
        } else {
            holder.LinearLayoutProject.setBackgroundColor(holder.itemView.getResources().getColor(R.color.OddProjectColor));
        }
        // ProjectName
        holder.ProjectName.setText(Project.getProjectName());
        // ProjectOwner : Developer Name
        holder.ProjectOwner.setText(Project.getProjectOwner());
        // ProjectDescriptionBody
        holder.ProjectDescriptionBody.setText(Project.getProjectDescriptionBody());

    }

    @Override
    public int getItemCount() {
        return AllProjectsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ShapeableImageView ProjectImage;
        public TextView ProjectName, ProjectOwner, ProjectDescriptionBody;
        public LinearLayout LinearLayoutProject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ProjectImage = itemView.findViewById(R.id.ProjectImage);
            ProjectName = itemView.findViewById(R.id.ProjectTitle);
            LinearLayoutProject = itemView.findViewById(R.id.LinearLayoutProject);
            ProjectOwner = itemView.findViewById(R.id.ProjectOwner);
            ProjectDescriptionBody = itemView.findViewById(R.id.ProjectDescriptionTextView);
        }

        @Override
        public void onClick(View view) {

            // Onclick Event For Each Card

            Intent ProjectIntent = new Intent(context, ProjectView.class);
            ProjectIntent.putExtra("AccessedUrl", AllProjectsList.get(getAdapterPosition()).getProjectID());
            context.startActivity(ProjectIntent);

        }
    }
}
