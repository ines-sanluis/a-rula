package com.example.rula;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TripDetailActivity extends AppCompatActivity implements OnMapReadyCallback{
    private DatabaseReference myDatabase;
    private GoogleMap mMap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String lat = "51.775712";
    private String lon = "19.489471";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        myDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Trip trip = createTrip(intent.getExtras());
        ((MapFragment) getFragmentManager().findFragmentById(R.id.myMap)).getMapAsync(this);

        Button btnRegister = findViewById(R.id.btnRegister);
       /*
        if(String.valueOf(trip.getMaxPeople()) <= 0 ){
            btnRegister.setEnabled(false);
            btnRegister.setBackgroundColor(0xFFEAEAEA);
        }*/

        TextView txtName = findViewById(R.id.txtName);
        txtName.setText(trip.getName());

        TextView txtDate = findViewById(R.id.txtDate);
        txtDate.setText(trip.getDate());

        TextView txtDifficulty = findViewById(R.id.txtDifficulty);
        txtDifficulty.setText(trip.getDifficulty());

       // TextView txtLocation = findViewById(R.id.txtLocation);
        //txtLocation.setText(trip.get());

        TextView txtAvailable = findViewById(R.id.txtAvailable);
        txtAvailable.setText(trip.getMaxPeople());

    }


    private Trip createTrip(Bundle extras){
        String key = extras.getString("key");
        String name = extras.getString("name");
        this.lat = extras.getString("latitude");
        this.lon = extras.getString("longitude");
        Log.w("LOCATION", this.lat);
        Log.w("LOCATION", this.lon);

        String date = extras.getString("date");
        String difficulty = extras.getString("difficulty");
        String maxPeople = extras.getString("maxPeople");
        return new Trip(name, lat, lon, difficulty, date, maxPeople);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng place = new LatLng(Double.parseDouble(this.lat), Double.parseDouble(this.lon));
        mMap.addMarker(new MarkerOptions().position(place).title("Lodz"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
    }
}


