/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonLibrary;

import java.io.Serializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Desktop
 */
public class Rating implements Serializable{
    private String critics_rating;
    private int critics_score;
    private String audience_rating;
    private int audience_score;

    public Rating() {
    }
    
    public void toXML(Element ratingsNode, Document document){
        
        //critics_rating
        Element critics_ratingNode = document.createElement("critics_rating");
        critics_ratingNode.setTextContent(critics_rating);
        ratingsNode.appendChild(critics_ratingNode);
        
        //critics_score
        Element critics_scoreNode = document.createElement("critics_score");
        critics_scoreNode.setTextContent(String.valueOf(critics_score));
        ratingsNode.appendChild(critics_scoreNode);
        
        
         //audience_rating
        Element audience_ratingNode = document.createElement("audience_rating");
        audience_ratingNode.setTextContent(audience_rating);
        ratingsNode.appendChild(audience_ratingNode);
        
        //critics_score
        Element audience_scoreNode = document.createElement("audience_score");
        audience_scoreNode.setTextContent(String.valueOf(audience_score));
        ratingsNode.appendChild(audience_scoreNode);
        
        
    }
    
}
