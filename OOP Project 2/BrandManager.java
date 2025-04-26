package com.influencer.platform;

import java.util.Date;

public class BrandManager extends User {
    private final String name;
    private double availableBudget;
    private Contract[] contracts = new Contract[10]; // Initial capacity
    private Influencer[] partneredInfluencers = new Influencer[10]; // Influencers with successful contracts
    private int contractCount = 0;
    private int influencerCount = 0;
    
    public static final String[] VALID_NICHES = { "FASHION", "TECH", "FOOD", "TRAVEL", "GAMING", "FITNESS"};

    public BrandManager(String name, double budget) {
        this.name = name;
        this.availableBudget = budget;
    }

    public Contract createContract(Influencer influencer, 
                                 Date start, Date end,
                                 double amount) {
        Contract newContract = new Contract(this, influencer, start, end, amount);
        // The contract constructor automatically adds it to both parties' contract arrays
        return newContract;
    }

    public void addContract(Contract contract) {
        // Resize array if needed
        if (contractCount == contracts.length) {
            Contract[] newArray = new Contract[contracts.length * 2];
            System.arraycopy(contracts, 0, newArray, 0, contracts.length);
            contracts = newArray;
        }
        contracts[contractCount++] = contract;
    }

    public void reviewAcceptedContract(Contract contract) {
        if (contract.getStatus() != ContractSystem.ContractStatus.ACCEPTED) {
            System.out.println("Cannot review - contract not in ACCEPTED state");
            return;
        }

        if (contract.getPaymentAmount() <= availableBudget) {
            availableBudget -= contract.getPaymentAmount();
            contract.brandManagerVerifiesBudget(availableBudget + contract.getPaymentAmount()); // Pass original budget
            
            // Add to partnered influencers if not already there
            boolean alreadyPartnered = false;
            for (int i = 0; i < influencerCount; i++) {
                if (partneredInfluencers[i].equals(contract.getInfluencer())) {
                    alreadyPartnered = true;
                    break;
                }
            }
            
            if (!alreadyPartnered) {
                // Resize array if needed
                if (influencerCount == partneredInfluencers.length) {
                    Influencer[] newArray = new Influencer[partneredInfluencers.length * 2];
                    System.arraycopy(partneredInfluencers, 0, newArray, 0, partneredInfluencers.length);
                    partneredInfluencers = newArray;
                }
                partneredInfluencers[influencerCount++] = contract.getInfluencer();
            }
        } else {
            contract.brandManagerVerifiesBudget(availableBudget);
        }
    }

    public Contract[] getContracts() {
        Contract[] result = new Contract[contractCount];
        System.arraycopy(contracts, 0, result, 0, contractCount);
        return result;
    }

    public Influencer[] getPartneredInfluencers() {
        Influencer[] result = new Influencer[influencerCount];
        System.arraycopy(partneredInfluencers, 0, result, 0, influencerCount);
        return result;
    }

    public String getName() { 
        return name; 
    }
    
    public double getAvailableBudget() { 
        return availableBudget; 
    }

    // Helper method to get the influencer from a contract (assuming Contract class has this method)
    private Influencer getInfluencerFromContract(Contract contract) {
        // This would depend on your Contract class implementation
        // Assuming there's a getInfluencer() method in Contract
        return contract.getInfluencer();
    }
}
