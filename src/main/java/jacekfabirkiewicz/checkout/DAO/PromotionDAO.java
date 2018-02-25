package jacekfabirkiewicz.checkout.DAO;

import jacekfabirkiewicz.checkout.DTO.ItemDTO;
import jacekfabirkiewicz.checkout.DTO.PromotionDTO;
import jacekfabirkiewicz.checkout.Entity.Item;
import jacekfabirkiewicz.checkout.Entity.ItemPrice;
import jacekfabirkiewicz.checkout.Entity.Promotion;
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
public class PromotionDAO {

    private MongoOperations mongoOps;

    public Promotion createPromotion(PromotionDTO promotionDTO) {
        Promotion promotion = mongoOps.findOne( query( where("name").is( promotionDTO.getName() )), Promotion.class);

        if(null == promotion) {
            promotion = new Promotion();
        }

        promotion.setName( promotionDTO.getName() );
        mongoOps.save( promotion );

        return promotion;
    }

    public List<Promotion> getPromotions() {
        return mongoOps.findAll(Promotion.class);
    }

    public Promotion find(String promotionId) {
        return mongoOps.findById( promotionId, Promotion.class );
    }
}
