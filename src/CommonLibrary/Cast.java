/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonLibrary;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Desktop
 */
public class Cast implements Serializable{
    String name;
    ArrayList<String> characters;

    public Cast() {
        characters = new ArrayList<String>();
    }
    
    
}
