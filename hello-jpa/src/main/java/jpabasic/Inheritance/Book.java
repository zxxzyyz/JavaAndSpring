package jpabasic.Inheritance;

import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("default is entity name")
public class Book extends Item {
    private String author;
}
