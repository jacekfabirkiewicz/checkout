package jacekfabirkiewicz.checkout.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Document
@CompoundIndexes({
        @CompoundIndex(name = "unique_definition", def = "{'promotion': 1, 'item': 1}")
})
public class PromotionDefinition {

    @Id
    private String id;

    @DBRef
    private Promotion promotion;

    @DBRef
    private Item item;

    private Integer quantity;

    private BigDecimal overallPriceForQuantity;

    private Date dateFrom;

    private Date dateTo;
}
