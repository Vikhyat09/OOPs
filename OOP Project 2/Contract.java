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
