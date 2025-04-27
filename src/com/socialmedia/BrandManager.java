package com.socialmedia;

import java.util.ArrayList;
import java.util.List;
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
    
    /**
     * Filters influencers based on specified criteria.
     * 
     * @param influencers List of influencers to filter
     * @param niche Niche to filter by (must match exactly)
     * @param minEngagement Minimum engagement value
     * @param minAge Minimum age demographic
     * @param maxAge Maximum age demographic
     * @return List of influencers that match the criteria
     */
    public List<Influencer> filterInfluencers(List<Influencer> influencers, String niche, 
                                             int minEngagement, int minAge, int maxAge) {
        List<Influencer> filteredInfluencers = new ArrayList<>();
        
        for (Influencer influencer : influencers) {
            boolean nicheMatch = niche.isEmpty() || influencer.getNiche().equalsIgnoreCase(niche);
            boolean engagementMatch = influencer.getEngagement() >= minEngagement;
            boolean ageMatch = influencer.getAgeDemographic() >= minAge && 
                              influencer.getAgeDemographic() <= maxAge;
            
            if (nicheMatch && engagementMatch && ageMatch) {
                filteredInfluencers.add(influencer);
            }
        }
        
        return filteredInfluencers;
    }
    
    /**
     * Displays a list of filtered influencers based on user input criteria.
     * 
     * @param allInfluencers List of all available influencers
     */
    public void findMatchingInfluencers(List<Influencer> allInfluencers) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n===== INFLUENCER FILTER =====");
        
        // Get niche input
        System.out.print("Enter niche (leave empty for any): ");
        String niche = scanner.nextLine().trim();
        
        // Get engagement input
        System.out.print("Enter minimum engagement: ");
        int minEngagement = Integer.parseInt(scanner.nextLine());
        
        // Get age demographic input
        System.out.print("Enter minimum age demographic: ");
        int minAge = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Enter maximum age demographic: ");
        int maxAge = Integer.parseInt(scanner.nextLine());
        
        // Filter influencers based on age x-y , engagement rate > z , and specific niche
        List<Influencer> matchingInfluencers = filterInfluencers(allInfluencers, niche, 
                                                                minEngagement, minAge, maxAge);
        
        // Display results
        System.out.println("\n===== MATCHING INFLUENCERS =====");
        if (matchingInfluencers.isEmpty()) {
            System.out.println("No influencers match your criteria.");
        } else {
            System.out.println("Found " + matchingInfluencers.size() + " matching influencers:\n");
            
            for (Influencer influencer : matchingInfluencers) {
                System.out.println("Username: " + influencer.getUsername());
                System.out.println("Niche: " + influencer.getNiche());
                System.out.println("Engagement: " + influencer.getEngagement());
                System.out.println("Age Demographic: " + influencer.getAgeDemographic());
                System.out.println("Followers: " + influencer.getFollowers());
                System.out.println("-----------------------------------");
            }
        }
    }
}
