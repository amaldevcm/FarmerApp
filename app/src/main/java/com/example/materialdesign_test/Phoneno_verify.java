package com.example.materialdesign_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class Phoneno_verify extends AppCompatActivity {
    Button verify;
    EditText phoneno;
    CountryCodePicker ccode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneno_verify);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        verify=(Button)findViewById(R.id.Verify);
        phoneno=(EditText)findViewById(R.id.phno);
        ccode=(CountryCodePicker)findViewById(R.id.ccp);
        Intent intent=getIntent();
        String Name=intent.getStringExtra("name");
        String Phoneno=intent.getStringExtra("Phone no");
        String Username=intent.getStringExtra("username");
        String Password=intent.getStringExtra("password");
        phoneno.setText(Phoneno);
        ccode.registerCarrierNumberEditText(phoneno);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),otp_check.class);
                intent.putExtra("phoneno",ccode.getFullNumberWithPlus().replace(" ",""));
                intent.putExtra("name",Name);
                intent.putExtra("username",Username);
                intent.putExtra("password",Password);
                startActivity(intent);
            }
        });
    }
}