package com.example.project;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;


public class symptoms extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        final TextView textView1 =findViewById(R.id.textView1);
        textView1.setText("The signs and symptoms of obstructive and central sleep apnea overlap, sometimes making it difficult to determine which type you have." +
                "Loud snoring can indicate a potentially serious problem, but not everyone who has sleep apnea snores. Talk to your doctor if you have signs or symptoms of sleep apnea. ");

        ImageView image1=(ImageView)findViewById(R.id.imageView1);
        image1.setImageResource(R.mipmap.symp1);


    }
}