package Basic.MemberItemApp.service;

import Basic.MemberItemApp.domain.Item;
import Basic.MemberItemApp.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    
    private final ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item update(Item item) {

        return itemRepository.save(item);
    }
    
    public Item findById(long itemId) {
        return itemRepository.findById(itemId).get();
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item(null, "itemA", 10000, 10, "itemA.png", "storedA.png", null));
        itemRepository.save(new Item(null, "itemB", 20000, 20, "itemB.png", "storedB.png", null));
    }
}
