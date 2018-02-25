package jacekfabirkiewicz.checkout.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO extends ItemDTO {

    private Integer quantity;
}
