package com.example.rula;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        trip = createTrip(intent.getExtras());
        reference = FirebaseDatabase.getInstance().getReference().child(trip.getKey()).child("bookings");

        TextView txtName = findViewById(R.id.txtName);
        txtName.setText(trip.getName());
        TextView txtDate = findViewById(R.id.txtDate);
        txtDate.setText(trip.getDate());
    }

    public void onButtonClick (View v) {
        Intent switchActivity = new Intent(getBaseContext(), MainActivity.class);
        startActivity(switchActivity);
    }

    public void onSaveButtonClick (View v) {
        EditText aux;
        aux = findViewById(R.id.editName);     String name = aux.getText().toString();
        aux = findViewById(R.id.editEmail);    String email = aux.getText().toString();
        aux = findViewById(R.id.editPhone);    String phone = aux.getText().toString();
        aux = findViewById(R.id.editPeopleNo); String nPeople = aux.getText().toString();
        reference.push().setValue(new Reservation(name, email, phone, nPeople));
        Toast.makeText(getBaseContext(), "Data inserted successfully!", Toast.LENGTH_LONG).show();
    }

    private Trip createTrip(Bundle extras){
        String key = extras.getString("key");
        String name = extras.getString("name");
        String latitude = extras.getString("latitude");
        String longitude = extras.getString("longitude");
        String locationTag = extras.getString("locationTag");
        String date = extras.getString("date");
        String difficulty = extras.getString("difficulty");
        String maxPeople = extras.getString("maxPeople");
        Location location = new Location(latitude, longitude, locationTag);
        return new Trip(key, name, location, difficulty, date, maxPeople);
    }

}
