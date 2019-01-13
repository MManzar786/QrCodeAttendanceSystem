package com.example.lazer.newproject;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    DatabaseReference dbRef,dbRefQR;
//    public static final int SUCCESS_RETURN_CODE = 1;
    //String url= "https://qrcodescanner-4a265.firebaseio.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        CameraPermission();
        initView();

    }
    private void initView() {
        dbRef = FirebaseDatabase.getInstance().getReference().child("QRCODE");
    }

    private void CameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }


    @Override
    public void handleResult(Result result)
    {
        dbRef.child("AfterScanQrCode").setValue(result.getText());
        Intent mIntent = new Intent(this, IntermediateActivity.class); //'this' is Activity B
        mIntent.putExtra("FROM_ACTIVITY", "B");
        finish();
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        scannerView.stopCamera();
    }
    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
