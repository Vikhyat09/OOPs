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
            
            brandManager.addContract(this);
            influencer.addContract(this);
        }

        // ... (other Contract methods remain the same)
    }
