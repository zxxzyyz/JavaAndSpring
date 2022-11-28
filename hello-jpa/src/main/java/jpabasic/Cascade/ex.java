package jpabasic.Cascade;

import javax.persistence.*;

public class ex {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {


            Team team = new Team();
            Member m1 = new Member();
            Member m2 = new Member();

            team.addMember(m1);
            team.addMember(m2);

            // No need if cascade : em.persist(m1);
            // No need if cascade : em.persist(m2);
            em.persist(team);



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
