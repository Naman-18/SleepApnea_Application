package com.example.project.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.ui.home.HomeFragment;
import com.google.android.gms.maps.model.Dash;

public class SleepapneanegActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleepapneaneg);
        btn = findViewById(R.id.Goback);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SleepapneanegActivity.this, DashboardFragment.class);
                startActivity(intent);
            }
        });
    }
}