/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonLibrary;

import Extraction.CommAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
/**
 *
 * @author Desktop
 */
public class Movie implements Serializable{
    private int id;
    private String title;
    private int year;
    private ArrayList<String> genres;
    private String mpaa_rating;
    private int runtime;
    private String critics_consensus;
    private Rating ratings;
    private String synopsis; 
    private ArrayList<Cast> abridged_cast;
    private ArrayList<Director> abridged_directors;
    private String studio;
    private ArrayList<Review> reviews;
   
    

    public Movie() {
        genres = new ArrayList<String>();
        abridged_cast = new ArrayList<Cast>();
        reviews = new ArrayList<Review>();
    }
    
    
    public void getReviews(){
        String reviewsJSON = CommAPI.getReviewsFromMovie(id);
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(reviewsJSON).getAsJsonObject();
        JsonArray rev=  root.getAsJsonArray("reviews");
        
        Type fooType = new TypeToken<ArrayList<Review>>() {}.getType();
        reviews = gson.fromJson(rev.toString(), fooType);
        
    }
    
    
    public ArrayList<Integer> getSimilar(){
        
        ArrayList<Integer> similarMovies = new ArrayList<Integer>();
        
        String similarJSON = CommAPI.getSimilarMovies(id);
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(similarJSON).getAsJsonObject();
        JsonArray sim=  root.getAsJsonArray("movies");
        
        for(int i = 0; i < sim.size(); i++){
            JsonObject ob = sim.get(i).getAsJsonObject();
            int simMovID = ob.get("id").getAsInt();
            similarMovies.add(simMovID);
        }
        
        
        return similarMovies;
        
    }
    
    
    public String toXML(){
        String ret = "";
        
        
        return ret;
    }
    
}
