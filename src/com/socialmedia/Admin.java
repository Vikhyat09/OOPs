package com.socialmedia;

import java.util.ArrayList;
import java.util.Random;

public class Admin extends User {
    private ArrayList<User> userList;

    private static final String[] NICHES = {
        "FITNESS", "FOOD", "TRAVEL", "BEAUTY", "FASHION", "TECH", "GAMING"
    };

    public Admin(String username, String password, String email) {
        super(username, password, email, "Admin", null);
        this.userList = new ArrayList<>();
    }

    public void addUser(String username, String password, String email, String role) {
        User newUser = null;

        // Randomly assign a niche
        String niche = getRandomNiche();

        switch (role) {
            case "Admin":
                newUser = new Admin(username, password, email);
                break;
            case "BrandManager":
                newUser = new BrandManager(username, password, email, role, niche);
                break;
            case "Influencer":
                newUser = new Influencer(username, password, email, role, niche);
                break;
            default:
                System.out.println("Invalid role. User not created.");
                return;
        }

        userList.add(newUser);
        System.out.println("User added: " + username + " with role: " + role + " and niche: " + niche);

        if (newUser instanceof Influencer) {
            Influencer influencer = (Influencer) newUser;
            System.out.println("Influencer Details - Engagement: " + influencer.getEngagement()
                + ", Age Demographic: " + influencer.getAgeDemographic()
                + ", Followers: " + influencer.getFollowers());
        }
    }

    public void removeUser(String username) {
        User userToRemove = null;
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            userList.remove(userToRemove);
            System.out.println("User removed: " + username);
        } else {
            System.out.println("User not found: " + username);
        }
    }

    public void viewAllUsers() {
        System.out.println("All registered users:");
        for (User user : userList) {
            System.out.println("- Username: " + user.getUsername() + ", Role: " + user.getRole() + ", Niche: " + user.getNiche());
        }
    }

    private String getRandomNiche() {
        Random random = new Random();
        int index = random.nextInt(NICHES.length);
        return NICHES[index];
    }

    @Override
    public void viewDashboard() {
        System.out.println("Admin Dashboard - Manage Users and View Platform Analytics");
    }
}