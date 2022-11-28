package jpabasic.Collection.PracticalUse;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @Setter
@Table(name = "shipping_address")
public class AddressEntity {
    @Id @GeneratedValue
    private Long id;
    private Address address;

    protected AddressEntity() {}

    public AddressEntity(String city, String street) {
        this.address = new Address(city, street);
    }
}
