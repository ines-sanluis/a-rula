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

    DatabaseReference reference;

    EditText name;
    EditText email;
    EditText phone;
    EditText nPeople;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();

        TextView txtName = findViewById(R.id.txtName);
        txtName.setText(intent.getExtras().getString("name"));
        TextView txtDate = findViewById(R.id.txtDate);
        txtDate.setText(intent.getExtras().getString("date"));

        reference = FirebaseDatabase.getInstance().getReference().child("Reservation");

        name = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        phone = findViewById(R.id.editPhone);
        nPeople = findViewById(R.id.editPeopleNo);
    }

    public void onButtonClick (View v) {
        Intent switchActivity = new Intent(getBaseContext(), MainActivity.class);
        startActivity(switchActivity);
    }

    public void onSaveButtonClick (View v) {
        Reservation reservation = new Reservation(name.getText().toString(), email.getText().toString(), phone.getText().toString(), nPeople.getText().toString());
        reference.push().setValue(reservation);
        Toast.makeText(getBaseContext(), "Data inserted succesfully!", Toast.LENGTH_LONG).show();
    }
}
