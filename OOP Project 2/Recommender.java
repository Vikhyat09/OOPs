package com.influencer.platform;

public interface Recommender {
    List<Influencer> recommend(String category);  // Basic recommendation
    List<Influencer> recommend(String category, Double maxBudget);  // Overloaded
}

