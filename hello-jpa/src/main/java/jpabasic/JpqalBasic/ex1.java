package jpabasic.JpqalBasic;

import jpabasic.domain.Item;

import javax.persistence.*;
import java.util.List;

public class ex1 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {


            // 반환 타입이 명확
            TypedQuery<Member> typedQuery = em.createQuery("SELECT m FROM Member m", Member.class);

            // 반환 타입 불명확
            Query query = em.createQuery("SELECT m.id, m.name FROM Member m");

            // 결과 반환
            List<Member> typedQueryResult = typedQuery.getResultList();
            List resultList = query.getResultList();

            // 단건 결과 반환
            // 결과가 없을 경우 : NoResultException
            // 결과가 복수일 경우 : NonUniqueResultException
            Member typedQuerySingleResult = typedQuery.getSingleResult();
            Object querySingleResult = query.getSingleResult();

            // 조건
            TypedQuery<Member> typedQueryWhere = em.createQuery("SELECT m FROM Member m WHERE m.name = :username", Member.class);
            typedQueryWhere.setParameter("username", "koo");

            // 조인
            TypedQuery<Team> typedQueryJoin = em.createQuery("SELECT t FROM Member  m JOIN m.team t", Team.class);

            // 중복 제거
            Query queryDistinct = em.createQuery("SELECT DISTINCT p.name, p.price FROM Product p");

            // 결과 사용 1
            List result1 = em.createQuery("SELECT m.id, m.name FROM Member m").getResultList();
            Object resultObject = result1.get(0);
            Object[] getResult1 = (Object[]) resultObject;
            System.out.println("id = " + getResult1[0]);
            System.out.println("name = " + getResult1[1]);

            // 결과 사용 2
            List<Object[]> result2 = em.createQuery("SELECT m.id, m.name FROM Member m").getResultList();
            Object[] getResult2 = result2.get(0);
            System.out.println("id = " + getResult2[0]);
            System.out.println("name = " + getResult2[1]);

            // 결과 사용 3
            List<MemberDto> result3 = em.createQuery("SELECT new jpabasic.JpqalBasic.MemberDto(m.id, m.name) FROM Member m", MemberDto.class).getResultList();
            MemberDto memberDto = result3.get(0);
            System.out.println("id = " + memberDto.getId());
            System.out.println("name = " + memberDto.getName());

            // 페이징
            List<Member> pagingQuery = em.createQuery("SELECT m FROM Member m ORDER BY m.name desc", Member.class)
                    .setFirstResult(5) // 몇 번째 부터
                    .setMaxResults(10) // 몇 개
                    .getResultList();

            // 내부 조인
            List<Member> innerJoin = em.createQuery("SELECT m FROM Member m INNER JOIN m.team t", Member.class).getResultList();

            // 외부 조인
            List<Member> leftJoin = em.createQuery("SELECT m FROM Member m LEFT JOIN m.team t", Member.class).getResultList();

            // 세타 조인
            List<Member> crossJoin = em.createQuery("SELECT m FROM Member m, Team t", Member.class).getResultList();

            // 조인 대상 필터링
            List<Member> filterJoin1 = em.createQuery("SELECT m FROM Member m LEFT JOIN m.team t on t.name = 'TeamABC'", Member.class).getResultList();
            List<Member> filterJoin2 = em.createQuery("SELECT m FROM Member m LEFT JOIN Team t on m.name = t.name", Member.class).getResultList();

            // 서브 쿼리
            // id가 평균값 보다 높은 멤버
            List<Member> sub1 = em.createQuery("SELECT m FROM Member m WHERE m.id > (SELECT avg(m.id) from Member m2)", Member.class).getResultList();
            // 한 건이라도 주문이 있는 멤버
            List<Member> sub2 = em.createQuery("SELECT m FROM Member m WHERE (SELECT count(o) FROM Order o WHERE m = o.member) > 0", Member.class).getResultList();
            // TeamABC 소속 멤버
            List<Member> sub3 = em.createQuery("SELECT m FROM Member m WHERE EXISTS (SELECT t FROM m.team t WHERE t.name = 'TeamABC')", Member.class).getResultList();
            // 재고보다 주문량이 많은 주문
            List<Member> sub4 = em.createQuery("SELECT o FROM Order o WHERE o.orderAmount > ALL (SELECT p.stockAmount FROM Product p)", Member.class).getResultList();
            // 어떤 팀이든 팀에 소속된 멤버
            List<Member> sub5 = em.createQuery("SELECT m FROM Member m WHERE m.team = ANY (SELECT t FROM Team t)", Member.class).getResultList();

            // 상속 관계가 있을 때 DTYPE 조건
            List<Item> dtypeCondition1 = em.createQuery("SELECT i FROM Item i WHERE TYPE(i) = Book", Item.class).getResultList();
            List<Item> dtypeCondition2 = em.createQuery("SELECT i FROM Item i WHERE TYPE(i) IN (Book, Album)", Item.class).getResultList();
            List<Item> dtypeCondition3 = em.createQuery("SELECT i FROM Item i WHERE TYPE(i) IN ('B', 'A')", Item.class).getResultList();
            List<Item> inheritance = em.createQuery("SELECT i FROM Item i WHERE TREAT(i as Book).author = 'koo'", Item.class).getResultList();

            // 케이스 쿼리
            List<Member> caseQuery = em.createQuery(
                    "SELECT " +
                            "case when m.id <= 10 then 'blah-blah' " +
                            "     when m.id >= 20 then 'blah-blah-blah' " +
                            "     else 'blah' end " +
                    "FROM Member m", Member.class).getResultList();

            // COALESCE 순차적 조회 null 아닌 값
            List<Member> coalesceQuery = em.createQuery("SELECT COALESCE(m.id, 'value') FROM Member m", Member.class).getResultList();

            // NULLIF 두 값이 같으면 NULL 아니면 첫 번째 값
            List<Member> nullIfQuery = em.createQuery("SELECT NULLIF(m.id, 'value') FROM Member m", Member.class).getResultList();

            // 함수 정의
            /*
            public MyFunctionClass extends H2Dialect(DB dialect class for the project) {
                public MyFunctionClass() {
                    registerfunction("function_name", new StandardSQLFunction("function_name", StandardBasicTypes.STRING));
                }
            }
            */
            // 함수 사용
            List<Member> functionQuery1 = em.createQuery("SELECT FUNCTION('function_name', m.name) Member m", Member.class).getResultList();
            List<Member> functionQuery2 = em.createQuery("SELECT function_name(m.name) Member m", Member.class).getResultList();

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
