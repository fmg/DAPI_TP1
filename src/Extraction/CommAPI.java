package Extraction;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author Fernando Graças
 */
public class CommAPI {
        
    
    final private static String API_KEY = "mghbme9b435fytzmp83ngcm3";
    
    public static String getBoxOfficeMovies(int number){

        int num = 50;
        if(number < 50)
            num = number;
        
        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=" + API_KEY +"&limit="+num;
        
        return executeHTTPGet(url);
        
    }
    
    
    public static String getTopRentals(int number){

        int num = 50;
        if(number < 50)
            num = number;
        
        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/top_rentals.json?apikey=" + API_KEY +"&limit="+num;
        
        return executeHTTPGet(url);
        
    }
    
    
    public static String getCurrentReleases(int number){
        
        int num = 50;
        if(number < 50)
            num = number;
        
        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/current_releases.json?apikey=" + API_KEY +"&limit="+num;
        
        return executeHTTPGet(url);
    }
    
    
    public static String getMovie(String ID){
                
        String url = "http://api.rottentomatoes.com/api/public/v1.0/movies/"+ ID+".json?apikey=" + API_KEY;

        return executeHTTPGet(url);
 
    }
    
    
    public static String getReviewsFromMovie(String ID){
        
        String url = "http://api.rottentomatoes.com/api/public/v1.0/movies/"+ID+"/reviews.json?apikey=" + API_KEY +"&review_type=top_critic&page_limit=20&country=us";

        return executeHTTPGet(url);

    }
    
    
    public static String getSimilarMovies(String ID){
        
        String url = "http://api.rottentomatoes.com/api/public/v1.0/movies/"+ID+"/similar.json?apikey=" + API_KEY;

        return executeHTTPGet(url);
    }
    
    
    
    static private String executeHTTPGet(String url){
         try {
             System.out.println("Request URL-> " + url);
            //prepare HTTP request
            String result = null;
            
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                            sb.append(line);
                    }

                    result=sb.toString();

                    if(result==null)
                        return null;


                    System.out.println("response JSON ->" + result);

                } finally {
                    instream.close();
                }
                
                return result;
            }
        } catch (IOException ex) {
            Logger.getLogger(CommAPI.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }
}
