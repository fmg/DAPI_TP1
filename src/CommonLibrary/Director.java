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
public class Director implements Serializable{
    String name;

    public Director() {
    }
    
    public void toXML(Element directorsNode, Document document){
        
        Element directorNode = document.createElement("director");
      
        //name
        Element nameNode = document.createElement("name");
        nameNode.setTextContent(name);
        directorNode.appendChild(nameNode);

        
        //append director to directorsNode
        directorsNode.appendChild(directorNode);

    }
    
}
