package com.sanjeev.gdscdce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanjeev.gdscdce.Model.HackORelayModel;
import com.squareup.picasso.Picasso;

public class Hack_o_Relay extends AppCompatActivity {

    private LinearLayout MentorRound, PPTRound, PrototypeRound;
    private TextView MentorRoundExpand, PPTRoundExpand, PrototypeRoundExpand;
    private String GoogleFormLink = "";
    private Button EventRegistrationButton;
    private ImageView Hackathon_Images;
    private TextView Faq;
    private TextView EventAbout;
    private LinearLayout Themes_Hackathon, Prizes_Hackathon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_orelay);

        EventRegistrationButton = findViewById(R.id.EventRegistrationButton);
        GoogleFormLink = getIntent().getStringExtra("RSVP");
        Faq = findViewById(R.id.FAQ);
        Hackathon_Images = findViewById(R.id.Hackathon_Images);

        Themes_Hackathon = findViewById(R.id.Themes_Hackathon);
        Prizes_Hackathon = findViewById(R.id.Prizes_Hackathon);

//        Hackathon Update Elements
        EventAbout = findViewById(R.id.EventAbout);
        Faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.sanjeev.gdscdce.Faq.class));
            }
        });


        EventRegistrationButton.setOnClickListener(v -> {
            Uri uri = Uri.parse(GoogleFormLink);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });


        MentorRound = findViewById(R.id.MentorRoundLinearLayout);
        PPTRound = findViewById(R.id.PPTRoundLinearLayout);
        PrototypeRound = findViewById(R.id.PrototypeRoundLinearLayout);

        MentorRoundExpand = findViewById(R.id.expanded_menu_1);
        PPTRoundExpand = findViewById(R.id.expanded_menu_2);
        PrototypeRoundExpand = findViewById(R.id.expanded_menu_3);

        MentorRound.setOnClickListener(v -> {
            if (MentorRoundExpand.getVisibility() == View.VISIBLE) {
                MentorRoundExpand.setVisibility(View.GONE);
            } else {
                PPTRoundExpand.setVisibility(View.GONE);
                PrototypeRoundExpand.setVisibility(View.GONE);
                MentorRoundExpand.setVisibility(View.VISIBLE);
            }
        });
        PPTRound.setOnClickListener(v -> {
            if (PPTRoundExpand.getVisibility() == View.VISIBLE) {
                PPTRoundExpand.setVisibility(View.GONE);
            } else {
                PPTRoundExpand.setVisibility(View.VISIBLE);
                PrototypeRoundExpand.setVisibility(View.GONE);
                MentorRoundExpand.setVisibility(View.GONE);
            }
        });
        PrototypeRound.setOnClickListener(v -> {
            if (PrototypeRoundExpand.getVisibility() == View.VISIBLE) {
                PrototypeRoundExpand.setVisibility(View.GONE);
            } else {
                PPTRoundExpand.setVisibility(View.GONE);
                PrototypeRoundExpand.setVisibility(View.VISIBLE);
                MentorRoundExpand.setVisibility(View.GONE);
            }
        });


        UpdateTheHackathonDetails();


    }

    private void UpdateTheHackathonDetails() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("/GDSC_DCE/").document("Hack_o_Relay");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    if (documentSnapshot.exists()) {
                        HackORelayModel Hack0Relay = documentSnapshot.toObject(HackORelayModel.class);

                        if (Hack0Relay.getHackathonAbout().length() > 0) {
                            EventAbout.setText(Hack0Relay.getHackathonAbout());
                        }
                        int ThemesCard = 6;
                        for (String ThemesImagesUrl : Hack0Relay.getThemesImages()) {
                            Picasso.get().load(ThemesImagesUrl).into((ShapeableImageView) Themes_Hackathon.getChildAt(--ThemesCard));
                        }
                        try {
                            Picasso.get().load(Hack0Relay.getMainPosterImage()).into(Hackathon_Images);
                        } catch (Exception e) {
                            Log.d("Error", e.getMessage());
                        }

                        try {
                            Picasso.get().load(Hack0Relay.getFirstPrize()).into((ShapeableImageView) Prizes_Hackathon.getChildAt(0));
                            Picasso.get().load(Hack0Relay.getSecondPrize()).into((ShapeableImageView) Prizes_Hackathon.getChildAt(1));
                            Picasso.get().load(Hack0Relay.getThirdPrize()).into((ShapeableImageView) Prizes_Hackathon.getChildAt(2));
                        } catch (Exception e) {
                            Log.d("Error", e.getMessage());
                        }


                        if (Hack0Relay.getTimelineHeading().size() > 0 && Hack0Relay.getTimelineHeading().get(0).length() > 0) {
                            ((TextView) findViewById(R.id.MentorRoundTextView)).setText(Hack0Relay.getTimelineHeading().get(0));
                            ((TextView) findViewById(R.id.PPTPresentationTextView)).setText(Hack0Relay.getTimelineHeading().get(1));
                            ((TextView) findViewById(R.id.PrototypePresentationTextView)).setText(Hack0Relay.getTimelineHeading().get(2));
                        }
                        if (Hack0Relay.getTimelineDescriptions().size() > 0 && Hack0Relay.getTimelineDescriptions().get(0).length() > 0) {
                            ((TextView) findViewById(R.id.expanded_menu_1)).setText(Hack0Relay.getTimelineDescriptions().get(0));
                            ((TextView) findViewById(R.id.expanded_menu_2)).setText(Hack0Relay.getTimelineDescriptions().get(1));
                            ((TextView) findViewById(R.id.expanded_menu_3)).setText(Hack0Relay.getTimelineDescriptions().get(2));
                        }


                        Log.d("Hack0Relay", Hack0Relay.toString());
                    } else {
                        Log.d("Hack0Relay", "No Such Document");
                    }
                } else {
                    Log.d("Hack0Relay", "Task Failed");
                }
            }
        });


    }
}