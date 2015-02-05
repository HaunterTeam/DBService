package rest.dozer;

import org.dozer.DozerBeanMapper;
import rest.Dao.ModelDao;
import rest.model.Measure;
import rest.model.MeasureType;
import rest.model.Person;
import rest.model.beans.MeasureBean;
import rest.model.beans.PersonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by les on 11/11/14.
 */
public class EntityTransfer {

    public static List<PersonBean> transferToPersonBean(List<Person> people){
        DozerBeanMapper mapper = ModelDao.instance.createDozerMapper();
        List<PersonBean> list = new ArrayList<PersonBean>();

        for(Person p:people)
            list.add(mapper.map(p,PersonBean.class));
        return list;

    }
    public static List<MeasureBean> transferToMeasureBean(List<Measure> historyList){
        DozerBeanMapper mapper = ModelDao.instance.createDozerMapper();
        List<MeasureBean> list = new ArrayList<MeasureBean>();

        for(Measure h:historyList)
            list.add(mapper.map(h,MeasureBean.class));
        return list;

    }

}
