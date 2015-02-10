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
}