/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author miriam
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class proposals {
    @JsonProperty("title")
    private String Title;
    
    @JsonProperty("proposalURL")
    private String ProposalURL;
    
    @JsonProperty("shortDescription")
    private String ShortDescription;
    
    @JsonProperty("costToComplete")
    private String CostToComplete;
    
    @JsonProperty("totalPrice")
    private String TotalPrice;
    
    @JsonProperty("percentFunded")
    private String PercentFunded;
    
    @JsonProperty("numDonors")
    private String NumDonors;
    
    @JsonProperty("numStudents")
    private String NumStudents;
    
    public String getTitle() {
	return Title;
    }
    
    public void setTitle(String title) {
	this.Title = title;
    }
        
    public String getProposalURL() {
	return ProposalURL;
    }

    public void setProposalURL(String proposalURL) {
	this.ProposalURL = proposalURL;
    }
    
     public String getShortDescription() {
	return ShortDescription;
    }

    public void setShortDescription(String ShortDescription) {
	this.ShortDescription = ShortDescription;
    }
    
        
     public String getCostToComplete() {
	return CostToComplete;
    }

    public void setCostToComplete(String costToComplete) {
	this.CostToComplete = costToComplete;
    }
    
    public String getTotalPrice() {
	return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
	this.TotalPrice = TotalPrice;
    }
    
    public String getPercentFunded() {
	return PercentFunded;
    }

    public void setPercentFunded(String PercentFunded) {
	this.PercentFunded = PercentFunded;
    }
        
    public String getNumDonors() {
	return NumDonors;
    }

    public void setNumDonors(String NumDonors) {
	this.NumDonors = NumDonors;
    }
    
    public String getNumStudents() {
	return NumStudents;
    }

    public void setNumStudents(String NumStudents) {
	this.NumStudents = NumStudents;
    }
     
}