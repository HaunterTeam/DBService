package rest.resources;

import document.model.Measure;
import document.model.Person;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;



public class PersonResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	EntityManager entityManager;
	
	int id;

	public PersonResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		this.entityManager = em;
	}
	
	public PersonResource(UriInfo uriInfo, Request request,int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	
	// Application integration
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Person getPerson() {
		Person person = Person.getPersonByID((long)id);
		if (person == null)
			throw new NotFoundException();
		return person;
	}

	// for the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public Person getPersonHTML() {
		Person person = Person.getPersonByID((long)this.id);
		if (person == null)
			throw new NotFoundException("Get: Person with " + id + " not found");
		System.out.println("Returning person... " + person.getId());
		return person;
	}

	@PUT
    //@Path("{personID}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response putPerson(Person person) {
		System.out.println("--> Updating Person... " +this.id);
		System.out.println("--> " + person.toString());
		//Person.updatePerson(person);
		
		Response res;
		
		Person existing = Person.getPersonByID((long)this.id);
		
		if (existing == null) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
			person.setId((long)this.id);

			Person.updatePerson(person);
		}

		return res;

		
	}

	@DELETE
	public void deletePerson() {
		Person c = Person.getPersonByID((long)id);
		if (c == null)
			throw new NotFoundException("Delete: Person with " + id
					+ " not found");
		Person.removePerson(c);
	}


}
