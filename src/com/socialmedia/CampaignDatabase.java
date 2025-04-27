package com.socialmedia;

import java.util.ArrayList;

public class CampaignDatabase {
    private static ArrayList<Campaign> campaigns = new ArrayList<>();

    public static void addCampaign(Campaign campaign) {
        campaigns.add(campaign);
    }

    public static ArrayList<Campaign> getAllCampaigns() {
        return campaigns;
    }
}