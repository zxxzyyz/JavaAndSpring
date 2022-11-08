package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        Item item = new Item("item", 1000, 100);
        Item savedItem = itemRepository.save(item);
        Item foundItem = itemRepository.findById(item.getId());
        Assertions.assertThat(foundItem).isSameAs(savedItem);
    }

    @Test
    void findAll() {
        Item item1 = new Item("item1", 1000, 100);
        Item item2 = new Item("item2", 2000, 200);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> result = itemRepository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(item1, item2);
    }

    @Test
    void updateItem() {
        Item item = new Item("itemA", 1000, 100);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Item updateParam = new Item("itemB", 2000, 200);
        itemRepository.update(itemId, updateParam);

        Item foundItem = itemRepository.findById(itemId);
        Assertions.assertThat(foundItem.getItemName()).isEqualTo(updateParam.getItemName());
        Assertions.assertThat(foundItem.getPrice()).isEqualTo(updateParam.getPrice());
        Assertions.assertThat(foundItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}