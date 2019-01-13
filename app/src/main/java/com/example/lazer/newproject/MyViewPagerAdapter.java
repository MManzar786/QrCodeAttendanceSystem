package com.example.lazer.newproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lazer.newproject.Fragments.ButtonFragment;
import com.example.lazer.newproject.Fragments.LoginFragment;

class MyViewPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT=2;
    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return new ButtonFragment();
            case 1:
                return new LoginFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
