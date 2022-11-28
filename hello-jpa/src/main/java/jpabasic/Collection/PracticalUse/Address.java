package jpabasic.Collection.PracticalUse;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@EqualsAndHashCode
public class Address {
    private String city;
    private String street;
    protected Address() {}
}
