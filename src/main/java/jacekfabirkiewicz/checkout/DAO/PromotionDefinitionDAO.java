package jacekfabirkiewicz.checkout.DAO;

import jacekfabirkiewicz.checkout.Controller.ControllerException.ItemNotFoundException;
import jacekfabirkiewicz.checkout.Controller.ControllerException.PromotionNotFoundException;
import jacekfabirkiewicz.checkout.DTO.PromotionDTO;
import jacekfabirkiewicz.checkout.DTO.PromotionDefinitionDTO;
import jacekfabirkiewicz.checkout.Entity.Item;
import jacekfabirkiewicz.checkout.Entity.Promotion;
import jacekfabirkiewicz.checkout.Entity.PromotionDefinition;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Repository
public class PromotionDefinitionDAO {

    private MongoOperations mongoOps;
    private ItemDAO itemDAO;
    private PromotionDAO promotionDAO;

    public PromotionDefinition createPromotionDefinition(PromotionDefinitionDTO promotionDefinitionDTO) {
        Promotion promotion = promotionDAO.find( promotionDefinitionDTO.getPromotionId());
        if( null == promotion ) {
            throw new PromotionNotFoundException( promotionDefinitionDTO.getPromotionId() );
        }

        Item item = itemDAO.find( promotionDefinitionDTO.getItemId() );
        if( null == item ) {
            throw new ItemNotFoundException( promotionDefinitionDTO.getItemId() );
        }

        // promotion + item must be unique
        PromotionDefinition promotionDefinition = mongoOps.findOne(
                query( where("promotion").is( promotion ).and("item").is( item ) ), PromotionDefinition.class);

        if(null == promotionDefinition) {
            promotionDefinition = new PromotionDefinition();
            promotionDefinition.setPromotion( promotion );
            promotionDefinition.setItem( item );
        }

        promotionDefinition.setQuantity( promotionDefinitionDTO.getQuantity() );
        promotionDefinition.setOverallPriceForQuantity( promotionDefinitionDTO.getOverallPriceForQuantity() );
        promotionDefinition.setDateFrom( promotionDefinitionDTO.getDateFrom() );
        promotionDefinition.setDateTo( promotionDefinitionDTO.getDateTo() );
        mongoOps.save( promotionDefinition );

        return promotionDefinition;
    }

    public List<PromotionDefinition> getPromotionDefinitions(Promotion promotion) {
        return mongoOps.find( query( where("promotion").is( promotion ) ), PromotionDefinition.class );
    }

    public PromotionDefinition find(String promotionDefinitionId) {
        return mongoOps.findById( promotionDefinitionId, PromotionDefinition.class );
    }
}
