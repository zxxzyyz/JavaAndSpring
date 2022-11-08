package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/basic/items")
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item foundItem = itemRepository.findById(itemId);

        if (foundItem == null) {

        }
        model.addAttribute("item", foundItem);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes,
            Model model) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        // As path variable
        redirectAttributes.addAttribute("itemId", item.getId());
        // As query parameter
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }
    public String addItemV2(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes) {
        itemRepository.save(item);
        // auto add:
        //model.addAttribute("item", item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }
    public String addItemV3(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        itemRepository.save(item);
        // auto add: class name "Item" -> "item" goes into attributeName
        //model.addAttribute("item", item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }
    public String addItemV4(Item item, RedirectAttributes redirectAttributes) {
        itemRepository.save(item);
        // when annotation is omitted, @ModelAttribute is default
        // auto add: class name "Item" -> "item" goes into attributeName
        //model.addAttribute("item", item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void createTestData() {
        itemRepository.save(new Item("itemA", 1000, 100));
        itemRepository.save(new Item("itemB", 2000, 50));
    }
}
