package jacekfabirkiewicz.checkout.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Document
public class ItemPrice {

    @Id
    private String id;

    @DBRef
    private Item item;

    private BigDecimal price;

    private Date dateFrom;

    private Date dateTo;

}
