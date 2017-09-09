/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donorschooseapi;

import Entities.proposals;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author miriam
 */
public class DonorsChooseApi {

    //private static String pageUrl = "https://api.donorschoose.org/common/json_feed.html?subject1=-1";
    private static String pageUrl = "https://api.donorschoose.org/common/json_feed.html?keywords=";
    private static final String ApiKey = "DONORSCHOOSE";
    private static final String fundingCost = "&costToCompleteRange=0+TO+2000";
    private static final String state = "&state=CA";
    private static final String sort = "&sortBy=0";
    private static final String resultMax = "&max=5";
    private static final int ownThread = 18;
    private static final int numberRows = 5;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ExecutorService executor = Executors.newFixedThreadPool(ownThread);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean requestComplete = false;        
        System.out.println("Search value :");
        String searchValue = reader.readLine();
        if(searchValue.length() >0 )
        {
            Runnable runnable = new CallRequestRunnable(searchValue);
            executor.execute(runnable);
            
            executor.shutdown();            
        }
        else
            System.out.println("Need value to search!");        
    }
    
    private static class CallRequestRunnable implements Runnable{
        
        private String searchValue;
        
        CallRequestRunnable(String _searchValue)
        {
            this.searchValue = _searchValue;
        }
        
        @Override
        public void run()
        {
            //boolean requestComplete = false;
            try {
                String apiPage = pageUrl+ searchValue +fundingCost +state+sort+resultMax+"&APIKey="+ApiKey;
                URL url = new URL(apiPage);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
            
                int codeResponse = conn.getResponseCode();
                if(codeResponse != 200)
                {
                    System.out.println("Can not response server page!");
                    return;
                }
           
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();
                                        
                while((line = reader.readLine()) != null)
                {
                    response.append(line);
                }
            
                reader.close();            
                String json = response.toString();
                //System.out.println(response.toString());
                              
                ObjectMapper mapper = new ObjectMapper();
                
                JsonNode rootNode = mapper.readTree(json);
                JsonNode nodeProposals = rootNode.path("proposals");
                Iterator<JsonNode> elements = nodeProposals.elements();
                float percentFundedAvg=0,numberDonosAvg=0,costCompleteAvg=0,numberStudentsAvg=0, totalPriceAvg=0;  //= Float.parseFloat("25");
                
                while(elements.hasNext()){
                    JsonNode lineNode = elements.next();
                    
                    proposals proposalsObj = mapper.treeToValue(lineNode, proposals.class);
                    System.out.println( "TITLE : " +proposalsObj.getTitle() + " SHORT DESCRIPTION : " + proposalsObj.getShortDescription() + " PROPOSALURL : "+proposalsObj.getProposalURL() + " COST TO COMPLETE : "+proposalsObj.getCostToComplete());
                    percentFundedAvg += Float.parseFloat(proposalsObj.getPercentFunded());
                    numberDonosAvg += Float.parseFloat(proposalsObj.getNumDonors());
                    costCompleteAvg += Float.parseFloat(proposalsObj.getCostToComplete());
                    numberStudentsAvg += Float.parseFloat(proposalsObj.getNumStudents());
                    totalPriceAvg += Float.parseFloat(proposalsObj.getTotalPrice());                     
                }
                
                if(percentFundedAvg  > 0)
                    percentFundedAvg = percentFundedAvg / numberRows;
                
                if(numberDonosAvg  > 0)
                    numberDonosAvg = numberDonosAvg / numberRows;
                
                if(costCompleteAvg  > 0)
                    costCompleteAvg = costCompleteAvg / numberRows;
                
                if(numberStudentsAvg  > 0)
                    numberStudentsAvg = numberStudentsAvg / numberRows;   
                
                if(totalPriceAvg  > 0)
                    totalPriceAvg = totalPriceAvg / numberRows;
                
                System.out.println("");
                System.out.println("Avg Percent Funded  : " +percentFundedAvg);
                System.out.println("Avg Number Donors : " +numberDonosAvg);
                System.out.println("Avg Cost Complete : " +costCompleteAvg);
                System.out.println("Avg Number Students : " +numberStudentsAvg);
                System.out.println("Avg Total Price : " +totalPriceAvg);
                
                System.out.println("");
                System.out.println("Finish Response");
            
            } catch (Exception ex) {
                System.out.println("Error search "+searchValue+ " :" + ex.getMessage());
            }         
        }
    }     
}