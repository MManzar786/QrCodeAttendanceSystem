package com.example.lazer.newproject;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.lazer.newproject.ModelClasses.Register;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference dbRef, dbUsers, dbRegNo, dbEmail;
    int flag = 0;
    FrameLayout frameLayout;
    Pattern p1, p2, p3;

    int count;
    String pass;
    EditText name, password, regNo, email, con_pass;
    FloatingActionButton registerBtn, btn;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Environment.getDataDirectory();
        setContentView(R.layout.activity_register);
        initView();
//        Validation();
       // CheckButton();

        setListener();
    }

    private void setListener() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateName())
                {
                    if(emailValidation())
                    {
                        if(passValidation())
                        {
                            Registeration();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Please Check Out your form", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Please Check Out your form", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(RegisterActivity.this, "Please Check Out your form", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Registeration() {
        final Register register = new Register(name.getText().toString(), regNo.getText().toString(), email.getText().toString(), password.getText().toString(), 0);
        final DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("Users");
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.hasChild(regNo.getText().toString())) {
                            dbRef.child("Users").child(regNo.getText().toString()).setValue(register);
                            Toast.makeText(RegisterActivity.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (dataSnapshot.hasChild(regNo.getText().toString())) {
                            Snackbar.make(frameLayout,"Already Registered",Snackbar.LENGTH_LONG).show();
                            emptyFields();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(RegisterActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
                    }

                });
            }

        });
    }

    private void emptyFields() {
        name.setText("");
        regNo.setText("");
        email.setText("");
        password.setText("");
        con_pass.setText("");
    }

    public void initView() {
        name = findViewById(R.id.name);
        regNo = findViewById(R.id.rollno);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        registerBtn = findViewById(R.id.signupbtn);
        con_pass = findViewById(R.id.cpass);
        dbRef = FirebaseDatabase.getInstance().getReference();
        frameLayout = findViewById(R.id.snackbar);
    }

//    public void CheckButton() {
//        if (count >= 5) {
//            registerBtn.setEnabled(true);
//        } else {
//            registerBtn.setEnabled(false);
//        }
//    }

    public boolean passValidation() {
        if(password.getText().toString().trim().equals(con_pass.getText().toString().trim()))
        {
            return true;
        }
        else
        {
            password.setError("password not matches");
            password.setText("");
            con_pass.setText("");
            return false;
        }
    }
    public boolean validateName() {
        Pattern pattern = Pattern.compile(new String("^[a-zA-Z\\s]*$"));
        Matcher matcher = pattern.matcher(name.getText().toString().trim());
        if (matcher.matches()) {
            return true;
        } else {
            name.setText("");
            name.setError("Invalid Name");
            return false;
        }
    }
    public boolean emailValidation()
    {
        if(isEmailValid())
        {
            return true;
        }
        else
        {
            email.setError("Invalid Email");
            email.setText("");
            return false;
        }
    }
    boolean isEmailValid() {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches();
    }
    }
