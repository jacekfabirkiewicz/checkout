package jacekfabirkiewicz.checkout.DAO;

import jacekfabirkiewicz.checkout.DTO.ItemDTO;
import jacekfabirkiewicz.checkout.Entity.Item;
import jacekfabirkiewicz.checkout.Entity.ItemPrice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Repository
public class ItemDAO {

    private MongoOperations mongoOps;

    public BigDecimal getItemPrice( Item item ) {
        Date now = new Date();

        ItemPrice itemPrice =  mongoOps.findOne( query(
                where( "item" ).is( item )
                .and("dateFrom").lt( now )
                .orOperator( where("dateTo").gt( now ), where("dateTo").exists( false ) )
        ), ItemPrice.class );

        if(null != itemPrice) {
            return itemPrice.getPrice();
        }

        return null;
    }

    public Item createItem(ItemDTO itemDTO) {
        Item item = mongoOps.findOne( query( where("code").is( itemDTO.getCode() )), Item.class);

        if(null == item) {
            item = new Item();
        }

        item.setCode( itemDTO.getCode() );
        item.setName( itemDTO.getName() );
        mongoOps.save( item );

        // set price for item only initially
        ItemPrice itemPrice = mongoOps.findOne( query( where("item").is( item )), ItemPrice.class);
        if(null == itemPrice) {
            itemPrice = new ItemPrice();
            itemPrice.setItem(item);
            itemPrice.setPrice(itemDTO.getPrice());
            itemPrice.setDateFrom(new Date());
            itemPrice.setDateTo(null);
            mongoOps.save(itemPrice);
        }

        return item;
    }

    public List<Item> getItems() {
        return mongoOps.findAll(Item.class);
    }

    public Item find(String itemId) {
        return mongoOps.findById( itemId, Item.class );
    }
}
