package document.ws;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jws.WebService;

import org.json.JSONException;

import auth.FacebookInfo;
import auth.FacebookService;
import document.model.Person;

//Service Implementation

@WebService(endpointInterface = "document.ws.People",
        serviceName="PeopleService")

public class PeopleImpl implements People {

    /***
     *
     * @param id the id of the person to return
     * @return the person identified by id
     * @throws IOException 
     * @throws JSONException 
     * @throws MalformedURLException 
     */
    @Override
    public Person readPerson(Long id, String token) throws MalformedURLException, JSONException, IOException {
        System.out.println("---> Reading Person by id = "+id);
        
        FacebookService fb = new FacebookService();
        FacebookInfo fi = fb.getInfoByToken(token);
        
        Person p = Person.getPersonByID(id);

        if (p!=null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getFirstname());
        } else {
            System.out.println("---> Didn't find any Person with  id = "+id);
        }
        return p;
    }

//    /***
//     *
//     * @return The list of all the people in the db
//     */
//    @Override
//    public List<Person> getPeople() {
//        return Person.getAll();
//    }
//
//    /***
//     *
//     * @param person the new person to add to the database
//     * @return the previosly added person
//     */
//    @Override
//    public Person addPerson(Person person) {
//
//        if(person.getMeasureHistory() == null)
//            person.setMeasureHistory(new ArrayList<Measure>());
//        return Person.savePerson(person);
//    }
//
//    /***
//     *
//     * @param person the person to update
//     * @return the update version of the object previously sent
//     */
//    @Override
//    public Person updatePerson(Person person) {
//
//        /**
//         * This fixes the bug due to the @XmlTransient in Measure. This creates null values for the attribute person in measure
//         * and creates null values in the database
//         */
//        if(person.getMeasureHistory() != null){
//            List<Measure> measures = Measure.getMeasuresByPersonID(person.getId(),"weight");
//            for (Measure m:person.getMeasureHistory())
//                m.setPerson(person);
//        }
//        return Person.updatePerson(person);
//    }
//
//    /***
//     *
//     * @param id the identifier of the person who needs to be removed
//     */
//    @Override
//    public void deletePerson(Long id) {
//        Person p = Person.getPersonByID(id);
//        if (p!=null)
//            Person.removePerson(p);
//    }
//
//    /***
//     *
//     * @param id the identifier of the person
//     * @param measure the measureType to filter on
//     * @return a list of measures which belongs the given filters
//     */
//    @Override
//    public List<Measure> getMeasuresFromIDandMeasure(Long id, String measure) {
//        return Measure.getMeasuresByPersonID(id, measure);
//
//    }
//
//    /***
//     *
//     * @param id the identifier of the person
//     * @param measure the measureType to filter on
//     * @param mid the measure id
//     * @return the measure which is uniquely defined by the three parameters
//     */
//    @Override
//    public Measure getPersonMeasurement(Long id, String measure, Long mid) {
//        return Measure.getMeasuresByPersonIDAndMID(id, measure, mid);
//    }
//
//    /**
//     *
//     * @param id the identifier of the person
//     * @param measure the measureType of the new measure
//     * @param newMeasure the new measure object
//     * @return the new measure saved in the database
//     */
//    @Override
//    public Long savePersonMeasure(Long id, String measure, Measure newMeasure) {
//        newMeasure.setPerson(Person.getPersonByID(id));
//        newMeasure.setMeasureType(MeasureType.getMeasureFromString(measure));
//
//        return Measure.saveMeasure(newMeasure).getMid();
//    }
//
//    /***
//     *
//     * @return all the measureTypes in the database
//     */
//    @Override
//    public List<MeasureType> getMeasureTypes()
//    {
//        List<MeasureType> m = null;
//        System.out.println("Retrieving measures");
//        return MeasureType.getMeasures();
//
//    }
//
//    /***
//     *
//     * @param id the identifier of the person
//     * @param measure the measure to be updated
//     * @return the updated measure
//     * @throws NotFoundException if no person with that id exists
//     */
//    @Override
//    public Measure updatePersonMeasure(Long id, Measure measure)  throws NotFoundException{
//        Person p = Person.getPersonByID(id);
//
//        if(p==null)
//            throw new NotFoundException("Person not existing");
//        measure.setPerson(p);
//        return Measure.updateMeasure(measure);
//    }
//
//    /***
//     *
//     * @param id the identifier of the person
//     * @param measureType the measureType to filter on
//     * @param before  end date
//     * @param after beginning date
//     * @return a list of measure defined by person and measuretype in the range of the two dates
//     */
//    @Override
//    public List<Measure> readPersonMeasureByDates(Long id, String measureType, Date before, Date after){
//
//        String dateAfter = "", dateBefore = "";
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//        try{
//            dateAfter = df.format(after);
//            dateBefore = df.format(before);
//        }
//        catch(Exception ex){
//            System.out.println(ex.getMessage());
//        }
//
//        return Measure.getMeasuresByRange(id, measureType, dateAfter, dateBefore);
//
//
//    }
//
//    /***
//     *
//     * @param measureType the measureType to filter on
//     * @param maxValue the max value allowed
//     * @param minValue the min value
//     * @return a list of person which measures are in the range given by min and max
//     */
//    @Override
//    public List<Person> readPersonListByMeasurement(String measureType, String maxValue, String minValue){
//        return Person.getPeopleInRange(measureType, Integer.parseInt(minValue), Integer.parseInt(maxValue));
//    }
}