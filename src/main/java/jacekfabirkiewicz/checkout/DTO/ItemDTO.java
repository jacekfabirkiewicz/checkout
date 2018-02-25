package jacekfabirkiewicz.checkout.DTO;

import jacekfabirkiewicz.checkout.DTO.Interfaces.ItemDTOI;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemDTO implements ItemDTOI {

    private String id;

    private String name;

    private String code;

    private BigDecimal price;

}
