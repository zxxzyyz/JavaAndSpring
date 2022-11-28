package jpabasic.Collection;

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



            // Insertion
            Member m1 = new Member();
            m1.setHomeAddress(new Address("homeCity", "homeStreet"));
            m1.getFavoriteFoods().add("pizza");
            m1.getFavoriteFoods().add("burger");
            m1.getShippingAddress().add(new Address("city1", "street1"));
            m1.getShippingAddress().add(new Address("city2", "street2"));

            em.persist(m1); // Insert into Member, food, shipping address tables

            em.flush();
            em.clear();



            // Select
            Member findMember = em.find(Member.class, m1.getId()); // Collections' fetch type is LAZY



            // Change food
            findMember.getFavoriteFoods().remove("pizza"); // delete query, works like cascade.ALL
            findMember.getFavoriteFoods().add("chicken"); // insert query, works like cascade.ALL



            // Change shipping address
            // Delete all address table. And insert all the things in the collection into table.
            // To prevent, can try @OrderColumn(name = "address_history_order") : This adds a column for history order.
            findMember.getShippingAddress().remove(new Address("city1", "street1"));
            findMember.getShippingAddress().add(new Address("city3", "street3"));


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

