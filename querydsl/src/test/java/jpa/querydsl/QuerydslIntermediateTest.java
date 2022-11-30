package jpa.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.querydsl.dto.MemberDto;
import jpa.querydsl.dto.QMemberDto;
import jpa.querydsl.dto.UserDto;
import jpa.querydsl.entity.Member;
import jpa.querydsl.entity.QMember;
import jpa.querydsl.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static jpa.querydsl.entity.QMember.member;
import static jpa.querydsl.entity.QTeam.team;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.iterable;


@SpringBootTest
@Transactional
class QuerydslIntermediateTest {
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
    void findDtoBySetter() {
        List<MemberDto> result = query
                .select(Projections.bean(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
//            memberDto = MemberDto(username=member1, age=10)
//            memberDto = MemberDto(username=member2, age=20)
//            memberDto = MemberDto(username=member3, age=30)
//            memberDto = MemberDto(username=member4, age=40)
        }
    }

    @Test
    void findDtoByField() {
        List<MemberDto> result = query
                .select(Projections.fields(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findDtoByConstructor() {
        List<MemberDto> result = query
                .select(Projections.constructor(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void differentDto1() {
        List<UserDto> result = query
                .select(Projections.fields(UserDto.class, member.username, member.age))
                .from(member)
                .fetch();
        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
//            userDto = UserDto(name=null, age=10)
//            userDto = UserDto(name=null, age=20)
//            userDto = UserDto(name=null, age=30)
//            userDto = UserDto(name=null, age=40)
        }
    }

    @Test
    void differentDto2() {
        List<UserDto> result = query
                .select(Projections.fields(UserDto.class, member.username.as("name"), member.age))
                .from(member)
                .fetch();
        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
//            userDto = UserDto(name=member1, age=10)
//            userDto = UserDto(name=member2, age=20)
//            userDto = UserDto(name=member3, age=30)
//            userDto = UserDto(name=member4, age=40)
        }
    }

    @Test
    void differentDto3() {
        QMember subMember = new QMember("subMember");
        List<UserDto> result = query
                .select(Projections.fields(UserDto.class,
                                member.username.as("name"),
                                ExpressionUtils.as(JPAExpressions
                                        .select(subMember.age.max())
                                        .from(subMember), "age")))
                .from(member)
                .fetch();
        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }
    }

    @Test
    void findDtoByQueryProjection() {
        List<MemberDto> result = query
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void dynamicQueryBuilder() {
        String usernameParam = "member1";
        Integer ageParam = 10;
        List<Member> result = searchMember1(usernameParam, ageParam);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }
    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (usernameCond != null) builder.and(member.username.eq(usernameCond));
        if (ageCond != null) builder.and(member.age.eq(ageCond));
        return query.selectFrom(member).where(builder).fetch();
    }

    @Test
    void dynamicQueryWhere() {
        String usernameParam = "member1";
        Integer ageParam = 10;
        List<Member> result = searchMember2(usernameParam, ageParam);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }
    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return query.selectFrom(member).where(usernameEq(usernameCond), ageEq(ageCond)).fetch();
    }
    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }
    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    @Test
    void bulkUpdate1() {
        long count = query.update(member).set(member.username, "test").where(member.age.lt(25)).execute();
        // 벌크 연산은 영속성 컨텍스트에 데이터를 반영하지 않기 때문에 초기화가 필요함.
        em.flush();
        em.clear();
    }

    @Test
    void bulkUpdate2() {
        query.update(member).set(member.age, member.age.add(-1)).execute();
    }

    @Test
    void bulkDelete1() {
        query.delete(member).where(member.age.gt(18)).execute();
    }
}