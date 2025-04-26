package com.socialmedia;

import java.util.ArrayList;
import java.util.Random;

public class Influencer extends User {
    private int engagement; // Engagement value for the influencer
    private int income; // Income earned by the influencer
    private final int ageDemographic;
    private final int followers;
    private ArrayList<Contract> receivedContracts;

    public Influencer(String username, String password, String email, String role, String niche) {
        super(username, password, email, role, niche);

        Random random = new Random();
        this.engagement = random.nextInt(100000000) + 1; // 1 to 100,000,000
        this.income = 0; // Initialize income to 0
        this.ageDemographic = random.nextInt(78) + 13;   // 13 to 90
        this.followers = random.nextInt(9999999) + 1;    // 1 to 9,999,999
        this.receivedContracts = new ArrayList<>();
    }

    public void receiveContract(Contract contract) {
        receivedContracts.add(contract);
        System.out.println(getUsername() + " received a contract for campaign: " + contract.getCampaignName() +
                " with an offer of $" + contract.getOfferAmount());
    }

    public void viewContracts() {
        System.out.println("Contracts received by " + getUsername() + ":");
        if (receivedContracts.isEmpty()) {
            System.out.println("No contracts received yet.");
        } else {
            for (int i = 0; i < receivedContracts.size(); i++) {
                Contract contract = receivedContracts.get(i);
                System.out.println((i + 1) + ". Campaign: " + contract.getCampaignName() + " (Status: " + contract.getStatus() + 
                        ", Offer: $" + contract.getOfferAmount() + ")");
            }
        }
    }

    public void acceptContract(int contractIndex) {
        if (contractIndex < 0 || contractIndex >= receivedContracts.size()) {
            System.out.println("Invalid contract selection.");
            return;
        }
        Contract contract = receivedContracts.get(contractIndex);
        contract.accept();

        // Add this influencer to the associated campaign
        Campaign campaign = contract.getCampaign();
        campaign.addInfluencer(this);

        // Add the contract amount to the influencer's income
        this.income += contract.getOfferAmount();

        // Increase engagement for both the influencer and the campaign
        Random random = new Random();
        int engagementIncrease = random.nextInt(100001); // Random value between 0 and 100000
        this.engagement += engagementIncrease;
        campaign.increaseEngagement(engagementIncrease);

        System.out.println("You have accepted the contract for campaign: " + campaign.getName());
        System.out.println("Engagement increased by " + engagementIncrease + " for both the influencer and the campaign.");
        System.out.println("You earned $" + contract.getOfferAmount() + " from this contract.");
    }

    public void rejectContract(int contractIndex) {
        if (contractIndex < 0 || contractIndex >= receivedContracts.size()) {
            System.out.println("Invalid contract selection.");
            return;
        }
        Contract contract = receivedContracts.get(contractIndex);
        contract.reject();
        System.out.println("You have rejected the contract for campaign: " + contract.getCampaignName());
    }

    public int getIncome() {
        return income;
    }

    public int getEngagement() {
        return engagement;
    }

    public int getAgeDemographic() {
        return ageDemographic;
    }

    public int getFollowers() {
        return followers;
    }

    public void viewDetails() {
        System.out.println("\nInfluencer Details:");
        System.out.println("Username: " + getUsername());
        System.out.println("Email: " + getEmail());
        System.out.println("Niche: " + getNiche());
        System.out.println("Engagement: " + engagement);
        System.out.println("Income: $" + income);
        System.out.println("Age Demographic: " + ageDemographic);
        System.out.println("Followers: " + followers);
    }

    @Override
    public void viewDashboard() {
        System.out.println("Influencer Dashboard - Collaborate on campaigns and manage contracts");
    }
}