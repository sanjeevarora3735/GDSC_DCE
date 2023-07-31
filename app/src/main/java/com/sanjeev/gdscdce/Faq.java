package com.sanjeev.gdscdce;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Faq extends AppCompatActivity {


    private LinearLayout Faq1, Faq2, Faq3, Faq4, Faq5, Faq6, Faq7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        Faq1 = findViewById(R.id.Faq_1);
        Faq2 = findViewById(R.id.Faq_2);
        Faq3 = findViewById(R.id.Faq_3);
        Faq4 = findViewById(R.id.Faq_4);
        Faq5 = findViewById(R.id.Faq_5);
        Faq6 = findViewById(R.id.Faq_6);
        Faq7 = findViewById(R.id.Faq_7);

        Faq1.setOnClickListener(v -> {
            if (Faq1.getChildAt(1).getVisibility() == View.VISIBLE) {
                Faq1.getChildAt(1).setVisibility(View.GONE);
                ((ImageView) ((ConstraintLayout) (Faq1.getChildAt(0))).getChildAt(1)).setRotation(0);

            } else {
                Faq1.getChildAt(1).setVisibility(View.VISIBLE);
                ((ImageView) ((ConstraintLayout) (Faq1.getChildAt(0))).getChildAt(1)).setRotation(90);
            }
        });

        Faq2.setOnClickListener(v -> {
            if (Faq2.getChildAt(1).getVisibility() == View.VISIBLE) {
                Faq2.getChildAt(1).setVisibility(View.GONE);
                ((ImageView) ((ConstraintLayout) (Faq2.getChildAt(0))).getChildAt(1)).setRotation(0);

            } else {
                Faq2.getChildAt(1).setVisibility(View.VISIBLE);
                ((ImageView) ((ConstraintLayout) (Faq2.getChildAt(0))).getChildAt(1)).setRotation(90);

            }
        });
        Faq3.setOnClickListener(v -> {
            if (Faq3.getChildAt(1).getVisibility() == View.VISIBLE) {
                Faq3.getChildAt(1).setVisibility(View.GONE);
                ((ImageView) ((ConstraintLayout) (Faq3.getChildAt(0))).getChildAt(1)).setRotation(0);

            } else {
                Faq3.getChildAt(1).setVisibility(View.VISIBLE);
                ((ImageView) ((ConstraintLayout) (Faq3.getChildAt(0))).getChildAt(1)).setRotation(90);

            }
        });
        Faq4.setOnClickListener(v -> {
            if (Faq4.getChildAt(1).getVisibility() == View.VISIBLE) {
                Faq4.getChildAt(1).setVisibility(View.GONE);
                ((ImageView) ((ConstraintLayout) (Faq4.getChildAt(0))).getChildAt(1)).setRotation(0);

            } else {
                Faq4.getChildAt(1).setVisibility(View.VISIBLE);
                ((ImageView) ((ConstraintLayout) (Faq4.getChildAt(0))).getChildAt(1)).setRotation(90);

            }
        });
        Faq5.setOnClickListener(v -> {
            if (Faq5.getChildAt(1).getVisibility() == View.VISIBLE) {
                Faq5.getChildAt(1).setVisibility(View.GONE);
                ((ImageView) ((ConstraintLayout) (Faq5.getChildAt(0))).getChildAt(1)).setRotation(0);

            } else {
                Faq5.getChildAt(1).setVisibility(View.VISIBLE);
                ((ImageView) ((ConstraintLayout) (Faq5.getChildAt(0))).getChildAt(1)).setRotation(90);

            }
        });
        Faq6.setOnClickListener(v -> {
            if (Faq6.getChildAt(1).getVisibility() == View.VISIBLE) {
                Faq6.getChildAt(1).setVisibility(View.GONE);
                ((ImageView) ((ConstraintLayout) (Faq6.getChildAt(0))).getChildAt(1)).setRotation(0);

            } else {
                Faq6.getChildAt(1).setVisibility(View.VISIBLE);
                ((ImageView) ((ConstraintLayout) (Faq6.getChildAt(0))).getChildAt(1)).setRotation(90);

            }
        });
        Faq7.setOnClickListener(v -> {
            if (Faq7.getChildAt(1).getVisibility() == View.VISIBLE) {
                Faq7.getChildAt(1).setVisibility(View.GONE);
                ((ImageView) ((ConstraintLayout) (Faq7.getChildAt(0))).getChildAt(1)).setRotation(0);

            } else {
                Faq7.getChildAt(1).setVisibility(View.VISIBLE);
                ((ImageView) ((ConstraintLayout) (Faq7.getChildAt(0))).getChildAt(1)).setRotation(90);
            }
        });
    }
}