package document.model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlTransient;

import document.dao.ModelDao;

@Entity
@Table(name="Person")
@NamedQueries({@NamedQuery(name="Person.findAll", query="SELECT p from Person p"),
            @NamedQuery(name = "Person.findAllNames", query="select p.firstname from Person p"),
            @NamedQuery(name = "Person.findPeopleByMeasureRange", query="select p from Person p join Measure m where p.id = m.mid and m.measureType = :typeVal and m.measureValue > :minVal and m.measureValue < :maxVal"),
            @NamedQuery(name = "Person.getCurrentHealth", query="select distinct m from Person p join Measure m where :id = m.person group by m.measureType order by m.dateRegistered desc"),
            @NamedQuery(name="Person.getOldHealthForBMI", query = "select distinct m from Person p join Measure m where :id = m.person and m.measureType = :measure order by m.dateRegistered desc")
})
//@XmlRootElement(name = "person")
public class Person implements Serializable{
    private static final long serialVersionUID = 1L;



    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
            pkColumnName="name", valueColumnName="seq",
            pkColumnValue="Person")
    @Id
    @GeneratedValue(generator="sqlite_person")
    @Column(name="idPerson")
    private int id;

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    //@Temporal(TemporalType.DATE)
    @Column(name="birthdate")
    private String birthdate;

    @XmlTransient
    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Measure> measureHistory;


    public Person(){ }

    //@XmlElementWrapper(name="currentHealth")
    public List<Measure> getCurrentHealth(){
        return Person.getCurrentHealth(this.getId());
    }
    public void setCurrentHealth(List<Measure> measures){
        //only for traslation purposes
    }

    public Long getId() {
        return ((long)id);
    }

    public void setId(Long idPerson) {

        this.id = idPerson.intValue();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

   // @XmlElement(name="birthdate")
    public Date getBirthdate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date toReturn = null;

        try {
            toReturn = df.parse(this.birthdate);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return toReturn;
    }

    public void setBirthdate(Date birthdate) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            this.birthdate = df.format(birthdate);

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public List<Measure> getMeasureHistory() {
        return measureHistory;
    }

    public void setMeasureHistory(List<Measure> historyList) {
        this.measureHistory = historyList;
    }

    public static Person getPersonByID(Long idPerson) {
        EntityManager em = ModelDao.instance.createEntityManager();
        Person p = em.find(Person.class,idPerson.intValue());
        ModelDao.instance.closeConnections(em);
        return p;
    }
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
        this.birthdate = (""+year+"-"+month+"-"+day);
    }

    /***
     * This function gets all the person objects in the database
     * @return
     */
    public static List<Person> getAll() {
        EntityManager em = ModelDao.instance.createEntityManager();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
        ModelDao.instance.closeConnections(em);
        return list;
    }

    /***
     * This function gets a list of person whose measures are in the range of min & max
     * @param measureType the measure to filter by
     * @param min the min value
     * @param max the max value
     * @return
     */
    public static List<Person> getPeopleInRange(String measureType, int min, int max) {
        EntityManager em = ModelDao.instance.createEntityManager();
        MeasureType type = new MeasureType();
        type.setMeasureType(measureType);

        List<Person> list = em.createNativeQuery("select distinct * from Measure m inner join person on idPerson = person where measureType = ? and m.measureValue > ? and m.measureValue < ? group by person", Person.class)
                .setParameter(1, measureType)
                .setParameter(2,min)
                .setParameter(3,max)
                .getResultList();
        ModelDao.instance.closeConnections(em);
        return list;
    }
    public static List<Measure> getCurrentHealth(Long id){

        EntityManager em = ModelDao.instance.createEntityManager();

        List<Measure> list = em.createNamedQuery("Person.getCurrentHealth",Measure.class)
                .setParameter("id",Person.getPersonByID(id))
                .getResultList();
        ModelDao.instance.closeConnections(em);

        return list;

    }
    public void setLastBMI(double bmi){}
    public double getLastBMI(){

        List<Measure> current = getCurrentHealth(getId());

        double height = -1, weight = -1;

        for(Measure m:current){
            if(m.getMeasureType().equals("weight"))
                weight = Double.parseDouble(m.getMeasureValue());
            else if(m.getMeasureType().equals("height"))
                height = Double.parseDouble(m.getMeasureValue());
        }

        if(height < 0 || weight < 0)
            return -1;

        return weight / ((height/100)*(height/100));

    }
    public void setOldBMI(double bmi){}
    public  double getOldBMI(){

        EntityManager em = ModelDao.instance.createEntityManager();

        double height = -1, weight = -1;

        List<Measure> current = em.createNamedQuery("Person.getOldHealthForBMI",Measure.class)
                .setParameter("id", Person.getPersonByID(getId()))
                .setParameter("measure",MeasureType.getMeasureFromString("height"))
                .getResultList();

        //one usually do not change height
        height = Double.parseDouble(current.get(0).getMeasureValue());

        current = em.createNamedQuery("Person.getOldHealthForBMI", Measure.class)
                .setParameter("id", Person.getPersonByID(getId()))
                .setParameter("measure", MeasureType.getMeasureFromString("weight"))
                .getResultList();
        ModelDao.instance.closeConnections(em);

        //there are no older records of height
        if(current.size() <= 1)
            return -1;
        weight = Double.parseDouble(current.get(1).getMeasureValue());

        return weight / ((height/100)*(height/100));
    }


    public static Person savePerson(Person p) {
        EntityManager em = ModelDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        ModelDao.instance.closeConnections(em);
        return p;
    }
    public static Person updatePerson(Person p) {
        EntityManager em = ModelDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        ModelDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = ModelDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        ModelDao.instance.closeConnections(em);
    }

    // mappedBy must be equal to the name of the attribute in LifeStatus that maps this relation



}
