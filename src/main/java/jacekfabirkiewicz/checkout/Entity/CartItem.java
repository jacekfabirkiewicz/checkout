package jacekfabirkiewicz.checkout.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
public class CartItem {

    @DBRef
    private Item item;

    private Integer quantity;
}
