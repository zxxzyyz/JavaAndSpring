package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Item item = (Item) target;

        /* Less function, single line validation
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");
        */

        if (!StringUtils.hasText(item.getItemName())) {
            //bindingResult.addError(new FieldError("item", "itemName", "Item name is necessary"));
            //bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "Item name is necessary"));
            //bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[] {"required.item.itemName"}, null, null));
            errors.rejectValue("itemName", "required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            //errors.addError(new FieldError("item", "price", "Price 1,000 to 1,000,000"));
            //errors.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "Price 1,000 to 1,000,000"));
            //errors.addError(new FieldError("item", "price", item.getPrice(), false, new String[] {"range.item.price"}, new Object[]{1000, 1000000}, null));
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            //errors.addError(new FieldError("item", "quantity", "Quantity 0 to 9,999"));
            //errors.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "Quantity 0 to 9,999"));
            //errors.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[] {"max.item.quantity"}, new Object[]{9999}, null));
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000) {
                //errors.addError(new ObjectError("item", "Quantity 0 to 9,999"));
                //errors.addError(new ObjectError("item",null,null,"Quantity 0 to 9,999"));
                //errors.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000, result},null));
                errors.reject("totalPriceMin", new Object[]{10000, result},null);
            }
        }
    }
}
