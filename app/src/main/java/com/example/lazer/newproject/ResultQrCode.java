package com.example.lazer.newproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class ResultQrCode extends AppCompatActivity {
    String CurrentUserRegNo,qrCodeResult,portalQrCode,Attendance;
    DatabaseReference dbRef,databaseRefUsers,dbRefAttendance,dbREfResultQrCode,dbRefPortalQRCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_qr_code);
        initView();
    }

    private void initView() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        databaseRefUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        dbREfResultQrCode = FirebaseDatabase.getInstance().getReference().child("QRCODE").child("AfterScanQrCode");
        dbRefPortalQRCode = FirebaseDatabase.getInstance().getReference().child("QRCode").child("qrcode");
        qrCodeResult="";
        portalQrCode="";
        Attendance="0";
        Intent i = getIntent();
        CurrentUserRegNo = i.getStringExtra("REGno");
    }

    private void matchQrCode() {
        dbREfResultQrCode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                qrCodeResult = dataSnapshot.getValue().toString().trim();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ResultQrCode.this, "Data Base Error", Toast.LENGTH_SHORT).show();
            }
        });

        dbRefPortalQRCode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    portalQrCode = dataSnapshot.getValue().toString().trim();
                    QRCodeVerification();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ResultQrCode.this, "Data Base Error", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void QRCodeVerification() {
        if(qrCodeResult.toUpperCase().equals(portalQrCode.toUpperCase()))
        {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            getTotalAttendance();

        }
        else
        {
            Toast.makeText(this, "Not Success", Toast.LENGTH_SHORT).show();
        }
    }
    private void getTotalAttendance() {
        dbRefAttendance = databaseRefUsers.child(CurrentUserRegNo).child("totalLectures");
        dbRefAttendance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.exists())
                    {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ResultQrCode.this, "Data Base Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
