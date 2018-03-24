package jacekfabirkiewicz.checkout.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BundleDTO {

    private String id;

    private String promotionId;

    private BigDecimal price;

}
