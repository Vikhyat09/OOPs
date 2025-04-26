package com.influencer.platform;

public abstract class User {
    private String username;
    private String password;
    private String email;
    protected String role;
    private boolean isLoggedIn;

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public boolean login(String username, String password) {
        if(this.username.equals(username) && this.password.equals(password)) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        isLoggedIn = false;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getRole()
    { 
        return this.role;
    }

    public abstract void viewDashboard(); 
}
