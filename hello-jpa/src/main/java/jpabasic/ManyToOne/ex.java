package jpabasic.ManyToOne;

import javax.persistence.*;

public class ex {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {


            // 1. 객체 지향 모델링 - 연관관계 저장
            //팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);
            //회원 저장
            Member member = new Member();
            member.setName("member1");
            member.setTeam(team); //단방향 연관관계 설정, 참조 저장
            em.persist(member);


            // 2. 객체 지향 모델링 - (참조로 연관관계 조회 - 객체 그래프 탐색
            //조회
            Member findMember = em.find(Member.class, member.getId());
            //참조를 사용해서 연관관계 조회
            Team findTeam = findMember.getTeam();


            // 3. 객체 지향 모델링 - 연관관계 수정
            // 새로운 팀B
            Team teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamB);
            // 회원1에 새로운 팀B 설정
            member.setTeam(teamB);


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
