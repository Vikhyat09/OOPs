package com.influencer.platform;

import java.util.Objects;

public class Influencer extends User {
    private Niche[] niches = new Niche[3];
    private Stats stats = new Stats(); // Each influencer has its own Stats
    private final String name;
    private Contract[] contracts = new Contract[10];
    private int contractCount = 0;

    public Influencer(String name) {
        this.name = name;
    }

    public void addContract(Contract contract) {
        if (contractCount == contracts.length) {
            Contract[] newArray = new Contract[contracts.length * 2];
            for (int i = 0; i < contracts.length; i++) {
                newArray[i] = contracts[i];
            }
            contracts = newArray;
        }
        contracts[contractCount++] = contract;
    }

    public Contract[] getContracts() {
        Contract[] result = new Contract[contractCount];
        for (int i = 0; i < contractCount; i++) {
            result[i] = contracts[i];
        }
        return result;
    }

    // ----------------------------
    // Nested Stats Class (properly encapsulated)
    // ----------------------------
    public static class Stats {
        private Double[] engagement = new Double[4];
        private Long[] audienceSize = new Long[4];
        private Double[] demographic = new Double[4];
        private double sumOfEngagement = 0;
        private double sumOfDemographic = 0;
        private double avgOfDemographic = 0;
        private long sumOfAudienceSize = 0;

        // Constructor
        public Stats() {
            // Default initialization (optional)
            for (int i = 0; i < engagement.length; i++) {
                engagement[i] = 0.0;
                demographic[i] = 0.0;
                audienceSize[i] = 0L;
            }
        }

        // Update sums when data is set
        private void calculateSums() {
            sumOfEngagement = 0;
            sumOfDemographic = 0;
            avgOfDemographic = 0;
            sumOfAudienceSize = 0;

            for (double e : engagement) {
                sumOfEngagement += e;
            }
            for (double d : demographic) {
                sumOfDemographic += d;
            }
            avgOfDemographic = sumOfDemographic / demographic.length;
            for (long a : audienceSize) {
                sumOfAudienceSize += a;
            }
        }

        // Setters
        private void setEngagement(Double[] rates) {
            this.engagement = rates;
            calculateSums();
        }

        private void setAudienceSize(Long[] sizes) {
            this.audienceSize = sizes;
            calculateSums();
        }

        private void setDemographic(Double[] demographics) {
            this.demographic = demographics;
            calculateSums();
        }

        // Getters
        public Double[] getEngagement() {
            return engagement;
        }

        public Long[] getAudienceSize() {
            return audienceSize;
        }

        public Double[] getDemographic() {
            return demographic;
        }

        public double getSumOfEngagement() {
            return sumOfEngagement;
        }

        public double getAvgOfDemographic() {
            return avgOfDemographic;
        }

        public long getSumOfAudienceSize() {
            return sumOfAudienceSize;
        }
    }

    // ----------------------------
    // Niche Methods
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
    // Controlled Stats Update (only Admin)
    // ----------------------------
    public void updateStats(Double[] engagement, Long[] audienceSize, Double[] demographic, User requester) {
        if (!(requester instanceof Admin)) {
            throw new SecurityException("Only Admin can update stats");
        }

        // Temporary Stats to check validity
        double tempSumEngagement = 0;
        double tempSumDemographic = 0;
        long tempSumAudienceSize = 0;

        for (double e : engagement) {
            tempSumEngagement += e;
        }
        for (double d : demographic) {
            tempSumDemographic += d;
        }
        for (long a : audienceSize) {
            tempSumAudienceSize += a;
        }

        if (tempSumEngagement < 0 || tempSumDemographic < 0 || tempSumAudienceSize < 0) {
            throw new IllegalArgumentException("Stats must be positive");
        }

        // Now set if everything is okay
        stats.setEngagement(engagement);
        stats.setAudienceSize(audienceSize);
        stats.setDemographic(demographic);
    }

    // ----------------------------
    // Stats Getters
    // ----------------------------
    public Double[] getEngagement() {
        return stats.getEngagement();
    }

    public Long[] getAudienceSize() {
        return stats.getAudienceSize();
    }

    public Double[] getDemographic() {
        return stats.getDemographic();
    }

    public double getSumOfEngagement() {
        return stats.getSumOfEngagement();
    }

    public double getAvgOfDemographic() {
        return stats.getAvgOfDemographic();
    }

    public long getSumOfAudienceSize() {
        return stats.getSumOfAudienceSize();
    }

    // ----------------------------
    // Other Methods
    // ----------------------------
    public void acceptCampaign(Campaign c) {
        /* Implementation */
    }
}
