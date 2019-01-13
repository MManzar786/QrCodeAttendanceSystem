package com.example.lazer.newproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IntermediateActivity extends AppCompatActivity{
    FloatingActionButton scanbtn;
    FrameLayout f;
    TextView welcome,RegNo;
    String previousActivity;
    String CurrentUserRegNo,qrCodeResult,portalQrCode,Attendance;
    DatabaseReference databaseRefUsers,dbRefCurrentUserName,dbRefAttendance,dbREfResultQrCode,dbRefPortalQRCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate);
        initViews();
        setListener();
        CurrentUser();
    }
    private void initViews() {
        scanbtn = findViewById(R.id.scanbtn);
        welcome = findViewById(R.id.welcome);
        Intent i = getIntent();
        CurrentUserRegNo = i.getStringExtra("REGNO");
        databaseRefUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        dbREfResultQrCode = FirebaseDatabase.getInstance().getReference().child("QRCODE").child("AfterScanQrCode");
        dbRefPortalQRCode = FirebaseDatabase.getInstance().getReference().child("QRCode").child("qrcode");
        RegNo = findViewById(R.id.RegnoTV);
        RegNo.append(" " + CurrentUserRegNo);
        qrCodeResult="";
        portalQrCode="";
        Attendance="0";
        Intent ib = new Intent(IntermediateActivity.this,ResultQrCode.class);
        ib.putExtra("REGno",CurrentUserRegNo);
        f = findViewById(R.id.sb);
        Intent mIntent = getIntent();
        previousActivity= mIntent.getStringExtra("FROM_ACTIVITY").trim();
    }

    private void matchQrCode() {
        dbREfResultQrCode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                qrCodeResult = dataSnapshot.getValue().toString().trim();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(IntermediateActivity.this, "Data Base Error", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(IntermediateActivity.this, "Data Base Error", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void QRCodeVerification() {
        if(qrCodeResult.toUpperCase().equals(portalQrCode.toUpperCase()))
        {
            if(qrCodeResult.equals(""))
            {
            }
            else
            {
                Snackbar.make(f,"Congratulation Attendance Marked",Snackbar.LENGTH_LONG).show();
            }
        }
        else
        {
            Snackbar.make(f,"Qr Code is wrong",Snackbar.LENGTH_LONG).show();
        }
    }

    private void CurrentUser() {
        dbRefCurrentUserName = databaseRefUsers.child(CurrentUserRegNo).child("name");
        dbRefCurrentUserName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    welcome.append(" " + dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(IntermediateActivity.this, "DataBase error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setListener(){
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntermediateActivity.this, ScannerActivity.class );
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        if(previousActivity.equals("B"))
        {
            matchQrCode();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        previousActivity="B";
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }
}
