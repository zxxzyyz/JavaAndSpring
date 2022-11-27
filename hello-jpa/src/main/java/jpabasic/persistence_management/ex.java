package jpabasic.persistence_management;

import javax.persistence.*;

public class ex {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {



            // Non persistent:Transient
            Member m1 = new Member();
            m1.setName("m1");

            // Persistent
            Member m2 = new Member();
            m2.setName("m2");

            // Detached
            em.detach(m2);

            // Remove
            em.remove(m2);

            // FlushMode
            em.flush(); // Manual flush
            em.setFlushMode(FlushModeType.AUTO); // default : On commit or on JPQL
            em.setFlushMode(FlushModeType.COMMIT); // Only on commit


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
