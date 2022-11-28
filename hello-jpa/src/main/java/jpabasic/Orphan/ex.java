package jpabasic.Orphan;

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


            Team team1 = new Team();
            Team team2 = new Team();
            Member m1 = new Member();
            Member m2 = new Member();
            Member m3 = new Member();
            Member m4 = new Member();

            team1.addMember(m1);
            team1.addMember(m2);
            team2.addMember(m3);
            team2.addMember(m4);
            em.persist(team1);
            em.persist(team2);
            em.persist(m1);
            em.persist(m2);
            em.persist(m3);
            em.persist(m4);

            em.flush();
            em.clear();

            // This calls delete query on member table : delete m1
            Team findTeam1 = em.find(Team.class, team1.getId());
            findTeam1.getMembers().remove(0);

            // This calls delete query on member table : delete m3 and m4
            Team findTeam2 = em.find(Team.class, team2.getId());
            em.remove(findTeam2);

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
