package com.example.rewerk3;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListingsActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView locationTextView;
    private TextView specializationTextView;
    private TextView dobTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);

        nameTextView = findViewById(R.id.item_nameTextView);
        phoneTextView = findViewById(R.id.item_phoneTextView);
        locationTextView = findViewById(R.id.item_locationTextView);
        specializationTextView = findViewById(R.id.item_specializationTextView);
        dobTextView = findViewById(R.id.item_dobTextView);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                HelperClass user = dataSnapshot.getValue(HelperClass.class);
                if (user != null) {
                    displayUser(user);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle child changed event if needed
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Handle child removed event if needed
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle child moved event if needed
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void displayUser(HelperClass user) {
        nameTextView.setText(user.getName());
        phoneTextView.setText(user.getPhone());
        locationTextView.setText(user.getLocation());
        specializationTextView.setText(user.getSpecialization());
        dobTextView.setText(user.getDob());
    }
}
