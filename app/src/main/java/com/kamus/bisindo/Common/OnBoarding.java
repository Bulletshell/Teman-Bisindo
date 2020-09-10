package com.kamus.bisindo.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kamus.bisindo.Dashboard;
import com.kamus.bisindo.MainActivity;
import com.kamus.bisindo.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class OnBoarding extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tab_indicator;
    Button btn_next;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //activity fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //open activity check
        if (restorePrefData()){
            Intent dashboard = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(dashboard);
            finish();
        }

        setContentView(R.layout.activity_on_boarding);

        //views
        btn_next = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tab_indicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        // fill list screen

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Selamat Datang", "", R.drawable.onboard1));
        mList.add(new ScreenItem("Kemudahan Belajar", "Teman BISINDO akan membantu kamu dalam mengenal Bahasa Isyarat.", R.drawable.onboard2));
        mList.add(new ScreenItem("Ayo Mulai", "", R.drawable.onboard3));


        //Setup ViewPager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //Setup TabLayout with ViewPager
        tab_indicator.setupWithViewPager(screenPager);

        //next Button click Listener
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size()) {
                    // TODO : show the GETSTARTED Button to hide the indicator and next button
                    loadLastScreen();
                }
            }
        });

        //tabLayout add change listener
        tab_indicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //GET Started button click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Main activity
                Intent dashboard = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(dashboard);

                //
                //preference data
                savePrefData();
                finish();

            }
        });

    }

    private boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        Boolean isIntroOpenedBefore = pref.getBoolean("isIntroOpened", false);
        return isIntroOpenedBefore;
    }

    private void savePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }

    // show the GETSTARTED Button to hide the indicator and next button
        private void loadLastScreen(){
        btn_next.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tab_indicator.setVisibility(View.INVISIBLE);

        //TODO : ADD an animation to the getStarted button
        //Animation setup
            btnGetStarted.setAnimation(btnAnim);





    }
}