package jpabasic.BasicMapping;

import jpabasic.persistence_management.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ex {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {



            /* persistence.xml
            <property name="hibernate.hbm2ddl.auto" value="create"/> : Drop + Create
            <property name="hibernate.hbm2ddl.auto" value="create-drop"/> : Drop + Create + Drop
            <property name="hibernate.hbm2ddl.auto" value="update"/> : Update differences
            <property name="hibernate.hbm2ddl.auto" value="validate"/> : Validate entity is mapped by table
            <property name="hibernate.hbm2ddl.auto" value="none"/> : Do nothing

            development environment -> create or update
            test environment -> update or validate
            stage environment -> validate or none
            production environment -> none
            */



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
          em.close();
        }
        emf.close();

    }

}
