package jacekfabirkiewicz.checkout.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartPromotionDTO {

    private String promotionId;

    private Integer quantity;

    private BigDecimal price;

    private List<CartItemDTO> cartItemDtoList;

}
