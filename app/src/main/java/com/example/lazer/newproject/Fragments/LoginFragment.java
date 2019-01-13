package com.example.lazer.newproject.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lazer.newproject.IntermediateActivity;
import com.example.lazer.newproject.ModelClasses.Register;
import com.example.lazer.newproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {
    Button lgnBtn;
    String REGNO,PASSWORD;
    EditText lgnRegno,lgnPassword;
 SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.lazer.newproject";
    private DatabaseReference databaseReference,dbRefUsers,dbRefRegNo;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setListener();

        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
            lgnRegno.setText(mPreferences.getString("REGNO", " "));
            lgnPassword.setText(mPreferences.getString("PASSWORD", " "));
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("REGNO", lgnRegno.getText().toString());
        preferencesEditor.putString("PASSWORD", lgnPassword.getText().toString());
        preferencesEditor.apply();
    }

    private void setListener() {
        final Register register = new Register(lgnRegno.getText().toString(),lgnPassword.getText().toString());
        lgnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUser();
            }
        });
    }



    private void initView(View view) {
        lgnBtn = view.findViewById(R.id.loginBtn);
        lgnRegno = view.findViewById(R.id.loginRegNo);
        lgnPassword = view.findViewById(R.id.loginPass);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    private void CheckUser()
    {
        dbRefRegNo = databaseReference.child("Users").child(lgnRegno.getText().toString()).child("password");
        dbRefRegNo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(lgnPassword.getText().toString().trim().equals(dataSnapshot.getValue().toString().trim()))
                    {
                        Intent intent = new Intent(getActivity(),IntermediateActivity.class);
                        intent.putExtra("REGNO",lgnRegno.getText().toString());
                        intent.putExtra("FROM_ACTIVITY", "A");
                        startActivity(intent);
                    }
                    else
                    {
                        lgnPassword.setText("");
                        lgnPassword.setError("Invalid Password");
                    }
                }
                else
                {
                    lgnRegno.setText("");
                    lgnRegno.setError("Invalid Registration No");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "DataBase Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
