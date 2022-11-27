package jpabasic.BasicMapping;

import lombok.*;
import javax.persistence.*;

@Entity @Getter @Setter
@Table(
        name = "name of the table",
        catalog = "name of the catalog",
        schema = "name of the schema",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "age"})})
public class UniqueMember {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    // Unique, Not null, up to 10 letters
    @Column(nullable = false, length = 10)
    private String name;

    private Integer age;
}
