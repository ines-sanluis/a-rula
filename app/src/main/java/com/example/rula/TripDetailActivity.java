package com.example.rula;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TripDetailActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        myDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Trip trip = createTrip(intent.getExtras());

        TextView txtName = findViewById(R.id.txtName);
        txtName.setText(trip.getName());

        TextView txtDate = findViewById(R.id.txtDate);
        txtDate.setText(trip.getDate());

        TextView txtDifficulty = findViewById(R.id.txtDifficulty);
        txtDifficulty.setText(Integer.toString(trip.getDifficulty()));

        TextView txtLocation = findViewById(R.id.txtLocation);
        txtLocation.setText(trip.getLocation());

    }

    private Trip createTrip(Bundle extras){
        String key = extras.getString("key");
        String name = extras.getString("name");
        String location = extras.getString("location");
        String date = extras.getString("date");
        Integer difficulty = Integer.parseInt(extras.getString("difficulty"));
        return new Trip(name, location, difficulty, date);
    }
}
