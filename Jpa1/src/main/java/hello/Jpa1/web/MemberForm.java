package hello.Jpa1.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class MemberForm {

    @NotBlank(message = "Please fill your name")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
