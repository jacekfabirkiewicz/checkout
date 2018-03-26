package jacekfabirkiewicz.checkout.promotion;

import jacekfabirkiewicz.checkout.common.DtoService;
import jacekfabirkiewicz.checkout.domain.Bundle;
import jacekfabirkiewicz.checkout.domain.Promotion;
import jacekfabirkiewicz.checkout.domain.PromotionDefinition;
import jacekfabirkiewicz.checkout.exception.*;
import jacekfabirkiewicz.checkout.model.BundleDTO;
import jacekfabirkiewicz.checkout.model.PromotionDTO;
import jacekfabirkiewicz.checkout.model.PromotionDefinitionDTO;
import jacekfabirkiewicz.checkout.repository.BundleDAO;
import jacekfabirkiewicz.checkout.repository.PromotionDAO;
import jacekfabirkiewicz.checkout.repository.PromotionDefinitionDAO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
class PromotionControllerService {


    private DtoService dtoService;
    private PromotionDAO promotionDAO;
    private PromotionDefinitionDAO promotionDefinitionDAO;
    private BundleDAO bundleDAO;


    Promotion createPromotion( PromotionDTO promotionDto ) {
        
        return promotionDAO.createPromotion(promotionDto);
    }

    public Collection<PromotionDTO> getPromotionList() {

        List<Promotion> promotionList = promotionDAO.getPromotions();

        if(null != promotionList) {
            return promotionList.stream().map(
                    promotion -> dtoService.getPromotionDTO(promotion)
            ).collect(Collectors.toList());
        }

        return null;
    }

    PromotionDTO getPromotion(String promotionId) {

        Promotion promotion = promotionDAO.find( promotionId );

        if (null == promotion) {
            throw new PromotionNotFoundException( promotionId );
        }

        return dtoService.getPromotionDTO( promotion );
    }

    PromotionDefinition createPromotionDefinition(String promotionId, PromotionDefinitionDTO promotionDefinitionDTO) {
        if(null == promotionId || !promotionId.equals( promotionDefinitionDTO.getPromotionId() )) {
            throw new PromotionIdUnspecifiedException();
        }

        return promotionDefinitionDAO.createPromotionDefinition(promotionDefinitionDTO);
    }

    PromotionDefinitionDTO getPromotionDefinition(String promotionId, String promotionDefinitionId) {

        PromotionDefinition promotionDefinition = promotionDefinitionDAO.find( promotionDefinitionId );

        if (null == promotionDefinition) {
            throw new PromotionDefinitionNotFoundException( promotionDefinitionId );
        }

        if( !promotionId.equals( promotionDefinition.getPromotion().getId() ) ) {
            throw new PromotionDefinitionForPromotionNotFoundException(promotionDefinitionId, promotionId);
        }

        return dtoService.getPromotionDefinitionDTO( promotionDefinition );
    }

    Bundle createBundle(String promotionId,  BundleDTO bundleDTO) {
        if(null == promotionId || !promotionId.equals( bundleDTO.getPromotionId() )) {
            throw new PromotionIdUnspecifiedException();
        }

        return bundleDAO.createBundle( bundleDTO );
    }

    BundleDTO getBundle(String promotionId, String bundleId) {

        Bundle bundle = bundleDAO.find( bundleId );

        if (null == bundle) {
            throw new BundleNotFoundException( bundleId );
        }

        if( !promotionId.equals( bundle.getPromotion().getId() ) ) {
            throw new BundleForPromotionNotFoundException(bundleId, promotionId);
        }

        return dtoService.getBundleDTO( bundle );
    }

}
