package document.ws;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jws.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import auth.FacebookErrorException;
import auth.FacebookInfo;
import auth.FacebookService;
import auth.Settings;
import document.model.Person;

//Service Implementation

@WebService(endpointInterface = "document.ws.People",
        serviceName="PeopleService")

public class PeopleImpl implements People {

    /***
     *
     * @param id the id of the person to return
     * @return the person identified by id
     * @throws IOException 
     * @throws JSONException 
     * @throws MalformedURLException 
     */
    @Override
    public Person readPerson(Long id, String token) throws MalformedURLException, JSONException, IOException {
        System.out.println("---> Reading Person by id = "+id);
        
        // FacebookService called
        FacebookService fb = new FacebookService();
        FacebookInfo fi = null;
        Person p = null;
        
        // DEBUG
        id = (long)1;
        
        // Check if the Id of the user which is authenticated by the given token is equal to
        // the Id that the user is looking for..
        // Not Authorized otherwise
        
        try {
        	
        	fi = fb.getInfoByToken(token);
        	if(fi.getId() == id || true /* DEBUG */) {
        		p = Person.getPersonByID(id);
        	} else {
        		System.err.println("User Not Authorized to read these data");
        	}

            if (p!=null) {
                System.out.println("---> Found Person by id = "+id+" => " + p.getFirstname());
            } else {
                System.out.println("---> Didn't find any Person with  id = " + id);
            }
        	
        } catch(FacebookErrorException fb_excep) {
        	System.err.println("Exception raised in FacebookService: " + fb_excep.getCode() + ", " + fb_excep.getMessage());
        	p = null;
        }
        
        return p;
    }
}