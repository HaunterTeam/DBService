package rest.deployment;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.net.InetAddress;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class RESTApp extends Thread
{
    //private static final URI BASE_URI = URI.create("http://localhost:9091/dbservice/");

    //deployment url
    // private static final URI BASE_URI = URI.create("http://95.85.59.254:8086/dbservice/");
    public  void run ()
    {
        try {

            String protocol = Settings.BASE_PROTOCOL;
            String port = System.getenv("PORT");
            if (port == null || port.isEmpty()) {
                port = Settings.BASE_PORT;
            }
            String hostname = InetAddress.getLocalHost().getHostAddress();
            String path = Settings.SERVICE_PATH;
            if (hostname.equals(Settings.BASE_URL))
            {
                hostname = "localhost";
            }

            URI baseUrl = new URI(protocol + hostname + ":" + port + path);
            
        	System.out.println("Starting RESTFul DB Service...");
            JdkHttpServerFactory.createHttpServer(baseUrl, createApp());
            System.out.println("Server started on " + baseUrl + "\n[kill the process to exit]");
        } catch( Exception exception) {
            exception.printStackTrace();
        }

        System.out.println("Done");

    }
    public static ResourceConfig createApp() {
        return new MyApplicationConfig();
    }
}
