package com.example.rula;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TripDetailActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        myDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Trip trip = createTrip(intent.getExtras());

        Button btnRegister = findViewById(R.id.btnRegister);
        if(trip.getMaxPeople() <= 0 ){
            btnRegister.setEnabled(false);
            btnRegister.setBackgroundColor(0xFFEAEAEA);
        }

        TextView txtName = findViewById(R.id.txtName);
        txtName.setText(trip.getName());

        TextView txtDate = findViewById(R.id.txtDate);
        txtDate.setText(trip.getDate());

        TextView txtDifficulty = findViewById(R.id.txtDifficulty);
        txtDifficulty.setText(Integer.toString(trip.getDifficulty()));

        TextView txtLocation = findViewById(R.id.txtLocation);
        txtLocation.setText(trip.getLocation());

        TextView txtAvailable = findViewById(R.id.txtAvailable);
        txtAvailable.setText(Integer.toString(trip.getMaxPeople()));

    }

    private Trip createTrip(Bundle extras){
        String key = extras.getString("key");
        String name = extras.getString("name");
        String location = extras.getString("location");
        String date = extras.getString("date");
        Integer difficulty = Integer.parseInt(extras.getString("difficulty"));
        Integer maxPeople = Integer.parseInt(extras.getString("maxPeople"));
        return new Trip(name, location, difficulty, date, maxPeople);
    }

}


