package rest.Dao;

/**
 * Created by les on 01/11/14.
 */


import org.dozer.DozerBeanMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public enum ModelDao {
    instance;
    private EntityManagerFactory emf;

    private ModelDao() {
        if (emf!=null) {
            emf.close();
        }
        emf = Persistence.createEntityManagerFactory("introsde-jpa");
    }

    public EntityManager createEntityManager() {
        try {
            return emf.createEntityManager();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
        return null;
    }
    public DozerBeanMapper createDozerMapper()
    {
        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add("dozermappings.xml");

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(myMappingFiles);

        return mapper;

    }

    public void closeConnections(EntityManager em) {
        em.close();
    }

    public EntityTransaction getTransaction(EntityManager em) {
        return em.getTransaction();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    // Person related operations could also directly go into the "Person" Model
    // Check Methods in LifeStaus as example

    // add other database global access operations

}

