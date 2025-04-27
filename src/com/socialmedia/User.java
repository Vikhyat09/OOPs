package com.socialmedia;

public abstract class User {
    private String username;
    private String password;
    private String email;
    protected String role;
    private String niche; // New attribute for niche

    public User(String username, String password, String email, String role, String niche) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.niche = niche;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email; // Getter for email
    }

    public String getNiche() {
        return niche;
    }

    public abstract void viewDashboard();
}