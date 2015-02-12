package rest.deployment;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class RESTApp extends Thread
{
    //private static final URI BASE_URI = URI.create("http://localhost:9091/dbservice/");

    //deployment url
    private static final URI BASE_URI = URI.create("http://95.85.59.245:8086/dbservice/");
    public  void run ()
    {
    	System.out.println("Starting RESTFul DB Service...");
        JdkHttpServerFactory.createHttpServer(BASE_URI, createApp());
        System.out.println("Server started on " + BASE_URI + "\n[kill the process to exit]");

        System.out.println("Done");

    }
    public static ResourceConfig createApp() {
        return new MyApplicationConfig();
    }
}
