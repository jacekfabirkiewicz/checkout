package jacekfabirkiewicz.checkout.service;

import jacekfabirkiewicz.checkout.entity.Bundle;
import jacekfabirkiewicz.checkout.entity.Promotion;
import jacekfabirkiewicz.checkout.entity.PromotionDefinition;
import jacekfabirkiewicz.checkout.exception.*;
import jacekfabirkiewicz.checkout.model.BundleDTO;
import jacekfabirkiewicz.checkout.model.PromotionDTO;
import jacekfabirkiewicz.checkout.model.PromotionDefinitionDTO;
import jacekfabirkiewicz.checkout.repository.BundleDAO;
import jacekfabirkiewicz.checkout.repository.PromotionDAO;
import jacekfabirkiewicz.checkout.repository.PromotionDefinitionDAO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PromotionControllerService {


    private DtoService dtoService;
    private PromotionDAO promotionDAO;
    private PromotionDefinitionDAO promotionDefinitionDAO;
    private BundleDAO bundleDAO;


    public Promotion createPromotion( PromotionDTO promotionDto ) {
        
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

    public PromotionDTO getPromotion(String promotionId) {

        Promotion promotion = promotionDAO.find( promotionId );

        if (null == promotion) {
            throw new PromotionNotFoundException( promotionId );
        }

        return dtoService.getPromotionDTO( promotion );
    }

    public PromotionDefinition createPromotionDefinition(String promotionId, PromotionDefinitionDTO promotionDefinitionDTO) {
        if(null == promotionId || !promotionId.equals( promotionDefinitionDTO.getPromotionId() )) {
            throw new PromotionIdUnspecifiedException();
        }

        return promotionDefinitionDAO.createPromotionDefinition(promotionDefinitionDTO);
    }

    public PromotionDefinitionDTO getPromotionDefinition(String promotionId, String promotionDefinitionId) {

        PromotionDefinition promotionDefinition = promotionDefinitionDAO.find( promotionDefinitionId );

        if (null == promotionDefinition) {
            throw new PromotionDefinitionNotFoundException( promotionDefinitionId );
        }

        if( !promotionId.equals( promotionDefinition.getPromotion().getId() ) ) {
            throw new PromotionDefinitionForPromotionNotFoundException(promotionDefinitionId, promotionId);
        }

        return dtoService.getPromotionDefinitionDTO( promotionDefinition );
    }

    public Bundle createBundle(String promotionId,  BundleDTO bundleDTO) {
        if(null == promotionId || !promotionId.equals( bundleDTO.getPromotionId() )) {
            throw new PromotionIdUnspecifiedException();
        }

        return bundleDAO.createBundle( bundleDTO );
    }

    public BundleDTO getBundle(String promotionId, String bundleId) {

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
