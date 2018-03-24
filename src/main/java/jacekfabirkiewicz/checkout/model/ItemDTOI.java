package jacekfabirkiewicz.checkout.model;

import java.math.BigDecimal;

public interface ItemDTOI {

    void setId(String id);

    void setName(String name);

    void setCode(String code);

    void setPrice(BigDecimal price);
}
