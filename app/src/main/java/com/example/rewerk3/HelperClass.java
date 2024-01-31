package com.example.rewerk3;

public class HelperClass {

    String name, email, username, password, dob, phone, specialization, location, description;
    float noRating, totalRating;

    public void setNoRating(float noRating) {
        this.noRating = noRating;
    }

    public void setTotalRating(float totalRating) {
        this.totalRating = totalRating;
    }

    public float getNoRating() {
        return noRating;
    }

    public float getTotalRating() {
        return totalRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HelperClass(String name, String email, String username, String password, String dob, String phone, String specialization, String location, String description, float noRating, float totalRating) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.phone = phone;
        this.specialization = specialization;
        this.location = location;
        this.description = description;
        this.noRating = noRating;
        this.totalRating = totalRating;
    }

    public HelperClass() {
    }
}
