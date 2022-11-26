package Basic.MemberItemApp.controller;

import Basic.MemberItemApp.domain.Item;
import Basic.MemberItemApp.dto.ItemDto;
import Basic.MemberItemApp.dto.ItemDtoMapper;
import Basic.MemberItemApp.dto.ItemFindParam;
import Basic.MemberItemApp.file.FileStore;
import Basic.MemberItemApp.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemDtoMapper itemDtoMapper;
    private final FileStore fileStore;

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "items/item";
    }

    @GetMapping
    public String items(@ModelAttribute("itemFindParam") ItemFindParam param, Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "items/items";
    }

    /**
     * Navigate to an HTML form to create an item with GUI.
     */
    @GetMapping("/create")
    public String createForm(@ModelAttribute("item") ItemDto itemDto) {
        return "items/itemCreateForm";
    }

    @PostMapping("/create")
    public String create(
            @Validated @ModelAttribute("item") ItemDto itemDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "items/itemCreateForm";
        }

        Map<String, String> filenames = fileStore.storeFile(itemDto.getAttachFile());
        itemDto.setOriginalFilename(filenames.get("originalFilename"));
        itemDto.setStoredFilename(filenames.get("storedFilename"));

        Item item = itemDtoMapper.toItem(itemDto);
        Item savedItem = itemService.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("create", true);
        return "redirect:/items/{itemId}";
    }

    @GetMapping("/{itemId}/update")
    public String updateForm(@PathVariable("itemId") long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "items/itemUpdateForm";
    }

    @PostMapping("/{itemId}/update")
    public String update(
            @Validated @ModelAttribute("item") ItemDto itemDto,
            BindingResult bindingResult,
            @PathVariable("itemId") Long itemId,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "items/itemUpdateForm";
        }

        Map<String, String> filenames = fileStore.storeFile(itemDto.getAttachFile());
        itemDto.setOriginalFilename(filenames.get("originalFilename"));
        itemDto.setStoredFilename(filenames.get("storedFilename"));

        Item item = itemDtoMapper.toItem(itemDto);
        itemService.save(item);
        redirectAttributes.addAttribute("update", true);
        return "redirect:/items/{itemId}";
    }
}
