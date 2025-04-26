package com.influencer.platform;

public class BrandManager extends User {

 private final String name;
        private double availableBudget;
        private Contract[] contracts = new Contract[10]; // Initial capacity
        private int contractCount = 0;
        public static final String[] VALID_NICHES = { "FASHION", "TECH", "FOOD", "TRAVEL", "GAMING", "FITNESS"};

        public BrandManager(String name, double budget) {
            this.name = name;
            this.availableBudget = budget;
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

        public Contract[] getContracts() {
            Contract[] result = new Contract[contractCount];
            System.arraycopy(contracts, 0, result, 0, contractCount);
            return result;
        }

        public Contract createContract(Influencer influencer, 
                                    Date start, Date end,
                                    double amount) {
            return new Contract(this, influencer, start, end, amount);
        }

        public void reviewAcceptedContract(Contract contract) {
            System.out.println("\n" + name + " reviewing accepted contract #" + contract.getId());
            System.out.println("Available budget: $" + availableBudget);
            System.out.println("Contract amount: $" + contract.getPaymentAmount());
            
            contract.brandManagerVerifiesBudget(availableBudget);
            
            if (contract.getStatus() == ContractStatus.SUCCEEDED) {
                availableBudget -= contract.getPaymentAmount();
            }
        }

        public String getName() { return name; }
        public double getAvailableBudget() { return availableBudget; }


  
public Influencer[] ParteneredInfluencers(Influencer Influencer) { /* Setup new campaign */ }

}
