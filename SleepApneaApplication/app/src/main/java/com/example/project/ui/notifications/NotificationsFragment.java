package com.example.project.ui.notifications;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;
import com.example.project.intro;
import com.example.project.login;
import com.example.project.signup;
import com.example.project.ui.home.HomeFragment;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private static final int GALLERY_REQUEST_CODE=123;
    TextView textView1,textView2;
    ImageView image1;
    Button logout;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME= "name";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        textView1 = root.findViewById(R.id.textView1);
        notificationsViewModel.getText1().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView1.setText(s);
            }
        });

        sharedPreferences=this.getActivity().getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String username=sharedPreferences.getString(KEY_NAME,null);


        textView2 = root.findViewById(R.id.textView2);
        /*notificationsViewModel.getText2().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView2.setText(s);
            }
        });*/
        textView2.setText(username);

        image1=(ImageView)root.findViewById(R.id.imageView1);
        image1.setImageResource(R.mipmap.profile);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Pick and Image"),GALLERY_REQUEST_CODE);
            }
        });



        logout=root.findViewById(R.id.bt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(NotificationsFragment.this.getActivity(), "Succesfully Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NotificationsFragment.this.getActivity(), login.class));
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            Uri imageData=data.getData();
            image1.setImageURI(imageData);
        }
    }
}