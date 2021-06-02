package com.example.project;

import android.content.res.AssetFileDescriptor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    private static String lastSpo2;
    private static String lastBpm;
    private static float low,mid,high;
    private static final String URL = "http://192.168.80.56/api.php";
    Interpreter interpreter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getJSON(URL);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }
    public  void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    LoadIntoView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... voids) {



                try {

                    java.net.URL url = new URL(urlWebService);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    StringBuilder sb = new StringBuilder();


                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    String json;

                    while ((json = bufferedReader.readLine()) != null) {

                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }


        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    void LoadIntoView(String json) throws JSONException{
        JSONArray details = new JSONArray(json);
        //lastSpo2 = "Here";
        low = 101;
        mid = 0;
        high = 0;
        for(int i=0;i<details.length();i++){
            JSONObject obj = details.getJSONObject(i);
            lastSpo2 = obj.getString("Spo2");
            lastBpm = obj.getString("Bpm");
            float currentReading = Float.parseFloat(lastSpo2);
            low = Math.min(low,currentReading);
            mid += currentReading;
            high = Math.max(high,currentReading);
        }
        mid /= details.length();
    }
    public static String getSpo2(){
        return lastSpo2;
    }
    public static String getBpm(){
        return lastBpm;
    }
    public static Float getLow(){return low;}
    public static Float getHigh(){return high;}
    public static Float getMid(){return mid;}



}
