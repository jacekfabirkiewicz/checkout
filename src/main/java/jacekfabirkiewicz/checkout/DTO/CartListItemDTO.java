package jacekfabirkiewicz.checkout.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CartListItemDTO {

    private String id;

    private Boolean isPaid;

    private Date createdAt;

}
