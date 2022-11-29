package jpabasic.JpqalBasic;

import jpabasic.domain.Item;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ex2 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {



            // 상태 필드 : 탐색X
            List<Member> fieldExploration = em.createQuery("SELECT m.name FROM Member m", Member.class).getResultList();
            // 단일 값 연관 필드 : 묵시적 내부 조인, 탐색O
            List<Member> one2ManyExploration = em.createQuery("SELECT m.team FROM Member m", Member.class).getResultList();
            // 복수 값 연관 필드 : 묵시적 내부 조인, 탐색X
            List<Team> many2OneExploration1 = em.createQuery("SELECT t.members FROM Team t", Team.class).getResultList();
            // 복수 값 연관 필드에서 탐색이 필요한 경우 :내부 조인을 하면 탐색을 하기 때문에 필드에 접근 가능
            List<Team> many2OneExploration2 = em.createQuery("SELECT m.name FROM Team t JOIN t.members m", Team.class).getResultList();

            // 아래 두 개는 동일한 쿼리를 실행
            String query1 = "SELECT m FROM Member m WHERE  m = :member";
            String query2 = "SELECT m FROM Member m WHERE  m.id = :memberId";

            // 네임드 쿼리 정의
            /*
            @Entity
            @NamedQuery(
                name = "Member.findByName",
                query="select m from Member m where m.name = :name")
            public class Member {
            }
            */
            // 네임드 쿼리 사용
            List<Member> resultList = em.createNamedQuery("Member.findByName", Member.class).setParameter("name", "n").getResultList();

            // 네임드 쿼리 XML 정의
            /* [META-INF/ormMember.xml
            <?xml version="1.0" encoding="UTF-8"?>
            <entity-mappings xmlns="http://xmlns.jcp.org/xml/ns/persistence/orm" version="2.1">
                <named-query name="Member.findByUsername">
                    <query><![CDATA[
                        select m
                        from Member m
                        where m.username = :username
                    ]]></query>
                </named-query>

                <named-query name="Member.count">
                    <query>select count(m) from Member m</query>
                </named-query>
            </entity-mappings>
            */

            /* META-INF/persistence.xml
            <persistence-unit name="hello" >
                <mapping-file>META-INF/ormMember.xml</mapping-file>
            </persistence-unit>
            */

            // 벌크 쿼리
            List<Product> updateQuery = em.createQuery("UPDATE Product p SET p.price = 1 WHERE p.stockAmount < 5", Product.class).getResultList();

            // 크라이테리아 쿼리 : 사용 안함
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);
            Root<Member> m = query.from(Member.class);
            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("id")), 1);
            List<Member> result = em.createQuery(cq).getResultList();

            // 네이티브 쿼리
            String sql = "SELECT ID, NAME FROM MEMBER WHERE NAME = 'koo'";
            List<Member> nativeResult = em.createNativeQuery(sql, Member.class).getResultList();

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
