package com.socialmedia;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BrandManager extends User {
    public int budget; // Budget for the BrandManager
    private ArrayList<Campaign> myCampaigns;

    public BrandManager(String username, String password, String email, String role, String niche) {
        super(username, password, email, role, niche);
        Random random = new Random();
        this.budget = random.nextInt(190001) + 10000; // Random budget between 10,000 and 200,000
        this.myCampaigns = new ArrayList<>();
    }

    // Method to create a new campaign
    public void createCampaign(String name, String platform) {
        Campaign campaign = new Campaign(name, platform);
        myCampaigns.add(campaign);
        System.out.println("Campaign '" + name + "' created successfully on platform: " + platform);
    }

    // Method to list all campaigns
    public void listCampaigns() {
        System.out.println("Campaigns created by " + getUsername() + ":");
        if (myCampaigns.isEmpty()) {
            System.out.println("No campaigns created yet.");
        } else {
            for (Campaign campaign : myCampaigns) {
                System.out.println("- Campaign: " + campaign.getName() + " on " + campaign.getPlatform());
                System.out.println("  Engagement: " + campaign.getEngagement());

                // Display associated influencers
                ArrayList<Influencer> influencers = campaign.getInfluencers();
                if (influencers.isEmpty()) {
                    System.out.println("  No influencers associated yet.");
                } else {
                    System.out.println("  Influencers:");
                    for (Influencer influencer : influencers) {
                        System.out.println("    - " + influencer.getUsername());
                    }
                }
            }
        }
    }

    // Method to offer a contract to an influencer
    public void offerContract(Influencer influencer, String campaignName) {
        Campaign targetCampaign = getCampaignByName(campaignName);
        if (targetCampaign == null) {
            System.out.println("Campaign not found: " + campaignName);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the offer amount for the contract: ");
        int offerAmount = scanner.nextInt();

        if (offerAmount > getBudget()) {
            System.out.println("Insufficient budget to offer this amount. Current budget: $" + getBudget());
            return;
        }

        Contract contract = new Contract(this, influencer, targetCampaign, offerAmount);
        influencer.receiveContract(contract); // Send the contract to the influencer
        budget -= offerAmount; // Deduct the offer amount from the budget
        System.out.println("Contract offered to " + influencer.getUsername() + " for campaign: " + campaignName +
                " with an offer of $" + offerAmount);
        System.out.println("Remaining budget: $" + getBudget());
    }

    // Helper method to find a campaign by its name
    private Campaign getCampaignByName(String campaignName) {
        for (Campaign campaign : myCampaigns) {
            if (campaign.getName().equalsIgnoreCase(campaignName)) {
                return campaign;
            }
        }
        return null;
    }

    // New Method: View Brand Manager Info
    public void viewInfo() {
        System.out.println("\nBrand Manager Information:");
        System.out.println("Username: " + getUsername());
        System.out.println("Email: " + getEmail());
        System.out.println("Niche: " + getNiche());
        System.out.println("Current Budget: $" + getBudget());
    }

    // Getter for budget
    public int getBudget() {
        return this.budget;
    }

    // Dashboard method for Brand Manager
    @Override
    public void viewDashboard() {
        System.out.println("Brand Manager Dashboard - Manage Campaigns and Contracts");
    }
}