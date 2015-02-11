package auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;


/**
*
* @author roberto
*/
public class FacebookService {

    public FacebookService() { }
    
    public FacebookInfo getInfoByToken(String token) throws FacebookErrorException {
    	
    	String url = Settings.BASE_PROTOCOL + Settings.FB_BASE_URL + Settings.FB_BASE_PORT + Settings.FB_BASE_PATH + token;
    	
    	FacebookInfo fi = new FacebookInfo();
        
        try {
        	
        	System.out.println("Request:" + url);
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
            
            JSONObject fb_serv_response = new JSONObject(response.toString());
            
            int code = fb_serv_response.getJSONObject(Settings.FB_JSON_OUT_STATUS_OBJ).getInt(Settings.FB_JSON_OUT_STATUS_CODE_ATTR);
            String message = fb_serv_response.getJSONObject(Settings.FB_JSON_OUT_STATUS_OBJ).getString(Settings.FB_JSON_OUT_STATUS_MESSAGE_ATTR);
            
            if(code == Settings.FB_OK_REQ) {
            	Long id = fb_serv_response.getLong(Settings.FB_JSON_OUT_ID_ATTR);
                String first_name = fb_serv_response.getString(Settings.FB_JSON_OUT_NAME_ATTR);
                String location = "Trento";

                if(fb_serv_response.has(Settings.FB_JSON_OUT_LOCATION_ATTR))
                    location = fb_serv_response.getString(Settings.FB_JSON_OUT_LOCATION_ATTR);
                fi.setId(id);
                fi.setFirst_name(first_name);
                fi.setLocation(location);
            }
            else {
            	throw new FacebookErrorException(Settings.FB_ERR_REQ, message);
            }
	       
        } catch (Exception excep) {
        	
        	System.err.println("Exception catched, " + excep.toString());
        	throw new FacebookErrorException(Settings.FB_ERR_REQ, Settings.FB_ERR_MESSAGE);
        	
        }
        
        return fi;
    }
}