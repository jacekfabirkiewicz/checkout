package jacekfabirkiewicz.checkout.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Document
public class Bundle {

    @Id
    private String id;

    @DBRef
    private Promotion promotion;

    private BigDecimal price;

}
