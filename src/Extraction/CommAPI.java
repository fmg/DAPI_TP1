package Extraction;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
    
    public static String getBoxOffice(int number){

        try {
            //prepare HTTP request
            
            
            String result = null;
            
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://api.rottentomatoes.com/api/public/v1.0.json?apikey=" + API_KEY);
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
    
    
    public static String getMovie(String ID){
        try {
            //prepare HTTP request
            
            
            String result = null;
            
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://api.rottentomatoes.com/api/public/v1.0/movies/"+ ID+".json?apikey=" + API_KEY);
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
    
    
    public static String getReviewsFromMovie(String ID){
        

        try {
            //prepare HTTP request
            
            
            String result = null;
            
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://api.rottentomatoes.com/api/public/v1.0/movies/"+ID+"/reviews.json?apikey=" + API_KEY +"&review_type=top_critic&page_limit=20&country=us");
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
    
    
    public static String getSimilarMovies(String ID){
        

        try {
            //prepare HTTP request
            
            
            String result = null;
            
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://api.rottentomatoes.com/api/public/v1.0/movies/"+ID+"/similar.json?apikey=" + API_KEY);
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
