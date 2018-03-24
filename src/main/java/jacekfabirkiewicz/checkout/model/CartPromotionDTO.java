package jacekfabirkiewicz.checkout.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartPromotionDTO {

    private String promotionId;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    private List<CartItemDTO> cartItemDtoList;

}
