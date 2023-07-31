package com.sanjeev.gdscdce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.sanjeev.gdscdce.Hack_o_Relay;
import com.sanjeev.gdscdce.R;
import com.squareup.picasso.Picasso;

public class UpcomingEventAdapter extends PagerAdapter {

    Context context;
    String[] URLS, RSVP;

    public UpcomingEventAdapter(Context context, String[] URLS, String[] RSVP) {
        this.context = context;
        this.URLS = URLS;
        this.RSVP = RSVP;
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

        if(RSVP[position].contains("https://forms.gle/ne2ikZBfmNcoVwLR8")){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent GoogleForm = new Intent(context, Hack_o_Relay.class);
                    GoogleForm.putExtra("RSVP",RSVP[position]);
                    context.startActivity(GoogleForm);
                }
            });
        }else {
            imageView.setOnClickListener(view1 -> {
                Uri uri = Uri.parse(RSVP[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            });
        }

        Picasso.get().load(URLS[position]).into(imageView);

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
