package jpa.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.querydsl.entity.Member;
import jpa.querydsl.entity.QMember;
import jpa.querydsl.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;


import java.util.List;

import static jpa.querydsl.entity.QMember.*;
import static jpa.querydsl.entity.QTeam.*;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class QuerydslBeginnerTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory query;

    @PersistenceUnit
    EntityManagerFactory emf;

    @BeforeEach
    void before() {
        query = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    void startJPQL() {
        String qlString =
                "select m from Member m " +
                        "where m.username = :username";
        Member findMember = em.createQuery(qlString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();
       assertThat(findMember.getUsername()).isEqualTo("member1");
    }
    @Test
    void startQuerydsl() {
        //QMember m = new QMember("m"); //별칭 직접 지정
        //QMember m = QMember.member; //기본 인스턴스 사용
        Member findMember = query
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void search() {
        Member member = query.selectFrom(QMember.member).where(QMember.member.username.eq("member1").and(QMember.member.age.eq(10))).fetchOne();
        assertThat(member.getUsername()).isEqualTo("member1");
    }

    @Test
    void allKindsOfSearch() {
        member.username.eq("member1"); // username = 'member1'
        member.username.ne("member1"); //username != 'member1'
        member.username.eq("member1").not(); // username != 'member1'
        member.username.isNotNull(); //이름이 is not null
        member.age.in(10, 20); // age in (10,20)
        member.age.notIn(10, 20); // age not in (10, 20)
        member.age.between(10,30); //between 10, 30
        member.age.goe(30); // age >= 30
        member.age.gt(30); // age > 30
        member.age.loe(30); // age <= 30
        member.age.lt(30); // age < 30
        member.username.like("member%"); //like 검색
        member.username.contains("member"); // like ‘%member%’ 검색
        member.username.startsWith("member"); //like ‘member%’ 검색
    }

    @Test
    void whereQueryCommaIsAnd() {
        List<Member> result1 = query
                .selectFrom(member)
                .where(member.username.eq("member1"),
                        member.age.eq(10))
                .fetch();
        assertThat(result1.size()).isEqualTo(1);
    }

    @Test
    void allKindsOfResult() {
        //List
        List<Member> fetch = query
                .selectFrom(member)
                .fetch();
        //단 건
        Member findMember1 = query
                .selectFrom(member)
                .fetchOne(); //NonUniqueResultException
        //처음 한 건 조회
        Member findMember2 = query
                .selectFrom(member)
                .fetchFirst();
        //페이징에서 사용
        List<Member> results = query
                .selectFrom(member)
                .offset(3)
                .limit(5)
                .fetch();
        //count 쿼리로 변경
        long count = query
                .selectFrom(member)
                .fetch().size();
    }

    @Test
    void paging() {
        QueryResults<Member> queryResults = query
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults(); // deprecated
        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(2);
        /*
        참고: 실무에서 페이징 쿼리를 작성할 때, 데이터를 조회하는 쿼리는 여러 테이블을 조인해야 하지만,
        count 쿼리는 조인이 필요 없는 경우도 있다. 그런데 이렇게 자동화된 count 쿼리는 원본 쿼리와 같이 모두
        조인을 해버리기 때문에 성능이 안나올 수 있다. count 쿼리에 조인이 필요없는 성능 최적화가 필요하다면,
        count 전용 쿼리를 별도로 작성해야 한다.
        */
    }

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순(desc)
     * 2. 회원 이름 올림차순(asc)
     * 단 1에서 회원 나이가 없으면 처음에 출력(nullsFirst)
     * 단 2에서 회원 이름이 없으면 마지막에 출력(nullsLast)
     */
    @Test
    void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));
        List<Member> result = query
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc().nullsFirst(), member.username.asc().nullsLast())
                .fetch();
        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    /**
     * COUNT(m) //회원수
     * SUM(m.age) //나이 합
     * AVG(m.age) //평균 나이
     * MAX(m.age) //최대 나이
     * MIN(m.age) //최소 나이
     */
    @Test
    void aggregation() {
        List<Tuple> result = query
                .select(member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min())
                .from(member)
                .fetch();
        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라.
     */
    @Test
    void group() {
        List<Tuple> result = query
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)//.having(condition)
                .fetch();
        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);
        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);
        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }

    /**
     * 팀 A에 소속된 모든 회원
     */
    @Test
    void join() {
        List<Member> result = query
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();
        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    /**
     * 세타 조인(연관관계가 없는 필드로 조인)
     * 회원의 이름이 팀 이름과 같은 회원 조회
     */
    @Test
    void crossJoin() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        List<Member> result = query
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();
        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    /**
     * 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * JPQL: SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'teamA'
     * SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name='teamA'
     */
    @Test
    void joinOnFiltering() {
        List<Tuple> result = query
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA"))
                .fetch();
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
//            tuple = [Member(id=3, username=member1, age=10), Team(id=1, name=teamA)]
//            tuple = [Member(id=4, username=member2, age=20), Team(id=1, name=teamA)]
//            tuple = [Member(id=5, username=member3, age=30), null]
//            tuple = [Member(id=6, username=member4, age=40), null]
        }
    }

    /**
     * 연관관계 없는 엔티티 외부 조인. 회원의 이름과 팀의 이름이 같은 대상 외부 조인
     * JPQL: SELECT m, t FROM Member m LEFT JOIN Team t on m.username = t.name
     * SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.username = t.name
     */
    @Test
    void joinOnNoRelation() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        List<Tuple> result = query
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
//            tuple = [Member(id=3, username=member1, age=10), null]
//            tuple = [Member(id=4, username=member2, age=20), null]
//            tuple = [Member(id=5, username=member3, age=30), null]
//            tuple = [Member(id=6, username=member4, age=40), null]
//            tuple = [Member(id=7, username=teamA, age=0), Team(id=1, name=teamA)]
//            tuple = [Member(id=8, username=teamB, age=0), Team(id=2, name=teamB)]
        }
    }

    @Test
    void fetchJoinNo() {
        em.flush();
        em.clear();
        Member findMember = query
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();
        boolean isTeamLoaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(isTeamLoaded).as("페치 조인 미적용").isFalse();
    }

    @Test
    void fetchJoinUse() {
        em.flush();
        em.clear();
        Member findMember = query
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();
        boolean isTeamLoaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(isTeamLoaded).as("페치 조인 적용").isTrue();
    }

    /**
     * 나이가 가장 많은 회원 조회
     */
    @Test
    void subQuery() {
        QMember subMember = new QMember("subMember");
        List<Member> result = query
                .selectFrom(member)
                .where(member.age.eq(JPAExpressions.select(subMember.age.max()).from(subMember)))
                .fetch();
        assertThat(result).extracting("age").containsExactly(40);
    }
    /**
     * 나이가 평균 나이 이상인 회원
     */
    @Test
    void subQueryGoe() {
        QMember subMember = new QMember("subMember");
        List<Member> result = query
                .selectFrom(member)
                .where(member.age.goe(JPAExpressions.select(subMember.age.avg()).from(subMember)))
                .fetch();
        assertThat(result).extracting("age").containsExactly(30, 40);
    }

    /**
     * 서브쿼리 여러 건 처리, IN 사용
     */
    @Test
    void subQueryIn() {
        QMember subMember = new QMember("subMember");
        List<Member> result = query
                .selectFrom(member)
                .where(member.age.in(JPAExpressions.select(subMember.age).from(subMember).where(subMember.age.gt(10))))
                .fetch();
        assertThat(result).extracting("age").containsExactly(20, 30, 40);
    }

    /**
     * SELECT 서브쿼리
     */
    @Test
    void subQuerySelect() {
        QMember subMember = new QMember("subMember");
        List<Tuple> fetch = query
                .select(member.username,
                        JPAExpressions.select(subMember.age.avg()).from(subMember))
                .from(member)
                .fetch();
        for (Tuple tuple : fetch) {
            System.out.println("username = " + tuple.get(member.username));
            System.out.println("age = " + tuple.get(JPAExpressions.select(subMember.age.avg()).from(subMember)));
//            username = member1
//            age = 25.0
//            username = member2
//            age = 25.0
//            username = member3
//            age = 25.0
//            username = member4
//            age = 25.0
        }
        /*
        from 절의 서브쿼리 한계
        JPA JPQL 서브쿼리의 한계점으로 from 절의 서브쿼리(인라인 뷰)는 지원하지 않는다. 당연히 Querydsl
        도 지원하지 않는다. 하이버네이트 구현체를 사용하면 select 절의 서브쿼리는 지원한다. Querydsl도
        하이버네이트 구현체를 사용하면 select 절의 서브쿼리를 지원한다.
        from 절의 서브쿼리 해결방안
        1. 서브쿼리를 join으로 변경한다. (가능한 상황도 있고, 불가능한 상황도 있다.)
        2. 애플리케이션에서 쿼리를 2번 분리해서 실행한다.
        3. nativeSQL을 사용한다.
        */
    }

    @Test
    void case1() {
        List<String> result = query
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();
        for (String s : result) {
            System.out.println("s = " + s);
//            s = 열살
//            s = 스무살
//            s = 기타
//            s = 기타
        }
    }

    @Test
    void case2() {
        List<String> result = query
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("0-20")
                        .when(member.age.between(21, 30)).then("21-30")
                        .otherwise("else"))
                .from(member)
                .fetch();
        for (String s : result) {
            System.out.println("s = " + s);
//            s = 0-20
//            s = 0-20
//            s = 21-30
//            s = else
        }
    }

    @Test
    void case3() {
        NumberExpression<Integer> rankOrder = new CaseBuilder()
                .when(member.age.between(0, 20)).then(2)
                .when(member.age.between(21, 30)).then(1)
                .otherwise(3);

        List<Tuple> result = query.select(member, rankOrder)
                .from(member)
                .orderBy(rankOrder.desc())
                .fetch();

        for (Tuple tuple : result) {
            Member m = tuple.get(member);
            Integer rank = tuple.get(rankOrder);
            System.out.println("m = " + m + " rank = " + rank);
//            m = Member(id=6, username=member4, age=40) rank = 3
//            m = Member(id=3, username=member1, age=10) rank = 2
//            m = Member(id=4, username=member2, age=20) rank = 2
//            m = Member(id=5, username=member3, age=30) rank = 1
        }
    }

    @Test
    void literal() {
        Tuple result = query.select(member, Expressions.constant("AbCd"))
                .from(member)
                .fetchFirst();
        System.out.println("result = " + result);
//        result = [Member(id=3, username=member1, age=10), AbCd]
    }

    @Test
    void stringConcat() {
        List<String> result = query.select(member.username.concat("___").concat(member.age.stringValue()))
                .from(member)
                .fetch();
        for (String s : result) {
            System.out.println("s = " + s);
//            s = member1___10
//            s = member2___20
//            s = member3___30
//            s = member4___40
        }
    }
}