package com.influencer.platform;

public class Campaign {
    // Overloaded constructors
    public Campaign(String name) { /* Minimal campaign */ }
    public Campaign(String name, Double budget, LocalDate endDate) { /* Full setup */ }
    
    // Varargs method
    public void addInfluencers(Influencer... influencers) { /* Add multiple influencers */ }
    
    public void trackPerformance() { /* Update metrics */ }
}
