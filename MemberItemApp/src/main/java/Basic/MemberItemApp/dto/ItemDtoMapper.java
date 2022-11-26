package Basic.MemberItemApp.dto;

import Basic.MemberItemApp.domain.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDtoMapper {

    public Item toItem(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getItemName(),
                itemDto.getPrice(),
                itemDto.getQuantity(),
                itemDto.getOriginalFilename(),
                itemDto.getStoredFilename(),
                null);
    }
}
