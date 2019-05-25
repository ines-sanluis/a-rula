package com.example.rula;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;
    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<Trip> myTrips = new ArrayList<>();
    private ArrayList<String> myKeys = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = FirebaseDatabase.getInstance().getReference();
        adapter = new ArrayAdapter<String>(this, R.layout.list_row, R.id.textView2, arrayList);
        listView = findViewById(R.id.trips_list_view);
        listView.setAdapter(adapter);
        listView.setClickable(true);

        myDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Trip myTrip = this.createTrip(dataSnapshot);
                myKeys.add(dataSnapshot.getKey());
                myTrips.add(myTrip);
                arrayList.add("\n"+myTrip.getName()+"\n");
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                Integer position = myKeys.indexOf(key);
                Trip myTrip = this.createTrip(dataSnapshot);
                myTrips.set(position, myTrip);
                arrayList.set(position, "\n"+myTrip.getName()+"\n");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                /*
                Not working

                String key = dataSnapshot.getKey();
                Integer position = myKeys.indexOf(key);
                myKeys.remove(position);
                myTrips.remove(position);
                arrayList.remove(position);
                adapter.notifyDataSetChanged();
                */
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            private Trip createTrip(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                String name = null;
                String difficulty = null;
                String date = null;
                String maxPeople = null;
                String latitude = null;
                String longitude = null;
                String locationTag = null;
                long nBookings = 0;

                for(DataSnapshot attribute : dataSnapshot.getChildren()) { //trips

                    switch (attribute.getKey()) {
                        case "location":
                            latitude = (String) attribute.child("lat").getValue();
                            longitude = (String) attribute.child("lon").getValue();
                            locationTag = (String) attribute.child("tag").getValue();
                            break;
                        case "date": date = (String) attribute.getValue(); break;
                        case "difficulty": difficulty = (String) attribute.getValue(); break;
                        case "maxPeople": maxPeople = (String) attribute.getValue(); break;
                        case "name": name = (String) attribute.getValue(); break;
                        case "bookings": nBookings = attribute.getChildrenCount(); break;
                    }
                }
                Location location = new Location(latitude, longitude, locationTag);
                return new Trip(key, name, location, difficulty, date, Long.toString(Long.parseLong(maxPeople)-nBookings));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), TripDetailActivity.class);
                Trip selected = myTrips.get(position);

                Bundle extras = new Bundle();
                extras.putString("key", selected.getKey());
                extras.putString("name", selected.getName());
                extras.putString("latitude", selected.getLatitude());
                extras.putString("longitude", selected.getLongitude());
                extras.putString("date", selected.getDate());
                extras.putString("difficulty", selected.getDifficulty());
                extras.putString("available", selected.getAvailable());
                extras.putString("locationTag", selected.getLocationTag());
                intent.putExtras(extras);

                startActivity(intent);
            }
        });
    }


}
