package auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;


/**
*
* @author roberto
*/
public class FacebookService {

    public FacebookService() { }
    
    public FacebookInfo getInfoByToken(String token) throws MalformedURLException, IOException, JSONException {
    	
    	String url = Settings.BASE_PROTOCOL + Settings.FB_BASE_URL + Settings.FB_BASE_PORT + Settings.FB_BASE_PATH + token;
    	
    	URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod(Settings.REQ_TYPE);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
        }
        in.close();
        
        JSONObject o = new JSONObject(response.toString());
        
        FacebookInfo fi = new FacebookInfo();
        String id = o.getString("id");
        String first_name = o.getString("first_name");
        String location = o.getString("location");
        fi.setId(id);
        fi.setFirst_name(first_name);
        fi.setLocation(location);
        return fi;
    }
}