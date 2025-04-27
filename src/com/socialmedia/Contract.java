package com.socialmedia;

public class Contract {
    private BrandManager brandManager;
    private Influencer influencer;
    private Campaign campaign;
    private int offerAmount; // Monetary value of the contract
    private String status; // Accepted, Rejected, Pending

    public Contract(BrandManager brandManager, Influencer influencer, Campaign campaign, int offerAmount) {
        this.brandManager = brandManager;
        this.influencer = influencer;
        this.campaign = campaign;
        this.offerAmount = offerAmount;
        this.status = "Pending";
    }

    public String getCampaignName() {
        return campaign.getName();
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public int getOfferAmount() {
        return offerAmount;
    }

    public void accept() {
        this.status = "Accepted";
    }

    public void reject() {
        this.status = "Rejected";
    }

    public String getStatus() {
        return status;
    }
}