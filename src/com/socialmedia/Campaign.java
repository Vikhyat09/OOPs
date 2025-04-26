package com.socialmedia;

import java.util.ArrayList;

public class Campaign {
    private String name;
    private String platform;
    private int engagement; // Engagement value for the campaign
    private ArrayList<Influencer> influencers; // List of influencers associated with the campaign

    public Campaign(String name, String platform) {
        this.name = name;
        this.platform = platform;
        this.engagement = 0; // Initialize engagement to 0
        this.influencers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public int getEngagement() {
        return engagement;
    }

    public void increaseEngagement(int value) {
        this.engagement += value;
    }

    public void addInfluencer(Influencer influencer) {
        influencers.add(influencer);
    }

    public ArrayList<Influencer> getInfluencers() {
        return influencers;
    }
}