package document.ws;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.json.JSONException;

import document.model.Person;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
    @WebMethod(operationName="readPerson")
    @WebResult(name="singlePerson")
    public Person readPerson(@WebParam(name="personId") Long id, @WebParam(name="token") String token) throws MalformedURLException, JSONException, IOException;
    
    // Others not allowed
    
//    @WebMethod(operationName="readPersonList")
//    @WebResult(name="people")
//    public List<Person> getPeople();
//
//    @WebMethod(operationName="createPerson")
//    @WebResult(name="singlePerson")
//    public Person addPerson(@WebParam(name="person")Person person);
//
//    @WebMethod(operationName="updatePerson")
//    @WebResult(name="singlePerson")
//    public Person updatePerson(@WebParam(name="person") Person person);
//
//    @WebMethod(operationName="deletePerson")
//    @WebResult(name="people")
//    public void deletePerson(@WebParam(name="personId") Long id);
//
//    @WebMethod(operationName="readPersonListByMeasurement")
//    @WebResult(name="people")
//    public List<Person> readPersonListByMeasurement(@WebParam(name="measureType") String measureType, @WebParam(name="maxValue") String maxValue, @WebParam(name="minValue") String minValue);
//
//
//    @WebMethod(operationName="readPersonHistory")
//    @WebResult(name="measurehistory")
//    public List<Measure> getMeasuresFromIDandMeasure(@WebParam(name="id") Long id, @WebParam(name="measureType") String measure);
//
//    @WebMethod(operationName = "readPersonMeasurement")
//    @WebResult(name="singleMeasure")
//    public Measure getPersonMeasurement(@WebParam(name="id") Long id, @WebParam(name="measureType") String measure, @WebParam(name="mid") Long mid);
//
//    @WebMethod(operationName="savePersonMeasurement")
//    @WebResult(name="measurehistory")
//    public Long savePersonMeasure(@WebParam(name="id") Long id, @WebParam(name="measureType") String measure, @WebParam(name="measure") Measure newMeasure);
//
//    @WebMethod(operationName = "readMeasureTypes")
//    @WebResult(name = "measureTypes")
//    public List<MeasureType> getMeasureTypes();
//
//    @WebMethod(operationName = "updatePersonMeasure")
//    @WebResult(name = "measurehistory")
//    public Measure updatePersonMeasure(@WebParam(name="id") Long id,@WebParam(name="measure") Measure measure) throws NotFoundException;
//
//    @WebMethod(operationName="readPersonMeasureByDates")
//    @WebResult(name="measurehistory")
//    public List<Measure> readPersonMeasureByDates(@WebParam(name="id")Long id, @WebParam(name="measureType") String measureType,@WebParam(name="before") Date before, @WebParam(name="after") Date after);



    /** @WebMethod(operationName="updatePersonHealthProfile")
     @WebResult(name="hpId")
     public int updatePersonHP(@WebParam(name="personId") int id, @WebParam(name="healthProfile") HealthProfile hp);**/
}