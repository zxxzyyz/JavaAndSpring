package jpabasic.BasicMapping;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class BasicMappingMember {

    /* @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Let db handle the id. Flush on persist to get id. (AUTO_INCREMENT).
    */

    /* @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Entity
    @SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1, allocationSize = 1)
    public class Member {
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
        private Long id;

    sequenceName : name of the sequence to get mapped.
    allocationSize : 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨 데이터베이스 시퀀스 값이 하나씩 증가하도록 설정되어 있으면 이 값을 반드시 1로 설정해야 한다)
    initialValue, catalog, schema
    */

    /* @Id @GeneratedValue(strategy = GenerationType.TABLE)
    @Entity
    @TableGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = “MEMBER_SEQ", allocationSize = 1)
    public class Member {
        @Id @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "MEMBER_SEQ_GENERATOR")
        private Long id;

    name, table, pkColumnName, valueColumnNa, pkColumnValue, initialValue, allocationSize, catalog, schema, uniqueConstraints
    */

    // @Id @GeneratedValue(strategy = GenerationType.AUTO) : default
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    // Map to db column name
    @Column(name = "name")
    private String username;

    @Enumerated(EnumType.STRING)
    private RoleType roleTypeString;

    @Enumerated(EnumType.ORDINAL)
    private RoleType roleTypeIncrementByOrder;

    @Transient
    private String mappingDisabled;

    // Time
    @Temporal(TemporalType.DATE) // 2022-11-16
    private Date dateOnly;
    @Temporal(TemporalType.TIME) // 23:59:59
    private Date timeOnly;
    @Temporal(TemporalType.TIMESTAMP)
    private Date oldDateWithTime;
    // Recommended time
    private LocalDateTime newDateWithTime;

    @Lob
    private String description; // CLOB
    @Lob
    private byte[] images; // BLOB

    @Column(insertable = false)
    private String isInsertAllowed;

    @Column(updatable = false)
    private String isUpdateAllowed;

    @Column(nullable = false)
    private String isNullAllowed;

    @Column(unique = true)
    private String isUnique;

    @Column(columnDefinition = "varchar(100) default 'EMPTY'")
    private String setDefinitionManually;

    // Default is 255
    @Column(length = 255)
    private String setMaxLength;

    // Don't work with double/float.
    @Column(precision = 19, scale = 2)
    private BigDecimal forBigTypes;



    public enum RoleType {
        USER, ADMIN
    }
}
