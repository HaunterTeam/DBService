package document.deployment;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.ws.Endpoint;

import auth.Settings;
import document.model.Person;
import document.ws.PeopleImpl;
import rest.deployment.RESTApp;

public class App {
	
    // public static String SERVER_URL = "http://localhost";
    // public static String SERVER_URL = "http://10.23.10.148";
	
	//    public static String PORT = "8443";
	//    public static String PEOPLE_BASE_URL = "/dbservice/ws/people";

    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException {
    	
        String protocol = Settings.BASE_PROTOCOL;
        String port = Settings.BASE_PORT;
        //String hostname = InetAddress.getLocalHost().getHostAddress();
        //String hostname = Settings.BASE_DEPLOY_URL;
        String hostname = "localhost";
        String path = Settings.BASE_PATH;
        if (hostname.equals(Settings.BASE_URL))
        {
            hostname = "localhost";
        }

        URI baseUrl = new URI(protocol + hostname + ":" + port + path);    	
    	
        System.out.println("Starting People Service...");
        System.out.println("--> Published at = " + baseUrl);
        Endpoint.publish(baseUrl.toString(), new PeopleImpl());

        (new RESTApp()).start();

        System.out.print("done");
    }
}
