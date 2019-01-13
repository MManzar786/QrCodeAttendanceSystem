package com.example.lazer.newproject;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

public class Intro_Page extends AppCompatActivity {

    ViewPager vp;
    MyViewPagerAdapter mvpa;
    DatabaseReference databaseReference;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro__page);
        initViews();
        setAdapter();

    }

    private void setAdapter() {
        mvpa = new MyViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mvpa);

    }

    private void initViews() {
        vp = findViewById(R.id.myvp);
    }

    public void swipeRight(int x) {
        if (x < 1) {
            vp.setCurrentItem(x + 1);
        }
    }
}



