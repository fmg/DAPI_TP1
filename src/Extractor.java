
import java.util.AbstractSet;
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
    
    private Set<Integer> storedIDs;

    public Extractor() {
    
        storedIDs = new TreeSet<Integer>();
    }
    
    private boolean hasMovieID(int id){
        return storedIDs.contains(id);
    }
    
    
    
    
    
    public static void main(String[] args){
        
        CommAPI api = new CommAPI();
        api.doSomething();
        
        
    }
}
