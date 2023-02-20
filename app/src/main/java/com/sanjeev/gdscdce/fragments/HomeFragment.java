package com.sanjeev.gdscdce.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.sanjeev.gdscdce.fragments.ExploreFragment.PAST_EVENTS_LIST;
import static com.sanjeev.gdscdce.fragments.StatsFragment.PROJECT_LIST;
import static com.sanjeev.gdscdce.fragments.StatsFragment.SHARED_PREFERENCES;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.sanjeev.gdscdce.Adapter.UpcomingEventAdapter;
import com.sanjeev.gdscdce.EventOverview;
import com.sanjeev.gdscdce.Interest;
import com.sanjeev.gdscdce.Model.AllProjects;
import com.sanjeev.gdscdce.Model.PastEvents;
import com.sanjeev.gdscdce.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    public static final int REQUEST_PERMISSION_CODE = 777;
    private static String[] UpcomingEventsPosters_URLS;
    private final ArrayList<Integer> UpcomingEventsPosters = new ArrayList<>();
    private ViewPager UpcomingEventsViewPager;
    private UpcomingEventAdapter upcomingEventAdapter;
    private ConstraintLayout PastEventConstraintLayout;
    private CircleImageView UserProfileImage;
    private CardView PastEvent_1_Poster, PastEvent_2_Poster;
    private LinearLayout PastEvent_1_LinearLayout, PastEvent_2_LinearLayout;
    private ConstraintLayout PastEvent_1, PastEvent_2;
    private TextView SignupLink;
    private OnButtonClicklistener mListener;
    private ImageView CARD_1, CARD_2, CARD_3, CARD_4;
    private String Urls = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        GetPosterImages();

//        RequestSomePermissions();


        PastEvent_1_LinearLayout = view.findViewById(R.id.PastEvent_1_LinearLayout);
        PastEvent_2_LinearLayout = view.findViewById(R.id.PastEvent_2_LinearLayout);
        PastEvent_1_Poster = view.findViewById(R.id.PastEvent_1_Poster);
        PastEvent_2_Poster = view.findViewById(R.id.PastEvent_2_Poster);
        PastEvent_1 = view.findViewById(R.id.PastEvent_1);
        PastEvent_2 = view.findViewById(R.id.PastEvent_2);
        SignupLink = view.findViewById(R.id.SignupLink);
        CARD_1 = view.findViewById(R.id.InterestCard_1);
        CARD_2 = view.findViewById(R.id.InterestCard_2);
        CARD_3 = view.findViewById(R.id.InterestCard_3);
        CARD_4 = view.findViewById(R.id.InterestCard_4);


        CARD_1.setOnClickListener(v -> {
            NavigateToInterests();
        });
        CARD_2.setOnClickListener(v -> {
            NavigateToInterests();
        });
        CARD_3.setOnClickListener(v -> {
            NavigateToInterests();
        });
        CARD_4.setOnClickListener(v -> {
            NavigateToInterests();
        });

        try {
            SetupLatestTwoPastEvents();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        //Setup ProfileImageView
        UserProfileImage = view.findViewById(R.id.UserProfileImage);
        try {
            Picasso.get().load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(UserProfileImage);

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        UserProfileImage.setOnClickListener(v -> {
            // Create a new instance of the fragment that you want to navigate to
//            ProfileFragment newFragment = new ProfileFragment();
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.FragmentContainerView, newFragment);
//            fragmentTransaction.commit();

            mListener.onButtonClick();

        });


        // Static Version
        UpcomingEventsViewPager = view.findViewById(R.id.UpComingEventsViewPager);
        PastEventConstraintLayout = view.findViewById(R.id.PastEvent_1);

//        int UpcomingPosterCount = 3;
//        UpcomingEventsPosters_URLS = new String[UpcomingPosterCount];
//        UpcomingEventsPosters_URLS[0] = "https://img.youtube.com/vi/7qgdJ4NmgRc/maxresdefault.jpg";
//        UpcomingEventsPosters_URLS[1] = "https://i.ibb.co/KXST0cX/flutter.png";
//        UpcomingEventsPosters_URLS[2] = "https://img.youtube.com/vi/7qgdJ4NmgRc/maxresdefault.jpg";


        SignupLink.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.GDSC_Page)));
            startActivity(browserIntent);
        });
