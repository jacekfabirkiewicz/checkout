package jacekfabirkiewicz.checkout.DTO;

import jacekfabirkiewicz.checkout.Entity.Item;
import jacekfabirkiewicz.checkout.Entity.Promotion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PromotionDefinitionDTO {

    private String id;

    private String promotionId;

    private String itemId;

    private Integer quantity;

    private BigDecimal overallPriceForQuantity;

    private Date dateFrom;

    private Date dateTo;

}
