package jpabasic.Embedded;

import javax.persistence.*;

@Embeddable
public class Address {
    private String city;
    private String street;
    protected Address () {}
}
