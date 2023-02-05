package com.sanjeev.gdscdce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjeev.gdscdce.EventOverview;
import com.sanjeev.gdscdce.Model.PastEvents;
import com.sanjeev.gdscdce.ProjectView;
import com.sanjeev.gdscdce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_PastEvents extends RecyclerView.Adapter<RecyclerViewAdapter_PastEvents.ViewHolder> {
    private final Context context;
    private List<PastEvents> pastEventsList;

    public RecyclerViewAdapter_PastEvents(Context context, List<PastEvents> pasteEventsList) {
        this.context = context;
        this.pastEventsList = pasteEventsList;
    }

    public void filterlist(ArrayList<PastEvents> filteredlist){
        pastEventsList = filteredlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_PastEvents.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_PastEvents.ViewHolder holder, int position) {

        PastEvents Event = pastEventsList.get(position);
        try {
            Picasso.get().load(Event.getYoutube()).into(holder.CardImageView);
        }catch (Exception e){
            Picasso.get().load("shorturl.at/hMPQ6").into(holder.CardImageView);
        }

    }

    @Override
    public int getItemCount() {
        return pastEventsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView CardImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            CardImageView = itemView.findViewById(R.id.PastEventImageView);
        }

        @Override
        public void onClick(View view) {

            int CurrentCardId = this.getAdapterPosition();
            Intent EventIntent = new Intent(context, EventOverview.class);
            EventIntent.putExtra("AccessedUrl", pastEventsList.get(getAdapterPosition()).getAccessedUrl());
            context.startActivity(EventIntent);

            Toast.makeText(context, "Item Clicked =>  "+pastEventsList.get(CurrentCardId).getEventTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
