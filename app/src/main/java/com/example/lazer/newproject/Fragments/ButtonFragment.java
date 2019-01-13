package com.example.lazer.newproject.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lazer.newproject.Intro_Page;
import com.example.lazer.newproject.RegisterActivity;
import com.example.lazer.newproject.R;

public class ButtonFragment extends Fragment {
    Button b,loginbtn;
    Activity context;

    public ButtonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_button, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b = view.findViewById(R.id.googlebtn);
        loginbtn = view.findViewById(R.id.fbbtn);
        context =getActivity();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Intro_Page)getActivity()).swipeRight(0);
            }
        });
        setButton();

    }

    public void setButton(){
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
