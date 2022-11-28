package jpabasic.Inheritance;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "default is dtype")
public abstract class Item {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private Integer price;

    /* Examples

    1-1 @Inheritance = SINGLE_TABLE
    Create table Item : dtype, id, name, price, author, artist

    1-2 @Inheritance = SINGLE_TABLE, @DiscriminatorColumn
    Create table Item : dtype, id, name, price, author, artist

    2-1 @Inheritance = JOINED
    Create table Item : id, name, price, author, artist
    Create table Album : id, artist
    Create table Book : id, author

    2-2 @Inheritance = JOINED, @DiscriminatorColumn
    Create table Item : dtype, id, name, price, author, artist
    Create table Album : id, artist
    Create table Book : id, author

    3-1 @Inheritance = TABLE_PER_CLASS, @DiscriminatorColumn
    Create table Album : id, name, price, artist
    Create table Book : id, name, price, author
    If Item class type is used for variable type, union select will happen to get value.

    */
}
