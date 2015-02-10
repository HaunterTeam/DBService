package document.model;

/**
 * Created by les on 03/11/14.
 */
import document.dao.ModelDao;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;



@Entity
@Table(name="MeasureTypes")
@NamedQueries({@NamedQuery(name="MeasureTypes.findtypes", query="select m from MeasureType m"),
        @NamedQuery(name="MeasureTypes.findTypeByName", query="select m from MeasureType m where m.measureType = :measure")
})
public class MeasureType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id


    @Column(name="measure")
    private String measureType;

    public MeasureType(){

    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measure) {
        this.measureType = measure;
    }

    public static List<MeasureType> getMeasures(){
        EntityManager em = ModelDao.instance.createEntityManager();
        List<MeasureType> list = em.createNamedQuery("MeasureTypes.findtypes", MeasureType.class).getResultList();
        ModelDao.instance.closeConnections(em);
        return list;

    }
    public static MeasureType getMeasureFromString(String measureType){
        EntityManager em = ModelDao.instance.createEntityManager();
        List<MeasureType> list = em.createNamedQuery("MeasureTypes.findTypeByName", MeasureType.class)
                .setParameter("measure",measureType)
                .getResultList();
        ModelDao.instance.closeConnections(em);
        if(list.size() == 0)
            return null;
        else
            return list.get(0);
    }

    @Override
    public String toString()
    {
        return this.getMeasureType();
    }
}
