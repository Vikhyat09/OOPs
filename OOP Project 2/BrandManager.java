package com.influencer.platform;

public class BrandManager extends User {
  private final String name;
        private double availableBudget;
        private Contract[] contracts = new Contract[10];
        private int contractCount = 0;

        public BrandManager(String name, double budget) {
            this.name = name;
            this.availableBudget = budget;
        }

        public void addContract(Contract contract) {
            if (contractCount == contracts.length) {
                Contract[] newArray = new Contract[contracts.length * 2];
                // Replace arraycopy with manual copy
                for (int i = 0; i < contracts.length; i++) {
                    newArray[i] = contracts[i];
                }
                contracts = newArray;
            }
            contracts[contractCount++] = contract;
        }

        public Contract[] getContracts() {
            Contract[] result = new Contract[contractCount];
            // Replace arraycopy with manual copy
            for (int i = 0; i < contractCount; i++) {
                result[i] = contracts[i];
            }
            return result;
        }


  
public Influencer[] ParteneredInfluencers(Influencer Influencer) { /* Setup new campaign */ }

}
