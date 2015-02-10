package document.model;

/**
 * Created by les on 01/11/14.
 */

import document.dao.ModelDao;
import org.hibernate.annotations.Type;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name= "Measure")

@NamedQueries({@NamedQuery(name="HealthMeasureHistory.findAll", query="SELECT h FROM Measure h"),
        @NamedQuery(name="HealthMeasureHistory.findMeasuresByPersonAndType", query="select  h from Measure h where h.person = :person and h.measureType = :mtype"),
        @NamedQuery(name="HealthMeasureHistory.findMeasuresByPersonAndMID", query="select  h from Measure h where h.person = :person and h.measureType = :mtype and h.mid = :mid"),
        @NamedQuery(name="HealthMeasureHistory.findMeasuresByRange", query="select  h from Measure h where h.person = :person and h.measureType = :mtype and h.dateRegistered between :before and :after"),
        @NamedQuery(name="Measure.find", query="SELECT m FROM Measure m WHERE m.person = :idface")
})

public class Measure implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="sqlite_mhistory")
    @TableGenerator(name="sqlite_mhistory", table="sqlite_sequence",
            pkColumnName="name", valueColumnName="seq",
            pkColumnValue="Measure")
    @Column(name="idMeasure")
    private Long mid;

    @Column(name="measureValue")
    private String measureValue;

    //@Temporal(TemporalType.DATE)

    @Column(name="dateRegistered") //There is no point in using date formats in sqlite from the moment that sqlite itself doesn't use dates but strings
    private String dateRegistered;


    @ManyToOne
    @JoinColumn(name="person",referencedColumnName="idPerson")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "measureType", referencedColumnName = "measure")
    private MeasureType measureType;

    public Measure(){

    }

    @XmlElement(name = "measureType")
    public String getMeasureType(){
        if(this.measureType != null)
            return this.measureType.getMeasureType();
        else
            return "";
    }
    public void setMeasureType(String measureType){
        this.measureType = MeasureType.getMeasureFromString(measureType);
    }
    public Long getMid() {
        return mid;
    }

    public void setMid(Long idMeasure) {
        this.mid = idMeasure;
    }

    public String getMeasureValue() {
        return measureValue;
    }

    public void setMeasureValue(String value) {
        this.measureValue = value;
    }

    public Date getDateRegistered() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date toReturn = null;

        try {
            toReturn = df.parse(this.dateRegistered);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return toReturn;
    }

    public void setDateRegistered(Date created) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.dateRegistered = df.format(created);

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @XmlTransient
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @XmlTransient
    public MeasureType getMeasureTypeModelObject() {
        return measureType;
    }

    /*public void setMeasureType(MeasureType measureType) {
        this.measureType = measureType;
    }*/

    public void setRandomDate() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR); 		// 1. get the current year
        int year = (int) Math.round(Math.random()*(currentYear-1950)+1950); // 2. generate a random year
        //    between 1950 and currentYear
        int month = (int) Math.round(Math.floor(Math.random()*11)+1);		// 3. select a random month of the year
        // 4. select a random day in the selected month
        // 4.1 prepare a months array to store how many days in each month
        int[] months = new int[]{31,28,30,30,31,30,31,31,30,31,30,31};
        // 4.2 if it is a leap year, feb (months[1]) should be 29
        if ((currentYear % 4 == 0) && ((currentYear % 100 != 0) || (currentYear % 400 == 0))) {
            months[1] = 29;
        }
        long day = Math.round(Math.floor(Math.random()*(months[month-1]-1)+1));
        this.dateRegistered = "" + year + "-" + month + "-" + day;
    }
    public void setTodayDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        this.dateRegistered = df.format(now);
    }

    /***
     *
     * @param personID the identifier of the person
     * @param measure the measure type
     * @return a list of measures for the given person & measureType
     */
    public static List<Measure> getMeasuresByPersonID(Long personID,String measure){
        EntityManager em = ModelDao.instance.createEntityManager();
        Person p = Person.getPersonByID(personID);
        //p.setId((long)personID);
        MeasureType type = MeasureType.getMeasureFromString(measure);
        List<Measure> list = em.createNamedQuery("HealthMeasureHistory.findMeasuresByPersonAndType", Measure.class)
                .setParameter("person",p)
                .setParameter("mtype",type)
                .getResultList();
        ModelDao.instance.closeConnections(em);
        return list;
    }
    /***
     *
     * @param personID the identifier of the person
     * @param measure the measure type
     * @param measureID the measure identifier
     * @return a list of measures for the given person & measureType & measure id
     */
    public static Measure getMeasuresByPersonIDAndMID(Long personID,String measure, Long measureID){
        EntityManager em = ModelDao.instance.createEntityManager();
        Person p = new Person();
        p.setId((long)personID);
        MeasureType type = new MeasureType();
        type.setMeasureType(measure);
        List<Measure> list = em.createNamedQuery("HealthMeasureHistory.findMeasuresByPersonAndMID", Measure.class)
                .setParameter("person",p)
                .setParameter("mtype",type)
                .setParameter("mid",measureID )
                .getResultList();
        ModelDao.instance.closeConnections(em);

        if(list.size() == 0)
            return null;
        else
            return list.get(0);
    }

    /***
     * Retrieves a list of measures in a temporal range
     * @param personID the identifier of the person
     * @param measure the measure type
     * @param date1 the date after
     * @param date2 the date before
     * @return list of measures
     */
    public static List<Measure> getMeasuresByRange(Long personID,String measure,String date1, String date2){
        EntityManager em = ModelDao.instance.createEntityManager();

        List<Measure> list = em.createNamedQuery("HealthMeasureHistory.findMeasuresByRange", Measure.class)
                .setParameter("person",Person.getPersonByID(personID))
                .setParameter("mtype",MeasureType.getMeasureFromString(measure))
                .setParameter("after", date1)
                .setParameter("before", date2)
                .getResultList();

        ModelDao.instance.closeConnections(em);
        return list;
    }
    public static Measure updateMeasure(Measure hm) {
        EntityManager em = ModelDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        hm=em.merge(hm);
        tx.commit();
        ModelDao.instance.closeConnections(em);
        return hm;
    }
    public static Measure saveMeasure(Measure m) {
        EntityManager em = ModelDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(m);
        tx.commit();
        ModelDao.instance.closeConnections(em);
        return m;
    }

      public static Measure saveMeasureFromPersonAndType(Person p, String measureType){
        Measure measureHistory = new Measure();
        measureHistory.setTodayDate();
        measureHistory.setPerson(p);

        measureHistory.setMeasureType(measureType);
        return Measure.saveMeasure(measureHistory);

    }



}
