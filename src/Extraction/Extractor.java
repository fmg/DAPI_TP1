package Extraction;

import CommonLibrary.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fernando Graças
 */
public class Extractor {
    
    private int dataSetSize;
    private Set<Integer> storedIDs;
    private ArrayList<Integer> queuedMovieIDs;


    public Extractor(int size) {
        dataSetSize = size;
        storedIDs = new TreeSet<Integer>();
        queuedMovieIDs = new ArrayList<Integer>();
    }
    
    
    
    private boolean hasMovieID(int id){
        return storedIDs.contains(id);
    }
    
    
    private void sendMovieToXMLFile(Movie m){
        
    }
    
    
    public void extract(){
        
        
    }
    
    private ArrayList<Movie> extractMoviesFromJSON(JsonArray mArray){
        return null;
        
    }
    
    public void test1(){
        
        String movieJSON = CommAPI.getMovie(770672122);
        Gson gson = new GsonBuilder().create();
        Movie m = gson.fromJson(movieJSON, Movie.class);
        m.getReviews();
        ArrayList<Integer> sim = m.getSimilar();
        
        
        
        
        /*
        FileWriter fstream;
        BufferedWriter out = null;
        try {
            fstream = new FileWriter("out.txt");
            out = new BufferedWriter(fstream);
            out.write(xml);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Extractor.class.getName()).log(Level.SEVERE, null, ex);
        }   
        */
    }
    
    
    public static void main(String[] args){
        
        Extractor extr = new Extractor(1);
        extr.test1();
        
        
        
    }
}
