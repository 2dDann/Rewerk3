package com.example.rewerk3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editEmail, editUsername, editPassword, editSpecialization, editLocation, editDescription, editPhone, editDOB;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editSpecialization = findViewById(R.id.editSpecialization);
        editLocation = findViewById(R.id.editLocation);
        editDescription = findViewById(R.id.editDescription);
        editPhone = findViewById(R.id.editPhone);
        editDOB = findViewById(R.id.editDOB);
        saveButton = findViewById(R.id.saveButton);

        // Retrieve the data passed from ProfileActivity
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String specialization = intent.getStringExtra("specialization");
        String location = intent.getStringExtra("location");
        String description = intent.getStringExtra("description");
        String phone = intent.getStringExtra("phone");
        String dob = intent.getStringExtra("dob");

        // Set the retrieved values in the EditText fields or TextViews
        editName.setText(name);
        editEmail.setText(email);
        editUsername.setText(username);
        editPassword.setText(password);
        editSpecialization.setText(specialization);
        editLocation.setText(location);
        editDescription.setText(description);
        editPhone.setText(phone);
        editDOB.setText(dob);

        // Save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the updated values from the EditText fields
                String updatedName = editName.getText().toString();
                String updatedEmail = editEmail.getText().toString();
                String updatedUsername = editUsername.getText().toString();
                String updatedPassword = editPassword.getText().toString();
                String updatedSpecialization = editSpecialization.getText().toString();
                String updatedLocation = editLocation.getText().toString();
                String updatedDescription = editDescription.getText().toString();
                String updatedPhone = editPhone.getText().toString();
                String updatedDOB = editDOB.getText().toString();

                // Update the user's profile in the Firebase Realtime Database
                DatabaseReference reference = FirebaseDatabase.getInstance("https://rewerk3-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
                Query query = reference.orderByChild("username").equalTo(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String userId = dataSnapshot.getKey();

                                // Update the values for the user
                                reference.child(userId).child("name").setValue(updatedName);
                                reference.child(userId).child("email").setValue(updatedEmail);
                                reference.child(userId).child("username").setValue(updatedUsername);
                                reference.child(userId).child("password").setValue(updatedPassword);
                                reference.child(userId).child("specialization").setValue(updatedSpecialization);
                                reference.child(userId).child("location").setValue(updatedLocation);
                                reference.child(userId).child("description").setValue(updatedDescription);
                                reference.child(userId).child("phone").setValue(updatedPhone);
                                reference.child(userId).child("dob").setValue(updatedDOB);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle the error
                    }
                });

                // Go back to the ProfileActivity
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                // Pass the updated values back to the ProfileActivity
                intent.putExtra("name", updatedName);
                intent.putExtra("email", updatedEmail);
                intent.putExtra("username", updatedUsername);
                intent.putExtra("password", updatedPassword);
                intent.putExtra("specialization", updatedSpecialization);
                intent.putExtra("location", updatedLocation);
                intent.putExtra("description", updatedDescription);
                intent.putExtra("phone", updatedPhone);
                intent.putExtra("dob", updatedDOB);
                startActivity(intent);
                finish();
            }
        });
    }
}
