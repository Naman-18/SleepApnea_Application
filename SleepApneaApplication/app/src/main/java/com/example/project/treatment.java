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


public class treatment extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        final TextView textView1 =findViewById(R.id.textView1);
        textView1.setText("\n1.Self-care: Physical exercise and Weight loss.\n\n"+"2.Supportive care: Continuous positive airway pressure (CPAP) and Airway management.\n\n"+"3.Surgery:Tonsillectomy, Adenoid removal and Palatoplasty.\n");

        ImageView image1=(ImageView)findViewById(R.id.imageView1);
        image1.setImageResource(R.mipmap.symp3);


    }
}