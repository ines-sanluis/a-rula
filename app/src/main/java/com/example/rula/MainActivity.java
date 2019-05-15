package com.example.rula;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button upcoming = findViewById(R.id.upcoming);
        Button donate = findViewById(R.id.donate);
        upcoming.setOnClickListener(this);
        donate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upcoming:
                Intent myIntent1 = new Intent(MainActivity.this, UpcomingActivity.class);
                MainActivity.this.startActivity(myIntent1);
                //Toast.makeText(this, "Upcoming button clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.donate:
                Intent myIntent2 = new Intent(MainActivity.this, DonateActivity.class);
                MainActivity.this.startActivity(myIntent2);
                break;
        }
    }
}
