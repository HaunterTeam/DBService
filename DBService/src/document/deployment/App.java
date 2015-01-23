package document.deployment;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.ws.Endpoint;

import auth.Settings;
import document.ws.PeopleImpl;

public class App {
	
    // public static String SERVER_URL = "http://localhost";
    // public static String SERVER_URL = "http://10.23.10.148";
	
	//    public static String PORT = "8443";
	//    public static String PEOPLE_BASE_URL = "/dbservice/ws/people";

    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException {
    	
        String protocol = Settings.BASE_PROTOCOL;
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = Settings.BASE_PORT;
        }
        String hostname = InetAddress.getLocalHost().getHostAddress();
        String path = Settings.BASE_PATH;
        if (hostname.equals(Settings.BASE_URL))
        {
            hostname = "localhost";
        }

        URI baseUrl = new URI(protocol + hostname + ":" + port + path);    	
    	
        System.out.println("Starting People Service...");
        System.out.println("--> Published at = " + baseUrl);
        Endpoint.publish(baseUrl.toString(), new PeopleImpl());

        System.out.print("done");
    }
}
