package jacekfabirkiewicz.checkout.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class BundleDTO {

    private String id;

    private String promotionId;

    private BigDecimal price;

}
