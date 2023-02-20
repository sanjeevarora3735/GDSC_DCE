package com.sanjeev.gdscdce;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.sanjeev.gdscdce.fragments.ExploreFragment.PAST_EVENTS_LIST;
import static com.sanjeev.gdscdce.fragments.StatsFragment.SHARED_PREFERENCES;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sanjeev.gdscdce.Model.PastEvents;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EventOverview extends AppCompatActivity {

    private String AccessedUrl;
    private TextView EventName_TopBackButtom, EventTitle_Overview, EventAbout, EventTimiline, EventTimings;
    private LinearLayout EventTagsLinearLayout, BackButtonTop;
    private ImageView EventPosterImageView;
    private Button EventRegistrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_overview);

        EventName_TopBackButtom = findViewById(R.id.EventName_TopBackButtom);
        EventTitle_Overview = findViewById(R.id.EventTitle_Overview);
        EventAbout = findViewById(R.id.EventAbout);
        EventTimiline = findViewById(R.id.EventTimilineTitle);
        EventTimings = findViewById(R.id.Timmings);
        EventTagsLinearLayout = findViewById(R.id.EventTagsLinearLayout);
        BackButtonTop = findViewById(R.id.BackButtonTop);
        EventPosterImageView = findViewById(R.id.EventPosterImageView);
        EventRegistrationButton = findViewById(R.id.EventRegistrationButton);


        BackButtonTop.setOnClickListener(v -> {
            super.onBackPressed();
        });

        Intent Event = getIntent();
        AccessedUrl = Event.getStringExtra("AccessedUrl");

        SetupTheUiModification();


    }

    private void SetupTheUiModification() {
        ArrayList<PastEvents> MultipleEvents;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String Json = sharedPreferences.getString(PAST_EVENTS_LIST, null);
        Type type = new TypeToken<ArrayList<PastEvents>>() {
        }.getType();
        MultipleEvents = gson.fromJson(Json, type);

        Log.d(TAG, "ProvidedUrl " + AccessedUrl);
        for (PastEvents Event : MultipleEvents) {
            Log.d(TAG, Event.getAccessedUrl());

            if (Event.getAccessedUrl().contains(AccessedUrl)) {


                EventRegistrationButton.setOnClickListener(v -> {
                    watchYoutubeVideo(Event.getYoutube().split("vi/")[1].split("/")[0]);
                });

                EventName_TopBackButtom.setText(Event.getEventTitle());
                EventAbout.setText(Event.getEventDescription());
                Picasso.get().load(String.valueOf(Event.getYoutube())).into((ImageView) EventPosterImageView);
                EventTimiline.setText(Event.getEventTitle());
                EventTimings.setText(Event.getEventTimings().split(",")[1] + Event.getEventTimings().split(",")[2].replace("(IST)", ""));
                EventTitle_Overview.setText(Event.getEventTitle());
                String[] Tags = Event.getEventTags().split(",");
                Log.d("EventOverView", String.valueOf(Tags.length));
                EventTagsLinearLayout.removeAllViews();
                for (String Tag : Tags) {
                    TextView tag = new TextView(this);
                    tag.setText(Tag);
                    tag.setBackground(getDrawable(R.drawable.actionbaricon_light));
                    LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    Params.setMarginStart(5);
                    tag.setLayoutParams(Params);
                    float scale = getResources().getDisplayMetrics().density;
                    tag.setPadding((int) (15 * scale + 0.5f), (int) (8 * scale + 0.5f), (int) (15 * scale + 0.5f), (int) (8 * scale + 0.5f));
                    tag.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tag.setTextColor(getResources().getColor(R.color.black));
                    tag.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (12 * scale + 0.5f));
                    EventTagsLinearLayout.addView(tag, 0);


                }

            }
        }

    }

    public void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}