package com.example.rula;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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

        TextView txtName = findViewById(R.id.lblTripName);
        txtName.setText(trip.getName());
        TextView txtDate = findViewById(R.id.lblTripDate);
        txtDate.setText(trip.getDate());
    }

    public void onButtonClick (View v) {
        Intent switchActivity = new Intent(getBaseContext(), MainActivity.class);
        startActivity(switchActivity);
    }

    public void onBookButtonClick (View v) {
        Boolean correct = true;
        EditText aux;
        aux = findViewById(R.id.txtName);
        String name = aux.getText().toString();
        if(name.isEmpty()){
            aux.setError("Please enter a name");
            correct = false;
        }
        aux = findViewById(R.id.txtEmail);
        String email = aux.getText().toString();
        if(email.isEmpty()){
            aux.setError("Please enter an email");
            correct = false;
        }
        if(!isValidEmail(email)){
            aux.setError("Please enter a valid email");
            correct = false;
        }
        aux = findViewById(R.id.txtPhone);
        String phone = aux.getText().toString();
        if(phone.isEmpty()){
            aux.setError("Please enter a phone");
            correct = false;
        }
        if(!isValidPhone(phone)){
            aux.setError("Please enter a valid phone");
            correct = false;
        }
        Spinner s = findViewById(R.id.txtPeople);
        String nPeople = s.getSelectedItem().toString();
        if(correct) {
            reference.push().setValue(new Reservation(name, email, phone, nPeople));
            Toast.makeText(getBaseContext(), "Data inserted successfully!", Toast.LENGTH_LONG).show();
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
        String maxPeople = extras.getString("maxPeople");
        Location location = new Location(latitude, longitude, locationTag);
        return new Trip(key, name, location, difficulty, date, maxPeople);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private boolean isValidPhone(String phone){
        String regex = "[+ ]*[0-9]+([0-9]*[ ]*)*$";
        return phone.matches(regex);
    }


}
