package com.example.rewerk3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {

    TextView tv_descName, tv_descAge, tv_location, tv_email, tv_phone, tv_description;
    RatingBar descRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tv_descName = findViewById(R.id.tv_descName);
        tv_descAge = findViewById(R.id.tv_descAge);
        tv_location = findViewById(R.id.tv_location);
        tv_email = findViewById(R.id.tv_email);
        tv_phone = findViewById(R.id.tv_phone);
        tv_description = findViewById(R.id.tv_description);
        descRating = findViewById(R.id.descRating);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            tv_descName.setText(bundle.getString("name"));
            tv_descAge.setText(bundle.getString("dob"));
            tv_location.setText(bundle.getString("location"));
            tv_email.setText(bundle.getString("email"));
            tv_phone.setText(bundle.getString("phone"));
            tv_description.setText(bundle.getString("description"));
        }
    }

}