package jacekfabirkiewicz.checkout.repository;

import jacekfabirkiewicz.checkout.domain.Bundle;
import jacekfabirkiewicz.checkout.domain.Promotion;
import jacekfabirkiewicz.checkout.domain.PromotionDefinition;
import jacekfabirkiewicz.checkout.model.PromotionDTO;
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
    private PromotionDefinitionDAO promotionDefinitionDAO;

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

    public BigDecimal getPrice(Promotion promotion ) {
        Date now = new Date();

        // return price defined on PromotionDefinition if exists (only for promo type N*productX)
        List<PromotionDefinition> promotionDefinitionList = promotionDefinitionDAO.getPromotionDefinitions( promotion );
        if(null != promotionDefinitionList
                && promotionDefinitionList.size() == 1
                && null != promotionDefinitionList.get(0).getOverallPriceForQuantity()) {
            return promotionDefinitionList.get(0).getOverallPriceForQuantity();
        }

        // return price defined on Bundle (for promo type { N0*productX0 + N1*ProductX1 + ...} )
        Bundle bundle =  mongoOps.findOne( query(
                where( "promotion" ).is( promotion )
        ), Bundle.class );

        if(null != bundle) {
            return bundle.getPrice();
        }

        return null;
    }
}
