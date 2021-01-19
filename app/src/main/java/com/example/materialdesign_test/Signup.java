package com.example.materialdesign_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.SigningInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

public class Signup extends AppCompatActivity {
    private Button Signup;
    private EditText Phno;
    private EditText name,username,password;
    private TextInputLayout Fullname,Username,PhoneNo,Password;
    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                                            /** Text field id's **/
        Signup=(Button)findViewById(R.id.Signup);
        name=(EditText)findViewById(R.id.Fullname);
        username=findViewById(R.id.Username);
        password=findViewById(R.id.Password);
        Phno=(EditText)findViewById(R.id.PhoneNo);

                                          /**Layout id's**/
        Fullname=findViewById(R.id.FullnameL);
        Username=findViewById(R.id.UsernameL);
        PhoneNo=findViewById(R.id.PhoneNoL);
        Password=findViewById(R.id.passwordL);

                                        /** on click action**/
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PhoneNo=Phno.getText().toString();
                String Name=name.getText().toString();
                String Username=username.getText().toString();
                String Password=password.getText().toString();
                if(validate(Name,Username,PhoneNo,Password))
                {
//                    insertinto_database();

                        Intent i=new Intent(getBaseContext(),Phoneno_verify.class);
                        i.putExtra("Phone no",PhoneNo);
                        i.putExtra("name",Name);
                        i.putExtra("username",Username);
                        i.putExtra("password",Password);
                        startActivity(i);

                }
            }
        });
    }

    private void insertinto_database() {
        database= FirebaseDatabase.getInstance();
        ref=database.getReference("Users");
        Intent intent = getIntent();
        String names= name.getText().toString();
        String phoneno = Phno.getText().toString();
        String username1 =username.getText().toString();
        String password1 =password.getText().toString();
        Adapters Details= new Adapters(names,username1,phoneno,password1);
        ref.child("Dhinesh315").setValue(Details);
        Toast.makeText(this, "SignUp Successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean validate(String name,String username, String phno,String pass) {
        int count=0;
        if(name.matches("")){
            Fullname.setError("Enter the name");
            count=count+1;
        }else{Fullname.setError(null);}
        if(pass.matches("")){
            Password.setError("enter the password");
            count=count+1;
        }else{Password.setError(null);}
        if(username.matches("")){
            Username.setError("Enter the username");
            count=count+1;
        }else{Username.setError(null);}
        if(phno.matches("")){
            PhoneNo.setError("Enter the Phoneno");
            count=count+1;
        }
        else if(phno.length()>10 || phno.length()<10){
            PhoneNo.setError("Invalid Password");
            count=count+1;
        }
        else{
            char arr[]=phno.toCharArray();
            int count1=0;
            for(int i=0;i<phno.length();i++)
            {
                if(Character.isDigit(arr[i])){count1+=1;}
            }
            if(count1!=10) {
                count += 1;
                PhoneNo.setError("Invalid Phoneno");
            }else{
                PhoneNo.setError(null);
            }
        }
        if(count==0)
        {
            return true;
        }
        else{
            return false;
        }

    }
}