package Basic.MemberItemApp.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemDto {

    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 10000, max = 10000000)
    @NumberFormat(pattern = "###,###")
    private Integer price;

    @NotNull
    @Min(1)
    private Integer quantity;

    private MultipartFile attachFile;

    private String originalFilename;

    private String StoredFilename;
}
