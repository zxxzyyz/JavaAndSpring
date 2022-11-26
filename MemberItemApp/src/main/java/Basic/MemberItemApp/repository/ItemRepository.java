package Basic.MemberItemApp.repository;

import Basic.MemberItemApp.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
