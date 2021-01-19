package com.example.materialdesign_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.rpc.Help;

public class SignIn extends AppCompatActivity {
    private Button newaccount;
    private Button login;
    private EditText username;
    private EditText password;
    private TextInputLayout userL,passL;
    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        newaccount=(Button)findViewById(R.id.NewAccount);
        login=(Button)findViewById(R.id.login);
                        /** Input id's **/
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
                        /** Layout id's **/
        userL=findViewById(R.id.UsernameL);
        passL=findViewById(R.id.passwordL);
        newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),Signup.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                if(validate(user,pass))
                {
                    check_database(user,pass);
                }
            }
        });
    }

    private boolean check_database(String user,String pass) {
        database=FirebaseDatabase.getInstance();
        ref=database.getReference("Users");
        Query check=ref.orderByChild("username").equalTo(user);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String password=snapshot.child(user).child("password").getValue(String.class);

                    if(password.equals(pass)){
                        Toast.makeText(SignIn.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getBaseContext(),ByuerPage.class);
                        startActivity(intent);
                    }else{
                        passL.setError("Password not match");
                    }
                }else{
                    userL.setError("No user found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return false;
    }

    private boolean validate(String user,String pass) {
        int count=0;
        if(user.matches(""))
        {
            count+=1;
            userL.setError("Enter the Username");
        }else{userL.setError(null);}

        if(pass.matches(""))
        {
            count+=1;
            passL.setError("Enter the Password");
        }else{passL.setError(null);}

        if(count==0){
            return true;
        }else{
            return  false;
        }
    }
}
