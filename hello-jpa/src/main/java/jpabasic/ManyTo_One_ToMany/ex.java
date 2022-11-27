package jpabasic.ManyTo_One_ToMany;

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


            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);
            Member member = new Member();
            member.setName("member1");
            member.setTeam(team);
            em.persist(member);

            // 1. 양방향 매핑 - 반대 방향으로 객체 그래프 탐색
            //조회
            Team findTeam = em.find(Team.class, team.getId());
            int memberSize = findTeam.getMembers().size(); //역방향 조회



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
          em.close();
        }
        emf.close();

    }

    public static void mostKnownMistake(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {


            // 양방향 매핑시 가장 많이 하는 실수 - 연관관계의 주인에 값을 입력하지 않음
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);
            Member member = new Member();
            member.setName("member1");
            //역방향(주인이 아닌 방향)만 연관관계 설정
            team.getMembers().add(member);
            em.persist(member);
            // result : member_id=1 name="member1" team_id=null



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    public static void fixedMostKnownMistake(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {


            // 양방향 매핑시 연관관계의 주인에 값을 입력해야 한다 - 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);
            Member member = new Member();
            member.setName("member1");
            team.getMembers().add(member);
            //연관관계의 주인에 값 설정
            member.setTeam(team);
            em.persist(member);



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
