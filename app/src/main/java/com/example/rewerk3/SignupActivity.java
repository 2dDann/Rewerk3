package com.example.rewerk3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupUsername, signupEmail, signupPassword, signupDob, signupPhone;
    TextView loginRedirectText;
    Button signupButton;
    Calendar dobCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupDob = findViewById(R.id.signup_dob);
        signupPhone = findViewById(R.id.signup_phone);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        dobCalendar = Calendar.getInstance();

        signupDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                String dob = signupDob.getText().toString();
                String phone = signupPhone.getText().toString();
                String specialization = " ";
                String location = " ";
                String description = " ";
                float totalRatings = 0;
                float noRating = 0;

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(username)
                        || TextUtils.isEmpty(password) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(SignupActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the username exists
                    checkUsernameAvailability(username, new OnUsernameAvailabilityListener() {
                        @Override
                        public void onUsernameAvailable() {
                            // Username is available, proceed with sign up
                            DatabaseReference reference = FirebaseDatabase.getInstance("https://rewerk3-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
                            HelperClass helperClass = new HelperClass(name, email, username, password, dob, phone, specialization, location, description, totalRatings, noRating);
                            reference.child(username).setValue(helperClass);

                            Toast.makeText(SignupActivity.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onUsernameUnavailable() {
                            // Username is unavailable, inform the user
                            Toast.makeText(SignupActivity.this, "Username has been used", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dobCalendar.set(Calendar.YEAR, year);
                        dobCalendar.set(Calendar.MONTH, monthOfYear);
                        dobCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDobField();
                    }
                },
                dobCalendar.get(Calendar.YEAR),
                dobCalendar.get(Calendar.MONTH),
                dobCalendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void updateDobField() {
        String dateFormat = "dd-MM-yyyy"; // Change the format as desired
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        signupDob.setText(sdf.format(dobCalendar.getTime()));
    }

    private void checkUsernameAvailability(String username, OnUsernameAvailabilityListener listener) {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://rewerk3-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        Query query = reference.orderByChild("username").equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Username already exists
                    listener.onUsernameUnavailable();
                } else {
                    // Username is available
                    listener.onUsernameAvailable();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    interface OnUsernameAvailabilityListener {
        void onUsernameAvailable();

        void onUsernameUnavailable();
    }
}
