package com.example.rula;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TripDetailActivity extends AppCompatActivity implements OnMapReadyCallback{
    private DatabaseReference myDatabase;
    private GoogleMap mMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private Trip trip = null;

    private Bundle stuff = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        myDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();

        trip = createTrip(intent.getExtras());
        ((MapFragment) getFragmentManager().findFragmentById(R.id.myMap)).getMapAsync(this);
        Button btnRegister = findViewById(R.id.btnReturn);
        if(Integer.parseInt(trip.getAvailable()) <= 0 ){
            btnRegister.setClickable(false);
            btnRegister.setBackgroundColor(0xFFEDEDED);
            btnRegister.setTextColor(0xFFC6C6C6);
            TextView lbl = findViewById(R.id.lblAvailable);
            lbl.setText("No available places");
            TextView txtAvailable = findViewById(R.id.txtAvailable);
            txtAvailable.setVisibility(View.INVISIBLE);
        }

        setDifficultyIcons(trip.getDifficulty());
        TextView txtName = findViewById(R.id.lblTripName);
        txtName.setText(trip.getName());
        TextView txtDate = findViewById(R.id.lblTripDate);
        txtDate.setText(trip.getDate());
        TextView txtLocation = findViewById(R.id.txtLocation);
        txtLocation.setText(trip.getLocationTag());
        TextView txtAvailable = findViewById(R.id.txtAvailable);
        txtAvailable.setText(trip.getAvailable());

    }

    public void onButtonClick (View v) {
        Intent intent = new Intent(v.getContext(), SignUpActivity.class);
        Bundle extras = new Bundle();
        extras.putString("key", this.trip.getKey());
        extras.putString("name", this.trip.getName());
        extras.putString("latitude", this.trip.getLatitude());
        extras.putString("longitude", this.trip.getLongitude());
        extras.putString("date", this.trip.getDate());
        extras.putString("difficulty", this.trip.getDifficulty());
        extras.putString("available", this.trip.getAvailable());
        extras.putString("locationTag", this.trip.getLocationTag());
        intent.putExtras(extras);
        intent.putExtras(stuff);
        startActivity(intent);
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
        String latitude = extras.getString("latitude");
        String longitude = extras.getString("longitude");
        String locationTag = extras.getString("locationTag");
        String date = extras.getString("date");
        String difficulty = extras.getString("difficulty");
        String available = extras.getString("available");
        Location location = new Location(latitude, longitude, locationTag);
        return new Trip(key, name, location, difficulty, date, available);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Double lat = Double.parseDouble(this.trip.getLatitude());
        Double lon = Double.parseDouble(this.trip.getLongitude());
        LatLng place = new LatLng(lat, lon);
        Marker marker = mMap.addMarker(new MarkerOptions().position(place).title("Meeting point"));
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        mMap.setMinZoomPreference(10);
        mMap.setMaxZoomPreference(100);
    }
}