//        SetupInterestCards();

        RefreshAllProjects();

        return view;
    }

    private String GetPosterImages() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("/GDSC_DCE/").document("STORAGE");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> PosterUrls = (List<String>) documentSnapshot.get("UpcomingPosterImages");
                for (String urls : PosterUrls) {
                    Urls += urls;
                    Urls += "___";
                }

                UpcomingEventsPosters_URLS = Urls.split("___");


                if(UpcomingEventsPosters_URLS.length>0 && UpcomingEventsPosters_URLS[0].length()>0 ) {
                    Log.d("PosterImage", UpcomingEventsPosters_URLS[0]);
                    upcomingEventAdapter = new UpcomingEventAdapter(getContext(), UpcomingEventsPosters_URLS);
                    UpcomingEventsViewPager.setAdapter(upcomingEventAdapter);
                    SetupAutomaticViewPagerSlider();
                }

                Log.d("PosterIMAGEURL2", Urls);


            }
        });


        return Urls;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnButtonClicklistener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement OnButtonClicklistener");
        }
    }

    private void RequestSomePermissions() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_NOTIFICATION_POLICY) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "PermissionGranted ", Toast.LENGTH_SHORT).show();
        } else {
            RequestNotificationPermission();
        }
    }

    private void NavigateToInterests() {
        startActivity(new Intent(getContext(), Interest.class));
    }

    private void RequestNotificationPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_NOTIFICATION_POLICY}, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SetupInterestCards() {

        FirebaseDatabase Database = FirebaseDatabase.getInstance();
        DatabaseReference InterestCard = Database.getReference("/InterestsInformation/All Cards/");

        InterestCard.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int i = 0;
                for (DataSnapshot Url : snapshot.getChildren()) {
                    if (Url.getValue() != "") {
                        if (i == 0)
                            Picasso.get().load(String.valueOf(Url.getValue())).into((ImageView) CARD_1);
                        if (i == 1)
                            Picasso.get().load(String.valueOf(Url.getValue())).into((ImageView) CARD_2);
                        if (i == 2)
                            Picasso.get().load(String.valueOf(Url.getValue())).into((ImageView) CARD_3);
                        if (i == 3)
                            Picasso.get().load(String.valueOf(Url.getValue())).into((ImageView) CARD_4);
                        i++;
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void RefreshAllProjects() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference AllProjectsDatabaseReference = database.getReference("/ProjectsInformation/AllProjects/");
        ArrayList<AllProjects> AllProjectsArrayList = new ArrayList<>();

        // Read from the database

        AllProjectsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    AllProjects projects = postSnapshot.getValue(AllProjects.class);
                    AllProjectsArrayList.add(projects);
                }
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = null;
                json = gson.toJson(AllProjectsArrayList);
                editor.putString(PROJECT_LIST, json);
                editor.apply();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SetupLatestTwoPastEvents() {


        ArrayList<PastEvents> TwoPastEvents;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String Json = sharedPreferences.getString(PAST_EVENTS_LIST, null);
        Type type = new TypeToken<ArrayList<PastEvents>>() {
        }.getType();
        TwoPastEvents = gson.fromJson(Json, type);
        Log.d(TAG, "TwoPastEvents : " + TwoPastEvents.size());
        int[] Two = new int[2];
        for (int i = 0, j = 0; i < TwoPastEvents.size(); i++) {
            if (TwoPastEvents.get(i).getYoutube().contains("youtube.com")) {
                Two[j] = i;
                j++;
                if (j == 2) {
                    break;
                }
            }
        }
        PastEvents event = TwoPastEvents.get(Two[0]);
        ImageView Temp = (ImageView) PastEvent_1_Poster.getChildAt(0);
        Picasso.get().load(event.getYoutube()).into((ImageView) Temp);
        TextView EventName = (TextView) PastEvent_1_LinearLayout.getChildAt(0);
        TextView EventType = (TextView) PastEvent_1_LinearLayout.getChildAt(1);
        TextView EventDate = (TextView) PastEvent_1_LinearLayout.getChildAt(2);
        EventName.setText(event.getEventTitle());
        EventType.setText("Webinar");
        EventDate.setText(event.getEventTimings().split(",")[1] + event.getEventTimings().split(",")[2]);

        event = TwoPastEvents.get(Two[1]);
        Temp = (ImageView) PastEvent_2_Poster.getChildAt(0);
        Picasso.get().load(event.getYoutube()).into((ImageView) Temp);
        EventName = (TextView) PastEvent_2_LinearLayout.getChildAt(0);
        EventType = (TextView) PastEvent_2_LinearLayout.getChildAt(1);
        EventDate = (TextView) PastEvent_2_LinearLayout.getChildAt(2);
        EventName.setText(event.getEventTitle());
        EventType.setText("Webinar");
        EventDate.setText(event.getEventTimings().split(",")[1] + event.getEventTimings().split(",")[2]);


        PastEvent_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Event = new Intent(getContext(), EventOverview.class);
                Event.putExtra("AccessedUrl", TwoPastEvents.get(Two[0]).getAccessedUrl());
                startActivity(Event);
            }
        });
        PastEvent_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Event = new Intent(getContext(), EventOverview.class);
                Event.putExtra("AccessedUrl", TwoPastEvents.get(Two[1]).getAccessedUrl());
                startActivity(Event);
            }
        });

    }

    private void SetupAutomaticViewPagerSlider() {

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UpcomingEventsViewPager.getCurrentItem() < upcomingEventAdapter.getCount() - 1) {
                    UpcomingEventsViewPager.setCurrentItem(UpcomingEventsViewPager.getCurrentItem() + 1, true);
                } else {
                    UpcomingEventsViewPager.setCurrentItem(0, true);
                }
                SetupAutomaticViewPagerSlider();
            }
        }, 10000);

    }

    public interface OnButtonClicklistener {
        void onButtonClick();
    }
}