package jacekfabirkiewicz.checkout.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class CartItem {

    @DBRef
    private Item item;

    private Integer quantity;
}
