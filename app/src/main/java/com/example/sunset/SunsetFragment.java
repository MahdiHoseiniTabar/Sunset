package com.example.sunset;


import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;


/**
 * A simple {@link Fragment} subclass.
 */
public class SunsetFragment extends Fragment {
    private View sky;
    private View sun;
    private View scene;
    private ObjectAnimator sunsetAnimator;
    private ObjectAnimator backgroundAnimator;
    private ObjectAnimator nightAnimator;
    private AnimatorSet animatorSet;
    public static final String TAG = "SunsetFragment";


    public static SunsetFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SunsetFragment fragment = new SunsetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SunsetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);
        scene = view;
        sky = view.findViewById(R.id.sky);
        sun = view.findViewById(R.id.sun);
        animatorSet = new AnimatorSet();


        scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*new CountDownTimer(3000,500){
                    @Override
                    public void onTick(long l) {
                        Log.i(TAG, "onTick: " + sun.getY());
                    }

                    @Override
                    public void onFinish() {
                        Log.i(TAG, "onFinish: " + sun.getY() +", " + sun.getTop());
                    }
                }.start();*/
                startAnimation();
            }
        });

        return view;
    }

    private void startAnimation() {
        float sunYStart = sun.getTop();
        float sunYEnd = sky.getHeight();
        sunsetAnimator = ObjectAnimator.ofFloat(sun,"y",sunYStart,sunYEnd).setDuration(3000);
        sunsetAnimator.setInterpolator(new AccelerateInterpolator());
        backgroundAnimator = ObjectAnimator.ofInt(sky,
                "backgroundColor",
                getResources().getColor(R.color.blue_sky),
                getResources().getColor(R.color.sunset_sky)).setDuration(1500);
        nightAnimator = ObjectAnimator.ofInt(sky,
                "backgroundColor",
                getResources().getColor(R.color.sunset_sky),
                getResources().getColor(R.color.night_sky)).setDuration(1500);

        backgroundAnimator.setEvaluator(new ArgbEvaluator());
        nightAnimator.setEvaluator(new ArgbEvaluator());

        animatorSet.play(sunsetAnimator)
                .with(backgroundAnimator)
                .before(nightAnimator);

        animatorSet.start();
    }

}
