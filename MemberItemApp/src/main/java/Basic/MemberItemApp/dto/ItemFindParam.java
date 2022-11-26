package Basic.MemberItemApp.dto;

import lombok.Data;

@Data
public class ItemFindParam {

    private String itemName;

    private Integer minPrice;

    public ItemFindParam() {}

    public ItemFindParam(String itemName, Integer minPrice) {
        this.itemName = itemName;
        this.minPrice = minPrice;
    }
}
