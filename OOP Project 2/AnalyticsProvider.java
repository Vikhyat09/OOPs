package com.influencer.platform;

/**
 * Provides analytics functionality
 */
public interface AnalyticsProvider {

     public double totalEngagementBrand(BrandManager man){
          double totalengagement;
          Influencer[] newArray=Arrays.copyof(man.getParteneredInfluencers,man.getParteneredInfluencers.length);
          for(int i =0;i<man.getParteneredInfluencers.length;i++){
               totalengagement+=newArray[i].getSumOfEngagement;
          }
          return totalengagement;
     }

       public double avgDemoBrand(BrandManager man){
          double Demo;
          Influencer[] newArray=Arrays.copyof(man.getParteneredInfluencers,man.getParteneredInfluencers.length);
          for(int i =0;i<man.getParteneredInfluencers.length;i++){
               Demo+=newArray[i].getAvgOfDemographic;
          }
            Demo=Demo/newArray.length;
          return Demo;
     }

       public double totalBrandAudience(BrandManager man){
          double Aud;
          Influencer[] newArray=Arrays.copyof(man.getParteneredInfluencers,man.getParteneredInfluencers.length);
          for(int i =0;i<man.getParteneredInfluencers.length;i++){
               Aud+=newArray[i].getSumOfAudienceSize;
          }
          return Aud;
     }

     public double twitterEngagementBrand(BrandManager man){
          double totalengagement;
          Influencer[] newArray=Arrays.copyof(man.getParteneredInfluencers,man.getParteneredInfluencers.length);
          for(int i =0;i<man.getParteneredInfluencers.length;i++){
               totalengagement+=newArray[i].Stats.engagement[1];
          }
          return totalengagement;
     }
     

     
               
               
          
     // Campaign performance stats
}
