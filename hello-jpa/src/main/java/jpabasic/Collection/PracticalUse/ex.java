package jpabasic.Collection.PracticalUse;

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



            Member m1 = new Member();
            m1.setHomeAddress(new Address("homeCity", "homeStreet"));
            m1.getShippingAddress().add(new AddressEntity("city1", "street1"));
            m1.getShippingAddress().add(new AddressEntity("city2", "street2"));
            em.persist(m1);
            em.flush();
            em.clear();

            // By changing value-type collection into ref-type collection, many disadvantages are gone
            Member findMember = em.find(Member.class, m1.getId());
            findMember.getShippingAddress().remove(new AddressEntity("city1", "street1"));
            findMember.getShippingAddress().add(new AddressEntity("city3", "street3"));


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

