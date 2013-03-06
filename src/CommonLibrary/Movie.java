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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
/**
 *
 * @author Desktop
 */
public class Movie implements Serializable{
    private String id;
    private String title;
    private int year;
    private ArrayList<String> genres;
    private String mpaa_rating;
    private String runtime;
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
    
    public String getId() {
        return id;
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
    
    
    public String toXML() throws Exception{
       
        try {
            
            //basic xml document for input
            String fakeXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><movie></movie>";
            InputStream is = new ByteArrayInputStream(fakeXML.getBytes());
            StringWriter writer = new StringWriter();
            
            
            //Create the documentBuilderFactory and documentBuilder
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(is);
            
            //Get the root element of the xml Document;
            Element documentElement = document.getDocumentElement();

            
            //ID
            Element idNode = document.createElement("id");
            idNode.setTextContent(id);
            documentElement.appendChild(idNode);
            
            //title
            Element titleNode = document.createElement("title");
            titleNode.setTextContent(title);
            documentElement.appendChild(titleNode);
            
            //year
            Element yearNode = document.createElement("year");
            yearNode.setTextContent(String.valueOf(year));
            documentElement.appendChild(yearNode);
            
            //genres
            Element genresNode = document.createElement("genres");
            for(String g: genres){
                Element genreNode = document.createElement("genre");
                genreNode.setTextContent(g);
                genresNode.appendChild(genreNode);
            }
            documentElement.appendChild(genresNode);
            
            //mpaa_rating
            Element mpaaNode = document.createElement("mpaa_rating");
            mpaaNode.setTextContent(String.valueOf(mpaa_rating));
            documentElement.appendChild(mpaaNode);

            //runtime
            Element runtimeNode = document.createElement("runtime");
            if(runtime == null){
                runtimeNode.setTextContent(new String("-"));
            }else{
                runtimeNode.setTextContent(runtime);
            }
            documentElement.appendChild(runtimeNode);
            
            //runtime
            Element critics_consensusNode = document.createElement("critics_consensus");
            critics_consensusNode.setTextContent(String.valueOf(critics_consensus));
            documentElement.appendChild(critics_consensusNode);
            
            //ratings
            Element ratingsNode = document.createElement("ratings");
            ratings.toXML(ratingsNode, document);
            documentElement.appendChild(ratingsNode);
            
            //synopsis
            Element synopsisNode = document.createElement("synopsis");
            if(synopsis == null){
                System.out.println("Synopsis missing");
                throw new Exception();
            }
            synopsisNode.setTextContent(synopsis);
            documentElement.appendChild(synopsisNode);
            
            //abridged_cast
            Element abridged_castNode = document.createElement("abridged_cast");
            for(Cast cast: abridged_cast){
                cast.toXML(abridged_castNode, document);
            }
            documentElement.appendChild(abridged_castNode);
            
            
            //abridged_directors
            Element abridged_directorsNode = document.createElement("abridged_directors");
            if(abridged_directors != null){
                for(Director dir: abridged_directors){
                    dir.toXML(abridged_directorsNode, document);
                }
            }else{
                Element directorNode = document.createElement("director");
      
                //name
                Element dirNameNode = document.createElement("name");
                dirNameNode.setTextContent(new String("-"));
                directorNode.appendChild(dirNameNode);


                //append director to directorsNode
                abridged_directorsNode.appendChild(directorNode);
            }
            documentElement.appendChild(abridged_directorsNode);
            
            //studio
            Element studioNode = document.createElement("studio");
            studioNode.setTextContent(studio);
            documentElement.appendChild(studioNode);
            
            
            //reviews
            Element reviewsNode = document.createElement("reviews");
            for(Review rev: reviews){
                rev.toXML(reviewsNode, document);
            }
            documentElement.appendChild(reviewsNode);
            
            
            
            //  Set output file to xml
            // Ignore XML header
            Transformer tFormer = TransformerFactory.newInstance().newTransformer();       
            //tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
            tFormer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            tFormer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            //  Write the node to the string
            Source source = new DOMSource(documentElement);
            Result result = new StreamResult(writer);
            tFormer.transform(source, result);
            writer.close();
            
            return writer.toString();
            
        } catch (SAXException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
}
