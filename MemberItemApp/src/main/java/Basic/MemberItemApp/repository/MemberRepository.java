package Basic.MemberItemApp.repository;

import Basic.MemberItemApp.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUserNameEqualsAndPasswordEquals(String userName, String password);
}
