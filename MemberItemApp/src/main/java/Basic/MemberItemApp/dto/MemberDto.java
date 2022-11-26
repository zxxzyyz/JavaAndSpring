package Basic.MemberItemApp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MemberDto {

    @NotBlank
    private String userName;

    @NotBlank
    @Size(min = 2, message = "{String.size.min}")
    @Size(max = 4, message = "{String.size.max}")
    private String password;
}
