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
public class Review implements Serializable{
    private String critic;
    private String date;
    private String original_score;
    private String freshness;
    private String publication;
    private String quote;

    public Review() {
    }
    
    
    public void toXML(Element reviewsNode, Document document){
        
        Element reviewNode = document.createElement("review");
        
        //critic
        Element criticNode = document.createElement("critic");
        criticNode.setTextContent(critic);
        reviewNode.appendChild(criticNode);
        
        //date
        Element dateNode = document.createElement("date");
        dateNode.setTextContent(date);
        reviewNode.appendChild(dateNode);
        
        //original_score
        Element original_scoreNode = document.createElement("original_score");
        if(original_score == null || original_score.equals(""))
            original_scoreNode.setTextContent(new String("-"));
        else
            original_scoreNode.setTextContent(original_score);
        reviewNode.appendChild(original_scoreNode);
        
        //freshness
        Element freshnessNode = document.createElement("freshness");
        freshnessNode.setTextContent(freshness);
        reviewNode.appendChild(freshnessNode);
        
        //publication
        Element publicationNode = document.createElement("publication");
        publicationNode.setTextContent(publication);
        reviewNode.appendChild(publicationNode);
        
        
        //quote
        Element quoteNode = document.createElement("quote");
        quoteNode.setTextContent(quote);
        reviewNode.appendChild(quoteNode);

        //append review to reviewsNode
        reviewsNode.appendChild(reviewNode);

    }
    
}
