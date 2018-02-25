package jacekfabirkiewicz.checkout.DAO;

import jacekfabirkiewicz.checkout.Controller.ControllerException.ItemNotFoundException;
import jacekfabirkiewicz.checkout.Controller.ControllerException.PromotionNotFoundException;
import jacekfabirkiewicz.checkout.DTO.BundleDTO;
import jacekfabirkiewicz.checkout.DTO.PromotionDefinitionDTO;
import jacekfabirkiewicz.checkout.Entity.Bundle;
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
public class BundleDAO {

    private MongoOperations mongoOps;
    private PromotionDAO promotionDAO;

    public Bundle createBundle(BundleDTO bundleDTO) {
        Promotion promotion = promotionDAO.find( bundleDTO.getPromotionId());
        if( null == promotion ) {
            throw new PromotionNotFoundException( bundleDTO.getPromotionId() );
        }

        Bundle bundle = findByPromotion(promotion);

        if(null == bundle) {
            bundle = new Bundle();
            bundle.setPromotion( promotion );
        }

        bundle.setPrice( bundleDTO.getPrice() );
        mongoOps.save( bundle );

        return bundle;
    }

    public Bundle findByPromotion(Promotion promotion) {
        return mongoOps.findOne( query( where("promotion").is( promotion ) ), Bundle.class);
    }

    public List<Bundle> getBundles() {
        return mongoOps.findAll(Bundle.class);
    }

    public Bundle find(String bundleId) {
        return mongoOps.findById( bundleId, Bundle.class );
    }
}
