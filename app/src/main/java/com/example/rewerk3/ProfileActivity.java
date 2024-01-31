package com.example.rewerk3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView profileName, profileEmail, profileUsername, profilePassword, profileSpecialization, profileLocation, profileDescription, profilePhone, profileDOB;
    Button editProfile, listingsButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profilePassword = findViewById(R.id.profilePassword);
        profileUsername = findViewById(R.id.profileUsername);
        profileSpecialization = findViewById(R.id.profileSpecialization);
        profileLocation = findViewById(R.id.profileLocation);
        profileDescription = findViewById(R.id.profileDescription);
        profilePhone = findViewById(R.id.profilePhone);
        profileDOB = findViewById(R.id.profileDOB);
        editProfile = findViewById(R.id.editProfile);
        listingsButton = findViewById(R.id.listingsButton);

        showAllUserData();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });

        listingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, userlist.class);
                startActivity(intent);
            }
        });
    }

    public void showAllUserData() {
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        String emailUser = intent.getStringExtra("email");
        String usernameUser = intent.getStringExtra("username");
        String passwordUser = intent.getStringExtra("password");
        String specializationUser = intent.getStringExtra("specialization");
        String locationUser = intent.getStringExtra("location");
        String descriptionUser = intent.getStringExtra("description");
        String phoneUser = intent.getStringExtra("phone");
        String dobUser = intent.getStringExtra("dob");

        profileName.setText(nameUser);
        profileEmail.setText(emailUser);
        profileUsername.setText(usernameUser);
        profilePassword.setText(passwordUser);
        profileSpecialization.setText(specializationUser);
        profileLocation.setText(locationUser);
        profileDescription.setText(descriptionUser);
        profilePhone.setText(phoneUser); // Set phone number
        profileDOB.setText(dobUser); // Set date of birth
    }

    public void passUserData() {
        String userUsername = profileUsername.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://rewerk3-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                    String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    String specializationFromDB = snapshot.child(userUsername).child("specialization").getValue(String.class);
                    String locationFromDB = snapshot.child(userUsername).child("location").getValue(String.class);
                    String descriptionFromDB = snapshot.child(userUsername).child("description").getValue(String.class);
                    String phoneFromDB = snapshot.child(userUsername).child("phone").getValue(String.class);
                    String dobFromDB = snapshot.child(userUsername).child("dob").getValue(String.class);

                    Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);

                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("username", usernameFromDB);
                    intent.putExtra("password", passwordFromDB);
                    intent.putExtra("location", locationFromDB);
                    intent.putExtra("specialization", specializationFromDB);
                    intent.putExtra("description", descriptionFromDB);
                    intent.putExtra("phone", phoneFromDB);
                    intent.putExtra("dob", dobFromDB);

                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
