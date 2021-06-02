package com.example.project;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;


public class signup extends AppCompatActivity {

    EditText username,password,repassword;
    Button signup,signin;
    DBhelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        repassword = (EditText)findViewById(R.id.repassword);
        signup = (Button)findViewById(R.id.signupbutton);
        signin = (Button)findViewById(R.id.signinbutton);
        DB= new DBhelper(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user= username.getText().toString();
                String pass= password.getText().toString();
                String repass= repassword.getText().toString();

                if(user.equals(" ")||pass.equals(" ")||repass.equals(" "))
                    Toast.makeText(signup.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkusername(user);
                        if(checkuser==false) {
                            Boolean insert = DB.insertData(user, pass);
                            if (insert == true) {
                                Toast.makeText(signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                            }
                        }
                        else
                            Toast.makeText(signup.this, "User Already Exist, Please Sigin", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(signup.this, "Both Passwords does not match", Toast.LENGTH_SHORT).show();

                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });



    }
}