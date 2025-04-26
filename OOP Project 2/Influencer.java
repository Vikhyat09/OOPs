package com.influencer.platform;

import java.util.Objects;

public class Influencer extends User {
    private Niche[] niches = new Niche[3];
    private Stats stats = new Stats(); // Each influencer has its own Stats

    // ----------------------------
    // Nested Stats Class (now properly encapsulated)
    // ----------------------------
    public static class Stats {
        private Double engagement;
        private Long audienceSize;
        private Double demographic;

        // Setters (private to enforce controlled access)
        private void setEngagement(Double rate) {
            this.engagement = rate;
        }
        private void setAudienceSize(Long size) {
            this.audienceSize = size;
        }
        private void setDemographic(Double demographic) {
            this.demographic = demographic;
        }

        // Getters
        public Double getEngagement() {
            return engagement;
        }
        public Long getAudienceSize() {
            return audienceSize;
        }
        public Double getDemographic() {
            return demographic;
        }
    }

    // ----------------------------
    // Niche Methods (unchanged)
    // ----------------------------
    public void setNiches(Niche first) {
        setNiches(first, null, null);
    }

    public void setNiches(Niche first, Niche second) {
        setNiches(first, second, null);
    }

    public void setNiches(Niche first, Niche second, Niche third) {
        validateNiche(first);
        if (second != null) validateNiche(second);
        if (third != null) validateNiche(third);

        niches[0] = Objects.requireNonNull(first, "First niche cannot be null");
        niches[1] = second;
        niches[2] = third;
    }

    private void validateNiche(Niche niche) {
        for (Niche valid : Niche.VALID_NICHES) {
            if (niche == valid) return;
        }
        throw new IllegalArgumentException("Invalid niche: " + niche + 
               ". Valid niches: " + java.util.Arrays.toString(Niche.VALID_NICHES));
    }

    // ----------------------------
    // Controlled Stats Update (for Admin)
    // ----------------------------
    public void updateStats(double engagement, long audienceSize, Double demographic , User requester) {
        if (!(requester instanceof Admin)) {
            throw new SecurityException("Only Admin can update stats");
        }
        if (engagement < 0 || audienceSize < 0 || demographic <0) {
            throw new IllegalArgumentException("Stats must be positive");
        }
        stats.setEngagement(engagement);
        stats.setAudienceSize(audienceSize);
        stats.setDemographic(demographic);
    }

    // ----------------------------
    // Stats Getters
    // ----------------------------
    public Double getEngagement() {
        return stats.getEngagement();
    }
    public Long getAudienceSize() {
        return stats.getAudienceSize();
    }
    public Double getDemographic() {
        return stats.getDemographic();
    }

    // ----------------------------
    // Other Methods
    // ----------------------------
    public void acceptCampaign(Campaign c) {
        /* Implementation */ 
    }
}