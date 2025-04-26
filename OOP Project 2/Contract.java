import java.util.Date;

public class ContractSystem {
    public enum ContractStatus {
        PENDING, ACCEPTED, REJECTED, SUCCEEDED, FAILED
    }

    public static class Contract {
        private static int nextId = 1;
        private final int contractId;
        private final BrandManager brandManager;
        private final Influencer influencer;
        private final Date startDate;
        private final Date endDate;
        private final double paymentAmount;
        private ContractStatus status;

        public Contract(BrandManager brandManager, Influencer influencer,
                      Date startDate, Date endDate, double paymentAmount) {
            this.contractId = nextId++;
            this.brandManager = brandManager;
            this.influencer = influencer;
            this.startDate = startDate;
            this.endDate = endDate;
            this.paymentAmount = paymentAmount;
            this.status = ContractStatus.PENDING;
            
            // Add to both parties' contract arrays
            brandManager.addContract(this);
            influencer.addContract(this);
        }

        // Added getters for all fields
        public BrandManager getBrandManager() {
            return brandManager;
        }

        public Influencer getInfluencer() {
            return influencer;
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void sendToInfluencer() {
            System.out.println("\nContract #" + contractId + " sent to " + influencer.getName());
            influencer.receiveContract(this);
        }

        public void influencerResponds(boolean accepted) {
            if (status != ContractStatus.PENDING) {
                System.out.println("Contract already processed");
                return;
            }

            if (accepted) {
                status = ContractStatus.ACCEPTED;
                System.out.println(influencer.getName() + " accepted contract #" + contractId);
                brandManager.reviewAcceptedContract(this);
            } else {
                status = ContractStatus.REJECTED;
                System.out.println(influencer.getName() + " rejected contract #" + contractId);
            }
        }

        public void brandManagerVerifiesBudget(double availableBudget) {
            if (status != ContractStatus.ACCEPTED) {
                System.out.println("Cannot verify budget - contract not accepted");
                return;
            }

            if (paymentAmount <= availableBudget) {
                status = ContractStatus.SUCCEEDED;
                System.out.println("Contract #" + contractId + " succeeded! Budget approved.");
                // Add to partnered lists if succeeded
                brandManager.addPartneredInfluencer(influencer);
                influencer.addPartneredBrand(brandManager);
            } else {
                status = ContractStatus.FAILED;
                System.out.println("Contract #" + contractId + " failed! Insufficient budget.");
            }
        }

        public void displayContractDetails() {
            System.out.println("\n=== Contract #" + contractId + " ===");
            System.out.println("Brand Manager: " + brandManager.getName());
            System.out.println("Influencer: " + influencer.getName());
            System.out.println("Duration: " + startDate + " to " + endDate);
            System.out.println("Payment: $" + paymentAmount);
            System.out.println("Status: " + status);
        }

        public int getId() { return contractId; }
        public double getPaymentAmount() { return paymentAmount; }
        public ContractStatus getStatus() { return status; }
    }
}
















package com.influencer.platform;

import java.util.Objects;

public class Influencer extends User {
    private Niche[] niches;
    private Stats stats = new Stats();
    private final String name;
    private Contract[] contracts = new Contract[10];
    private BrandManager[] partneredBrands = new BrandManager[10];
    private int contractCount = 0;
    private int brandCount = 0;
    
    public static final String[] VALID_NICHES = { "FASHION", "TECH", "FOOD", "TRAVEL", "GAMING", "FITNESS"};

    public Influencer(String name) {
        this.niches = new Niche[3];
        this.name = name;
    }

    public void addContract(Contract contract) {
        if (contractCount == contracts.length) {
            Contract[] newArray = new Contract[contracts.length * 2];
            System.arraycopy(contracts, 0, newArray, 0, contracts.length);
            contracts = newArray;
        }
        contracts[contractCount++] = contract;
    }

    public void addPartneredBrand(BrandManager brandManager) {
        // Check if already partnered
        for (int i = 0; i < brandCount; i++) {
            if (partneredBrands[i].equals(brandManager)) {
                return;
            }
        }
        
        if (brandCount == partneredBrands.length) {
            BrandManager[] newArray = new BrandManager[partneredBrands.length * 2];
            System.arraycopy(partneredBrands, 0, newArray, 0, partneredBrands.length);
            partneredBrands = newArray;
        }
        
        partneredBrands[brandCount++] = brandManager;
    }

    public Contract[] getContracts() {
        Contract[] result = new Contract[contractCount];
        System.arraycopy(contracts, 0, result, 0, contractCount);
        return result;
    }

    public BrandManager[] getPartneredBrands() {
        BrandManager[] result = new BrandManager[brandCount];
        System.arraycopy(partneredBrands, 0, result, 0, brandCount);
        return result;
    }

    public void receiveContract(Contract contract) {
        System.out.println(name + " received contract #" + contract.getId());
    }

    public void respondToContract(Contract contract, boolean accept) {
        contract.influencerResponds(accept);
    }

    public String getName() { return name; }

    // ----------------------------
    // Nested Stats Class
    // ----------------------------
    public static class Stats {
        private Double[] engagement = new Double[4];
        private Long[] audienceSize = new Long[4];
        private Double[] demographic = new Double[4];
        private double sumOfEngagement = 0;
        private double sumOfDemographic = 0;
        private double avgOfDemographic = 0;
        private long sumOfAudienceSize = 0;

        public Stats() {
            for (int i = 0; i < engagement.length; i++) {
                engagement[i] = 0.0;
                demographic[i] = 0.0;
                audienceSize[i] = 0L;
            }
        }

        private void calculateSums() {
            sumOfEngagement = 0;
            sumOfDemographic = 0;
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

        public Double[] getEngagement() { return engagement; }
        public Long[] getAudienceSize() { return audienceSize; }
        public Double[] getDemographic() { return demographic; }
        public double getSumOfEngagement() { return sumOfEngagement; }
        public double getAvgOfDemographic() { return avgOfDemographic; }
        public long getSumOfAudienceSize() { return sumOfAudienceSize; }
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

    private void validateNiche(String niche) {
        for (String valid : VALID_NICHES) {
            if (valid.equalsIgnoreCase(niche)) {
                return;
            }
        }
        throw new IllegalArgumentException("Invalid niche: " + niche +
               ". Valid niches are: " + java.util.Arrays.toString(VALID_NICHES));
    }

    // ----------------------------
    // Stats Methods
    // ----------------------------
    public void updateStats(Double[] engagement, Long[] audienceSize, Double[] demographic, User requester) {
        if (!(requester instanceof Admin)) {
            throw new SecurityException("Only Admin can update stats");
        }

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

        stats.setEngagement(engagement);
        stats.setAudienceSize(audienceSize);
        stats.setDemographic(demographic);
    }

    public Double[] getEngagement() { return stats.getEngagement(); }
    public Long[] getAudienceSize() { return stats.getAudienceSize(); }
    public Double[] getDemographic() { return stats.getDemographic(); }
    public double getSumOfEngagement() { return stats.getSumOfEngagement(); }
    public double getAvgOfDemographic() { return stats.getAvgOfDemographic(); }
    public long getSumOfAudienceSize() { return stats.getSumOfAudienceSize(); }

    public String getAnalytics() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Engagement: ").append(getSumOfEngagement()).append("\n");
        sb.append("Average Demographic: ").append(getAvgOfDemographic()).append("\n");
        sb.append("Total Audience Size: ").append(getSumOfAudienceSize()).append("\n");
        return sb.toString();
    }

    public void acceptCampaign(Campaign c) {
        /* Implementation */
    }
}
