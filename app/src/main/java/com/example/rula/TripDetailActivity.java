package com.example.rula;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class TripDetailActivity extends AppCompatActivity implements OnMapReadyCallback{
    private DatabaseReference tripReference;
    private GoogleMap mMap;
    private Trip trip = null;
    private Bundle extras = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        Intent intent = getIntent();
        extras = intent.getExtras();
        Location location = new Location(extras.getString("latitude"), extras.getString("longitude"), extras.getString("locationTag"));
        trip = new Trip(extras.getString("key"), location, extras.getString("maxPeople"), extras.getString("nBookings"));
        checkBookButton();

        tripReference = FirebaseDatabase.getInstance().getReference().child(trip.getKey());
        ((MapFragment) getFragmentManager().findFragmentById(R.id.myMap)).getMapAsync(this);

        tripReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateTrip(dataSnapshot);
                updateFields(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateTrip(dataSnapshot);
                updateFields(dataSnapshot.getKey());
                if(dataSnapshot.getKey().equals("maxPeople") || dataSnapshot.getKey().equals("bookings")) Toast.makeText(getBaseContext(), "The available places have just been updated", Toast.LENGTH_LONG).show();
                else Toast.makeText(getBaseContext(), "The "+dataSnapshot.getKey()+" has just been updated", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getBaseContext(), "We are sorry. This trip has just been cancelled", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateTrip(DataSnapshot dataSnapshot){
        switch(dataSnapshot.getKey()){
            case "location":
                this.trip.setLatitude((String) dataSnapshot.child("lat").getValue());
                this.trip.setLongitude((String) dataSnapshot.child("lon").getValue());
                this.trip.setLocationTag((String) dataSnapshot.child("tag").getValue());
                break;
            case "date": this.trip.setDate((String) dataSnapshot.getValue()); break;
            case "difficulty":  this.trip.setDifficulty((String) dataSnapshot.getValue()); break;
            case "name":  this.trip.setName((String) dataSnapshot.getValue()); break;
            case "bookings":
                long nBookings = 0;
                if(dataSnapshot.getChildrenCount() != 0) {
                    for (DataSnapshot reservation : dataSnapshot.getChildren()) {
                        nBookings += Long.parseLong((String) reservation.child("numOfPeople").getValue());
                    }
                }
                this.trip.setNumberBookings(Long.toString(nBookings));  break;
            case "maxPeople":  this.trip.setMaxPeople((String) dataSnapshot.getValue()); break;
        }
    }

    public void onButtonClick (View v) {
        Intent intent = new Intent(v.getContext(), SignUpActivity.class);
        Bundle extras = new Bundle();
        extras.putString("key", trip.getKey());
        extras.putString("name", trip.getName());
        extras.putString("latitude", trip.getLatitude());
        extras.putString("longitude", trip.getLongitude());
        extras.putString("date", trip.getDate());
        extras.putString("difficulty", trip.getDifficulty());
        extras.putString("maxPeople", trip.getMaxPeople());
        extras.putString("nBookings", trip.getNumberBookings());
        extras.putString("locationTag", trip.getLocationTag());
        intent.putExtras(extras);
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

    private void updateFields(String key){
        TextView txtAvailable = null;
        switch(key){
            case "location":
                TextView txtLocation = findViewById(R.id.txtLocation);
                txtLocation.setText(trip.getLocationTag());
                updateMap();
                break;
            case "date":
                TextView txtDate = findViewById(R.id.lblTripDate);
                txtDate.setText(trip.getDate());
                break;
            case "difficulty":
                setDifficultyIcons(trip.getDifficulty());
                break;
            case "name":
                TextView txtName = findViewById(R.id.lblTripName);
                txtName.setText(trip.getName());
                break;
            case "bookings":
                txtAvailable = findViewById(R.id.txtAvailable);
                txtAvailable.setText(trip.getAvailable());
                checkBookButton();
                break;
            case "maxPeople":
                txtAvailable = findViewById(R.id.txtAvailable);
                txtAvailable.setText(trip.getAvailable());
                checkBookButton();
                break;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateMap();
    }

    private void updateMap(){
        mMap.clear();
        Double lat = Double.parseDouble(this.trip.getLatitude());
        Double lon = Double.parseDouble(this.trip.getLongitude());
        LatLng place = new LatLng(lat, lon);
        Marker marker = mMap.addMarker(new MarkerOptions().position(place).title("Meeting point"));
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        mMap.setMinZoomPreference(10);
        mMap.setMaxZoomPreference(100);
    }

    private void checkBookButton(){
        Button btnRegister = findViewById(R.id.btnReturn);
        TextView txtAvailable = null;
        TextView lbl = null;
        if(Integer.parseInt(trip.getAvailable()) <= 0 ){
            btnRegister.setClickable(false);
            btnRegister.setBackgroundColor(0xFFEDEDED);
            btnRegister.setTextColor(0xFFC6C6C6);
            lbl = findViewById(R.id.lblAvailable);
            lbl.setText("No available places");
            txtAvailable = findViewById(R.id.txtAvailable);
            txtAvailable.setVisibility(View.INVISIBLE);
        }else{
            btnRegister.setClickable(true);
            btnRegister.setBackgroundColor(0xFFD81B60);
            btnRegister.setTextColor(0xFFFFFFFF);
            lbl = findViewById(R.id.lblAvailable);
            lbl.setText("Available places");
            txtAvailable = findViewById(R.id.txtAvailable);
            txtAvailable.setVisibility(View.VISIBLE);
            txtAvailable.setText(this.trip.getAvailable());
        }
    }
}


