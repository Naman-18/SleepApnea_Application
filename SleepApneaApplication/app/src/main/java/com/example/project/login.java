package com.example.project;


import android.content.Intent;
import android.content.SharedPreferences;
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


public class login extends AppCompatActivity {

    EditText username,password;
    Button login,register;
    DBhelper DB;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME= "name";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.username1);
        password = (EditText)findViewById(R.id.password1);
        login = (Button)findViewById(R.id.loginbutton);
        register=(Button)findViewById(R.id.register);
        DB= new DBhelper(this);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user= username.getText().toString();
                String pass= password.getText().toString();
                if(user.equals(" ") || pass.equals(" ")){
                    Toast.makeText(login.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkuserpass= DB.checkusernamepassword(user,pass);
                    if(checkuserpass==true){
                        Toast.makeText(login.this, "SignIn Successfull", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(KEY_NAME,username.getText().toString());
                        editor.apply();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    }
                    else
                        Toast.makeText(login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                }


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(getApplicationContext(),signup.class);
                startActivity(intent);
            }
        });




    }
}