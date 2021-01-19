package com.example.materialdesign_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class otp_check extends AppCompatActivity {
    private EditText otp_enter;
    private EditText otp_enter1;
    private Button verify;
    private ProgressBar progressbar;
    String verify_system;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_check);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        otp_enter = (EditText) findViewById(R.id.otp);
        firebaseAuth=firebaseAuth.getInstance();
        verify = (Button) findViewById(R.id.submit);
        progressbar = (ProgressBar) findViewById(R.id.progrss);

        /** USER VALUES FROM SIGNUP PAGE VIA VERIFY PAGE **/
        Intent intent = getIntent();
        String Phoneno = intent.getStringExtra("phoneno");
        sendVerficationCodeUser(Phoneno);
    }
    private void sendVerficationCodeUser(String phoneno) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Toast.makeText(this, phoneno, Toast.LENGTH_SHORT).show();// OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verify_system = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressbar.setVisibility(View.VISIBLE);
                verifycode(code);
                otp_enter.setText(code);
                Toast.makeText(otp_check.this, "I am coming", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(otp_check.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifycode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verify_system, code);
        signinUsercredential(credential);
    }

    private void signinUsercredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            insertinto_database();
                            Intent i=new Intent(getApplicationContext(),ByuerPage.class);
                            i.putExtra("username",getIntent().getStringExtra("username"));
                            startActivity(i);
                        } else {
                            Toast.makeText(otp_check.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void insertinto_database() {
        database= FirebaseDatabase.getInstance();
        ref=database.getReference("Users");
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phoneno = intent.getStringExtra("Phone no");
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        Adapter set=new Adapter(name,username,phoneno,password);
        ref.child(username).setValue(set);
    }
}