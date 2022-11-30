package jpa.springjpa.repository;

import jpa.springjpa.entity.Member;
import jpa.springjpa.entity.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // Return type
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-return-types

    // Defining query
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details

    // Return result
    // Collection : 0 size (not null)
    // Single type : null when no data. NonUniqueResultException(IncorrectResultSizeDataAccessException) when plural data.

    List<Member> findByUsername(String username);
    Streamable<Member> findByAge(int age);

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findDistinctMemberByAge(int age);
    List<Member> findMemberDistinctByAge(int age);

    List<Member> findByUsernameIgnoreCase(String username);

    List<Member> findByUsernameOrderByAgeDesc(String username);

    Long countMemberByUsername(String username);

    Boolean existsMemberByAge(int age);

//    @NamedQuery(name="Member.findByUsername", query="select m from Member m where m.username = :username")
//    @Query(name = "Member.findByUsername")
//    List<Member> any1(@Param("username") String username);

    @Query("SELECT m FROM Member m WHERE m.username = :username")
    List<Member> any2(String username);

    @Query("SELECT m FROM Member m WHERE m.username = :username and m.age = :age")
    List<Member> any3(@Param("username") String username, @Param("age") int age);

    @Query("SELECT m FROM Member m WHERE m.username = :username and m.age = :age")
    List<Member> any4(String username, int age);

    @Query("SELECT m.username FROM Member m")
    List<String> any5();

    @Query("SELECT new jpa.springjpa.entity.MemberDto(m.id, m.username, t.name) FROM Member m JOIN m.team t")
    List<MemberDto> any6();

    @Query("SELECT m FROM Member m WHERE m.username IN :names") // (:names) auto
    List<Member> any7(@Param("names") List<String> names);

    Page<Member> findUsePageByAge(int age, Pageable pageable);

    Slice<Member> findUseSliceByAge(int age, Pageable pageable);

    @Query(value = "SELECT m FROM Member m LEFT JOIN m.team t",
            countQuery = "SELECT COUNT(m) FROM Member m")
    Page<Member> findUsePageCountQuery(int age, Pageable pageable);

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.team")
    List<Member> findFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("SELECT m FROM Member m")
    List<Member> findEG();

//    @NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @QueryHints(value = { @QueryHint(name = "org.hibernate.readOnly", value = "true")}, forCounting = true)
    Page<Member> findByUsername(String name, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findUseLockByUsername(String name);
}
