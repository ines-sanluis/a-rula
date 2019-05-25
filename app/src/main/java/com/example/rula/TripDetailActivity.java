package com.example.rula;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TripDetailActivity extends AppCompatActivity implements OnMapReadyCallback{
    private DatabaseReference myDatabase;
    private GoogleMap mMap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String latitude = null;
    private String longitude = null;
    private String locationTag = null;

    private Bundle stuff = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        myDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Trip trip = createTrip(intent.getExtras());
        ((MapFragment) getFragmentManager().findFragmentById(R.id.myMap)).getMapAsync(this);
        Button btnRegister = findViewById(R.id.btnRegister);

        if(Integer.parseInt(trip.getMaxPeople()) <= 0 ){
            btnRegister.setVisibility(View.INVISIBLE);
            TextView lbl = findViewById(R.id.lblAvailable);
            lbl.setText("No available places");
            TextView txtAvailable = findViewById(R.id.txtAvailable);
            txtAvailable.setVisibility(View.INVISIBLE);
        }

        //test stuff
        /*
        TextView txt1 = findViewById(R.id.txt1);
        TextView txt2 = findViewById(R.id.txt2);

        txt1.setText("This is some text for the title");

        StringBuilder strBuilder = new StringBuilder();
        String msg = "Some message to append";

        for(int i = 0; i < 200; i++)
            strBuilder.append(msg);

        txt2.setText(strBuilder.toString());
        */ //end of test stuff

        setDifficultyIcons(trip.getDifficulty());
        TextView txtName = findViewById(R.id.txtName);
        txtName.setText(trip.getName());
        TextView txtDate = findViewById(R.id.txtDate);
        txtDate.setText(trip.getDate());
        TextView txtLocation = findViewById(R.id.txtLocation);
        txtLocation.setText(trip.getLocationTag());
        TextView txtAvailable = findViewById(R.id.txtAvailable);
        txtAvailable.setText(trip.getMaxPeople());

        stuff.putString("name", trip.getName());
        stuff.putString("date", trip.getDate());
    }

    public void onButtonClick (View v) {
        Log.w("TAG", "pre switch activity");
        Intent switchActivity = new Intent(getBaseContext(), SignUpActivity.class);
        switchActivity.putExtras(stuff);
        startActivity(switchActivity);
    }

    private void setDifficultyIcons(String difficulty){
        ImageView img = null;
        switch(difficulty){
            case "0":
                img = findViewById(R.id.dif1);
                img.setImageResource(android.R.drawable.presence_invisible);
                img = findViewById(R.id.dif2);
                img.setImageResource(android.R.drawable.presence_invisible);
                img = findViewById(R.id.dif3);
                img.setImageResource(android.R.drawable.presence_invisible);
                img = findViewById(R.id.dif4);
                img.setImageResource(android.R.drawable.presence_invisible);
                img = findViewById(R.id.dif5);
                img.setImageResource(android.R.drawable.presence_invisible);
                break;
            case "1":
                img = findViewById(R.id.dif1);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif2);
                img.setImageResource(android.R.drawable.presence_invisible);
                img = findViewById(R.id.dif3);
                img.setImageResource(android.R.drawable.presence_invisible);
                img = findViewById(R.id.dif4);
                img.setImageResource(android.R.drawable.presence_invisible);
                img = findViewById(R.id.dif5);
                img.setImageResource(android.R.drawable.presence_invisible);
                break;
            case "2":
                img = findViewById(R.id.dif1);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif2);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif3);
                img.setImageResource(android.R.drawable.presence_invisible);
                img = findViewById(R.id.dif4);
                img.setImageResource(android.R.drawable.presence_invisible);
                img = findViewById(R.id.dif5);
                img.setImageResource(android.R.drawable.presence_invisible);
                break;
            case "3":
                img = findViewById(R.id.dif1);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif2);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif3);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif4);
                img.setImageResource(android.R.drawable.presence_invisible);
                img = findViewById(R.id.dif5);
                img.setImageResource(android.R.drawable.presence_invisible);
                break;
            case "4":
                img = findViewById(R.id.dif1);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif2);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif3);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif4);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif5);
                img.setImageResource(android.R.drawable.presence_invisible);
                break;
            case "5":
                img = findViewById(R.id.dif1);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif2);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif3);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif4);
                img.setImageResource(android.R.drawable.presence_online);
                img = findViewById(R.id.dif5);
                img.setImageResource(android.R.drawable.presence_online);
                break;
        }
    }

    private Trip createTrip(Bundle extras){
        String key = extras.getString("key");
        String name = extras.getString("name");
        this.latitude = extras.getString("latitude");
        this.longitude = extras.getString("longitude");
        this.locationTag = extras.getString("locationTag");
        String date = extras.getString("date");
        String difficulty = extras.getString("difficulty");
        String maxPeople = extras.getString("maxPeople");
        return new Trip(name, latitude, longitude, locationTag, difficulty, date, maxPeople);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Double lat = Double.parseDouble(this.latitude);
        Double lon = Double.parseDouble(this.longitude);
        LatLng place = new LatLng(lat, lon);
        Marker marker = mMap.addMarker(new MarkerOptions().position(place).title("Meeting point"));
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        mMap.setMinZoomPreference(10);
        mMap.setMaxZoomPreference(100);
    }
}


