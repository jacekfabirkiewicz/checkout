package jacekfabirkiewicz.checkout.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document
public class Cart {

    @Id
    private String id;

    private Boolean isPaid;

    private Date createdAt;

    private List<CartItem> cartItemList;

}
