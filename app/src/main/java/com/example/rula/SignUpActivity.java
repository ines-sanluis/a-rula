package com.example.rula;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference myBookings;
    private DatabaseReference tripReference;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        trip = createTrip(intent.getExtras());
        myBookings = FirebaseDatabase.getInstance().getReference().child(trip.getKey()).child("bookings");
        tripReference  = FirebaseDatabase.getInstance().getReference().child(trip.getKey());
        setSpinner();

        tripReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals("date")){
                    Toast.makeText(getBaseContext(), "The date has just been updated", Toast.LENGTH_LONG).show();
                    trip.setDate((String) dataSnapshot.getValue());
                    TextView txtDate = findViewById(R.id.lblTripDate);
                    txtDate.setText(trip.getDate());
                }else if(dataSnapshot.getKey().equals("name")){
                    Toast.makeText(getBaseContext(), "The name has just been updated", Toast.LENGTH_LONG).show();
                    trip.setName((String) dataSnapshot.getValue());
                    TextView txtName = findViewById(R.id.lblTripName);
                    txtName.setText(trip.getName());
                }else if(dataSnapshot.getKey().equals("maxPeople")){
                    trip.setMaxPeople((String) dataSnapshot.getValue());
                    if(Integer.parseInt(trip.getAvailable()) <= 0){
                        Toast.makeText(getBaseContext(), "We are sorry. This trip has just been fully booked", Toast.LENGTH_LONG).show();
                        goBackTripDetail();
                    }else{
                        Toast.makeText(getBaseContext(), "The available places have just been updated", Toast.LENGTH_LONG).show();
                    }
                    setSpinner();
                }else if(dataSnapshot.getKey().equals("bookings")){
                    long nBookings = 0;
                    if(dataSnapshot.getChildrenCount() != 0) {
                        for (DataSnapshot reservation : dataSnapshot.getChildren()) {
                            nBookings += Long.parseLong((String) reservation.child("numOfPeople").getValue());
                        }
                    }
                    trip.setNumberBookings(Long.toString(nBookings));
                    if(Integer.parseInt(trip.getAvailable()) <= 0){
                        Toast.makeText(getBaseContext(), "We are sorry. This trip has just been fully booked", Toast.LENGTH_LONG).show();
                        goBackTripDetail();
                    }else{
                        Toast.makeText(getBaseContext(), "The available places have just been updated", Toast.LENGTH_LONG).show();
                    }
                    setSpinner();
                }else{
                    Toast.makeText(getBaseContext(), "The "+dataSnapshot.getKey()+" has just been updated", Toast.LENGTH_LONG).show();
                }
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
        TextView txtName = findViewById(R.id.lblTripName);
        txtName.setText(trip.getName());
        TextView txtDate = findViewById(R.id.lblTripDate);
        txtDate.setText(trip.getDate());
    }

    public void onReturnButtonClick (View v) {
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
            myBookings.push().setValue(new Reservation(name, email, phone, nPeople));
            Toast.makeText(getBaseContext(), "Data inserted successfully!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
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
        String nBookings = extras.getString("nBookings");
        String description = extras.getString("description");
        Location location = new Location(latitude, longitude, locationTag);
        return new Trip(key, name, location, difficulty, date, maxPeople, nBookings, description);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private boolean isValidPhone(String phone){
        String regex = "[+ ]*[0-9]+([0-9]*[ ]*)*$";
        return phone.matches(regex);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBackTripDetail();
                break;
        }
        return true;
    }

    private void goBackTripDetail(){
        Intent intent = new Intent(this, TripDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putString("key", trip.getKey());
        extras.putString("latitude", trip.getLatitude());
        extras.putString("longitude", trip.getLongitude());
        extras.putString("locationTag", trip.getLocationTag());
        extras.putString("maxPeople", trip.getMaxPeople());
        extras.putString("nBookings", trip.getNumberBookings());
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void setSpinner(){
        Spinner spinner1 = (Spinner) findViewById(R.id.txtPeople);
        List<Integer> spinnerArray = new ArrayList<>();
        for(int i = 1; i<=Integer.parseInt(trip.getAvailable()); i++) spinnerArray.add(i);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,spinnerArray);
        spinner1.setAdapter(adapter);
    }


}
