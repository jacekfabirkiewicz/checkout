package jacekfabirkiewicz.checkout.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Item {

    @Id
    private String id;

    private String code;

    private String name;

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals( ((Item) obj).getId() );
    }
}
