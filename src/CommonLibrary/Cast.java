/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonLibrary;

import java.io.Serializable;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Desktop
 */
public class Cast implements Serializable{
    String id;
    String name;
    ArrayList<String> characters;

    public Cast() {
        characters = new ArrayList<String>();
    }
    
    
    public void toXML(Element abridged_castNode, Document document){
        
        Element castNode = document.createElement("cast");
        
        
        
        //id
        Element idNode = document.createElement("id");
        idNode.setTextContent(String.valueOf(id));
        castNode.appendChild(idNode);
        
        //name
        Element nameNode = document.createElement("name");
        nameNode.setTextContent(name);
        castNode.appendChild(nameNode);
        
        
         //audience_rating
        Element charactersNode = document.createElement("characters");
        for(String ch: characters){
            Element characterNode = document.createElement("character");
            characterNode.setTextContent(ch);
            charactersNode.appendChild(characterNode);
        }
        castNode.appendChild(charactersNode);
        
        
        //append cast to abriged_castNode
        abridged_castNode.appendChild(castNode);

    }
    
}
