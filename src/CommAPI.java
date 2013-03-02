
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author Fernando Graças
 */
public class CommAPI {
    
    final static String API_KEY = "mghbme9b435fytzmp83ngcm3";
    
    public void doSomething(){
        try {
            //prepare HTTP request
            
            Gson gson = new GsonBuilder().create();
            
            
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://api.rottentomatoes.com/api/public/v1.0.json?apikey=" + API_KEY);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                            sb.append(line);
                    }

                    String result=sb.toString();

                    if(result==null)
                        return;


                    System.out.println("response JSON ->" + result);
                    
                    
                    
                } finally {
                    instream.close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CommAPI.class.getName()).log(Level.SEVERE, null, ex);
        } 

        
    }
    
    
    
}
