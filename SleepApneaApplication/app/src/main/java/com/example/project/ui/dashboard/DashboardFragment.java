package com.example.project.ui.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.MainActivity;
import com.example.project.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class DashboardFragment extends Fragment  {

    private Context mContext;
    public void FirstFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    Interpreter interpreter;
    String SPO2,BPM;
    Float low,mid,high;
    private DashboardViewModel dashboardViewModel;
    private EditText name, age, gender, diabetic, breathing;
    private TextView userCountry, userState, userCity,bpm,readings,textView1;
    Button predict,btloc;
    FusedLocationProviderClient client;
    //private LocationManager locationManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView1 = root.findViewById(R.id.textView1);
        dashboardViewModel.getText1().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView1.setText(s);
            }
        });
        final TextView textView0 = root.findViewById(R.id.textView0);
        dashboardViewModel.getText0().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView0.setText(s);
            }
        });

        userState = root.findViewById(R.id.userstate);
        userCity = root.findViewById(R.id.usercity);
        name = root.findViewById(R.id.Name);
        age = root.findViewById(R.id.Age);
        gender = root.findViewById(R.id.Gender);
        diabetic = root.findViewById(R.id.Pulse);
        breathing = root.findViewById(R.id.breathing);
        predict = root.findViewById(R.id.button);
        btloc= root.findViewById(R.id.locbutton);

        readings = root.findViewById(R.id.readings);

        SPO2 = MainActivity.getSpo2();
        BPM = MainActivity.getBpm();
        low = MainActivity.getLow();
        mid = MainActivity.getMid();
        high = MainActivity.getHigh();
        readings.setText("                      Current Oxygen Levels : " + SPO2 + "\n" + "                        Current BPM Levels : " + BPM);
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        final float input[][] = new float[1][7];
        input[0][0] = low;input[0][1]=mid;input[0][2]=high;
        if ((gender.getText().toString()=="M")) input[0][3] = 1;
        else input[0][3] = 0;
        if(diabetic.getText().toString()=="T") input[0][4] = 1;
        else input[0][4] = 0;
        input[0][5] = 0;
        if(breathing.getText().toString() == "T")input[0][6] = 1;
        else input[0][6] = 0;


        btloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){

                    getCurrentLocation();
                }else{

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                ,Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }
            }
        });
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    MappedByteBuffer tfliteModel = FileUtil.loadMappedFile(mContext, "MOD.tflite");
                    Interpreter.Options options = new Interpreter.Options();
                    Interpreter tflite = new Interpreter(tfliteModel, options);
                    float [][] output = new float[1][1];
                    tflite.run(input,output);
                    if(output[0][0] > 0.5){
                        Intent intent = new Intent(mContext,SleepApneaPos.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(mContext,SleepapneanegActivity.class);
                        startActivity(intent);
                    }
                }
                catch(IOException e){

                    Log.e("tflite Support", "Error reading model", e);
                }
            }
        });
        return root;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode ==100 && (grantResults.length>0) &&
            (grantResults[0]+grantResults[1] == PackageManager.PERMISSION_GRANTED)){
        getCurrentLocation();
    }else{
        Toast.makeText(getActivity(),"Permission Denied",Toast.LENGTH_SHORT).show();
    }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager=(LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location=task.getResult();
                    if(location!=null){
                        try {

                            Geocoder geocoder = new Geocoder(getContext(),Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude()-8.7781743, location.getLongitude()+199.456635, 1);
                            userState.setText("Latitude:"+String.valueOf(location.getLatitude()-8.7781743)+",Longitude:"+String.valueOf(location.getLongitude()+199.456635));
                            userCity.setText(addresses.get(0).getLocality()+","+addresses.get(0).getAdminArea()+","+addresses.get(0).getCountryName());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else{
                        LocationRequest locationRequest=new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback=new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                    Location location1=locationResult.getLastLocation();
                                try {

                                    Geocoder geocoder = new Geocoder(getContext(),Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location1.getLatitude()-8.7781743, location1.getLongitude()+199.456635, 1);
                                    userState.setText("Latitude:"+String.valueOf(location1.getLatitude()-8.7781743)+",Longitude:"+String.valueOf(location1.getLongitude()+199.456635));
                                    userCity.setText(addresses.get(0).getLocality()+","+addresses.get(0).getAdminArea()+","+addresses.get(0).getCountryName());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        client.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());

                    }

                }
            });
        }else{
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Enable GPS service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    }).setNegativeButton("Cancel", null)
                    .show();
        }
    }

    /*private void getLocation() {
        try {
            locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, (LocationListener)this.getContext());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationIsEnablesOrNot(){
        LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        boolean gpsEnabled=false;
        boolean networkEnabled = false;

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Enable GPS service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel", null)
                    .show();
        }

    }

        private void grantPermission() {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
        }



    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {

            Geocoder geocoder = new Geocoder(getContext(),Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            userCountry.setText(addresses.get(0).getCountryName());
            userState.setText(addresses.get(0).getAdminArea());
            userCity.setText(addresses.get(0).getLocality());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/


    public float doInterface(){

//        float [][] output = new float[1][1];
//        interpreter.run(toModel,output);
//        return output[0][0];
        return Float.parseFloat("1.0");
    }




}