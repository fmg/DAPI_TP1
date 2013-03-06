package Extraction;

import CommonLibrary.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fernando Graças
 */
public class Extractor {
    
    
    private final static String XML_HEADER = "<?xml version=\"10\" encoding=\"UTF-8\"?>\n";
    
    private File xmlFile;
    private int dataSetSize;
    private Set<Integer> storedIDs;
    private Set<Integer> queuedMovieIDs;


    public Extractor(int size, String file) {
        dataSetSize = size;
        storedIDs = new TreeSet<Integer>();
        queuedMovieIDs = new TreeSet<Integer>();
        xmlFile = new File(file);
    }

    
    private void extractMovies(String receivedJSON, FileWriter fileWriter) throws IOException{
        
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(receivedJSON).getAsJsonObject();
        JsonArray movs=  root.getAsJsonArray("movies");
        
        ArrayList<Movie> movies = new ArrayList<Movie>();
        
        Type fooType = new TypeToken<ArrayList<Movie>>() {}.getType();
        movies = gson.fromJson(movs.toString(), fooType);
        
        for(Movie m: movies){
            
            int mID = Integer.parseInt(m.getId());
            
            if(!storedIDs.contains(mID)){
            
                String fullInfo = CommAPI.getMovie(m.getId());
                m = gson.fromJson(fullInfo, Movie.class);
                m.getReviews();
                ArrayList<Integer> sim = m.getSimilar();

                for(Integer i: sim){
                    queuedMovieIDs.add(i);
                }
                
                String movieXML;
                try {
                    movieXML = m.toXML();
                    fileWriter.write(movieXML);
                
                    storedIDs.add(mID);
                } catch (Exception ex) {
                    System.out.println("\n\nERRO A OBTER FILME /FILME SEM SYNOPSYS " + mID);
                }
                
            }else{
                System.out.println("\n\nDuplicate detected ->" + mID + "\n\n");

            }
                
            
        }
        
        System.out.println("Movies->" + storedIDs.size() + " ; Similar-> " + queuedMovieIDs.size());
        
    }
    
     
    
    private Set<Integer> extractQueuedMovies(FileWriter fileWriter) throws IOException{
        
        Set<Integer> newQueuedMovies = new TreeSet<Integer>();
        
        Gson gson = new GsonBuilder().create();
        
        for(Integer i: queuedMovieIDs){
            
            if(!storedIDs.contains(i)){
                String movJSON = CommAPI.getMovie(i.toString());
                try{
                    Movie movie = gson.fromJson(movJSON, Movie.class);
                    movie.getReviews();
                    ArrayList<Integer> similars = movie.getSimilar();

                    for(Integer simID: similars){
                        if(!storedIDs.contains(simID)){
                            if(!queuedMovieIDs.contains(simID)){
                                newQueuedMovies.add(simID);
                            }
                        }
                    }


                    fileWriter.write(movie.toXML());
                    storedIDs.add(i);
                } catch (Exception ex) {
                    System.out.println("\n\nERRO A OBTER FILME /FILME SEM SYNOPSYS " + i);
                }
                
            }else{
                System.out.println("\n\nDuplicate detected ->" + i.toString() + "\n\n");
            }
            
        }
        
        return newQueuedMovies;
    }
    
    public void extract(){
        
        int moviesLeft = dataSetSize;
        
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(xmlFile);
            
            fileWriter.write(XML_HEADER);
            fileWriter.write("<movies>\n");
        
            System.out.println("BOX OFFICE MOVIES");
            String movies = CommAPI.getBoxOfficeMovies(moviesLeft);
            extractMovies(movies, fileWriter);
            
            moviesLeft -= storedIDs.size();
            
            if(moviesLeft == 0){
                fileWriter.write("</movies>");
                fileWriter.close();
                return;
            }
            
            
            System.out.println("TOP RENTALS");
            movies = CommAPI.getTopRentals(moviesLeft);
            extractMovies(movies, fileWriter);
            
            moviesLeft = dataSetSize - storedIDs.size();
            
            if(moviesLeft == 0){
                fileWriter.write("</movies>");
                fileWriter.close();
                return;
            }
            
            System.out.println("CURRENT RELEASES");
            movies = CommAPI.getCurrentReleases(moviesLeft);
            extractMovies(movies, fileWriter);
            
             moviesLeft = dataSetSize - storedIDs.size();
            
            if(moviesLeft == 0){
                fileWriter.write("</movies>");
                fileWriter.close();
                return;
            }

            
            System.out.println("QUEUED MOVIES");
            System.out.println("Movies left-> " + moviesLeft + "; Movies in queue->" + queuedMovieIDs.size());
            do{
                queuedMovieIDs = extractQueuedMovies(fileWriter);
                 moviesLeft = dataSetSize - storedIDs.size();
                System.out.println("Movies left-> " + moviesLeft + "; Movies aquired->" + queuedMovieIDs.size());
                
            }while(queuedMovieIDs.size() > 0 && dataSetSize > storedIDs.size());
            
            System.out.println("NO MORE MOVIES");


            
            
            
            fileWriter.write("</movies>");
            fileWriter.close();
            
            
            System.out.println("Movies->" + storedIDs.size());
            
        } catch (IOException ex) {
            Logger.getLogger(Extractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }
    
    
    public static void main(String[] args){
        
        if(args.length != 2){
            System.out.println("u need to specify the size of the data set and the output file.\n"
                    + "Example: java extractor 100 movies.xml");
        }
        
        int datasize = Integer.parseInt(args[0]);
        String file = args[1];
        
        Extractor extr = new Extractor(datasize, file);
        //extr.test1();
        extr.extract();
        
        
        
    }
}
