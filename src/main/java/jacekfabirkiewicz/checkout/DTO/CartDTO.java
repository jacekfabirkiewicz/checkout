package jacekfabirkiewicz.checkout.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CartDTO {

    private String id;

    private Boolean isPaid;

    private Date createdAt;

    private List<CartItemDTO> cartItemDtoList;

}
