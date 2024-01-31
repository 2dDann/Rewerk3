package com.example.rewerk3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<HelperClass> list;


    public MyAdapter(Context context, ArrayList<HelperClass> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listings,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HelperClass user = list.get(position);
        holder.name.setText(user.getName());
        holder.phonenumber.setText(user.getPhone());
        holder.location.setText(user.getLocation());
        holder.specialization.setText(user.getSpecialization());
        holder.dob.setText(user.getDob());

        String name=user.getName();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userUsername = name.trim();

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

                            Intent intent = new Intent(context, DetailActivity.class);

                            intent.putExtra("name", nameFromDB);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("username", usernameFromDB);
                            intent.putExtra("password", passwordFromDB);
                            intent.putExtra("location", locationFromDB);
                            intent.putExtra("specialization", specializationFromDB);
                            intent.putExtra("description", descriptionFromDB);
                            intent.putExtra("phone", phoneFromDB);
                            intent.putExtra("dob", dobFromDB);

                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, phonenumber, location, specialization, dob;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_nameTextView);
            phonenumber = itemView.findViewById(R.id.item_phoneTextView);
            location = itemView.findViewById(R.id.item_locationTextView);
            specialization = itemView.findViewById(R.id.item_specializationTextView);
            dob = itemView.findViewById(R.id.item_dobTextView);
        }
    }

}