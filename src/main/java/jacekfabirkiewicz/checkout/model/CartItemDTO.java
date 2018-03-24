package jacekfabirkiewicz.checkout.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO extends ItemDTO {

    private Integer quantity;
}
