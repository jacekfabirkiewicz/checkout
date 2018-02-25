package jacekfabirkiewicz.checkout.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO extends ItemDTO {

    private Integer quantity;
}
