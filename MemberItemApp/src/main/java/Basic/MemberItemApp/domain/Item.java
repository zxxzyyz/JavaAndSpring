package Basic.MemberItemApp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private Integer price;

    private Integer quantity;

    private String originalFilename;

    private String StoredFilename;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
