package document.deployment;

import document.model.Person;
import document.ws.PeopleImpl;

import javax.xml.ws.Endpoint;

public class App {
    public static String SERVER_URL = "http://localhost";
    //public static String SERVER_URL = "http://10.23.10.148";

    public static String PORT = "8443";
    public static String PEOPLE_BASE_URL = "/dbservice/ws/people";

    public static String getPeopleEndpointURL() {
        return SERVER_URL+":"+PORT+PEOPLE_BASE_URL;
    }

    public static void main(String[] args) {
        System.out.println("Starting People Service...");
        System.out.println("--> Published at = " + getPeopleEndpointURL());
        Endpoint.publish(getPeopleEndpointURL(), new PeopleImpl());


        System.out.print("done");
    }
}
