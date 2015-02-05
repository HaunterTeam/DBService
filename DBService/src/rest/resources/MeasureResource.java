package rest.resources;

import document.model.*;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

/*
 * TODO
 * - There is a problem with the EntityManager injection through @PersistenceUnit or @PersistenceContext
 * - will look into it later
 */
@Path("/")


public class MeasureResource {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @GET
    @Path("/measures")
    @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<MeasureType> getMeasures(){
        System.out.println("Retrieving measures");
        return MeasureType.getMeasures();

    }
}
