package rest.resources;

import auth.FacebookInfo;
import auth.FacebookService;
import document.model.Measure;
import document.model.Person;


import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


@Path("/person")
public class PersonCollectionResource {

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of people to the user in the browser
	@GET
	@Produces(MediaType.APPLICATION_JSON )
	public List<document.model.Person> getPersonsBrowser(@QueryParam("measureType") String measure, @QueryParam("min") int min, @QueryParam("max") int max) {

        if (measure == null) {
            System.out.println("Getting list of people...");
            return document.model.Person.getAll();
        }
        else {
            //return rest.model.Person.getPeopleInRange(measure, min, max);
            return document.model.Person.getPeopleInRange(measure, min, max);
        }
	}


	// retuns the number of people
	// to get the total number of records
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public int getCount() {
		System.out.println("Getting count...");
        return Person.getAll().size();

	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Person newPerson(@QueryParam("token") String token, Person person) throws Exception {
		System.out.println("Creating new person...");

        if(token == null || token.equals(""))
            return Person.savePerson(person);
        
        FacebookService fs = new FacebookService();
        FacebookInfo fi = fs.getInfoByToken(token);

        int person_id = fi.getId().intValue();
        person.setId((long)person_id);
        person.setFirstname(fi.getFirst_name());
        return Person.savePerson(person);

	}
	

	// Defines that the next path parameter after the base url is
	// treated as a parameter and passed to the PersonResources
	// Allows to type http://localhost:599/base_url/1
	// 1 will be treaded as parameter todo and passed to PersonResource
	@Path("{personId}")
	public PersonResource getPerson(@PathParam("personId") int id) {
		return new PersonResource(uriInfo, request, id);
	}

    @GET
    @Path("{personId}/{measure}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public List<Measure> getMeasureByPerson(@PathParam("personId") int id, @PathParam("measure") String measure, @QueryParam("before") String before, @QueryParam("after") String after) {

        if(after != null && before != null)
             return  Measure.getMeasuresByRange((long)id, measure, after, before);

        return Measure.getMeasuresByPersonID((long)id, measure);

	}

    @GET
    @Path("{personId}/{measure}/{mid}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Measure getMeasureByPersonAndID(@PathParam("personId") int id, @PathParam("measure") String measure, @PathParam("mid") int mid) {
		return Measure.getMeasuresByPersonIDAndMID((long)id, measure, (long)mid);
	}

    @POST
    @Path("{personId}/{measure}")
    @Produces({"application/javascript"})
    @Consumes(MediaType.APPLICATION_JSON)
    public String insertMeasureByPerson(@PathParam("personId") long id, @PathParam("measure") String measure,@QueryParam("token") String token,@QueryParam("callback") String callback, Measure newMeasure)
            throws Exception
    {
        Person p = Person.getPersonByID((long)id);

        if(p == null && !token.equals("")) {
            FacebookService fs = new FacebookService();
            FacebookInfo fi = fs.getInfoByToken(token);
            p = new Person();
            p.setFirstname(fi.getFirst_name());
            p.setId((long)fi.getId());
            p = Person.savePerson(p);
        }
        else
            throw new NotFoundException("This person does not exist");

        newMeasure.setPerson(p);
        newMeasure.setMeasureType(measure);
        newMeasure.setTodayDate();

        return callback+"("+Measure.saveMeasure(newMeasure).toString()+")";
    }

    @PUT
    @Path("{personId}/{measure}/{mid}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Measure updateMeasureByID(@PathParam("personId") int id, @PathParam("measure") String measure, @PathParam("mid") int mid, Measure measureHistory){
        Measure tempMeasure = Measure.getMeasuresByPersonIDAndMID((long)id, measure, (long)mid);

        if(tempMeasure == null)
            throw new NotFoundException();

        //tempMeasure.setValue(measureHistory.getValue());
        //tempMeasure.setCreated(measureHistory.getCreated());
        Measure.updateMeasure(tempMeasure);
        return tempMeasure;
    }
    @POST
    @Path("/facebook")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Measure insertMeasureFB(@QueryParam("token") String token, Measure newMeasure)
            throws Exception
    {
        System.out.println("This is the post working");

        FacebookService fs = new FacebookService();
        FacebookInfo fi = fs.getInfoByToken(token);
        Person p = Person.getPersonFromFB(fi.getId());
        if(p != null) {
            p= new Person();
            p.setFirstname(fi.getFirst_name());
            p.setId((long) fi.getId());
            p = Person.savePerson(p);
        }
        else
            throw new NotFoundException("This person does not exist");

        newMeasure.setPerson(p);
        newMeasure.setTodayDate();

        return Measure.saveMeasure(newMeasure);
    }



}
