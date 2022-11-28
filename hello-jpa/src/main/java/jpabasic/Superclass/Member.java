package jpabasic.Superclass;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    // Create table Member : id, name, createdDate, modifiedDate
}
