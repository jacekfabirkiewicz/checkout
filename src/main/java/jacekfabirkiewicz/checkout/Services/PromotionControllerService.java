package jacekfabirkiewicz.checkout.Services;

import jacekfabirkiewicz.checkout.Controller.ControllerException.*;
import jacekfabirkiewicz.checkout.DAO.BundleDAO;
import jacekfabirkiewicz.checkout.DAO.PromotionDAO;
import jacekfabirkiewicz.checkout.DAO.PromotionDefinitionDAO;
import jacekfabirkiewicz.checkout.DTO.BundleDTO;
import jacekfabirkiewicz.checkout.DTO.PromotionDTO;
import jacekfabirkiewicz.checkout.DTO.PromotionDefinitionDTO;
import jacekfabirkiewicz.checkout.Entity.Bundle;
import jacekfabirkiewicz.checkout.Entity.Promotion;
import jacekfabirkiewicz.checkout.Entity.PromotionDefinition;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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


    public ResponseEntity createPromotion( PromotionDTO promotionDto ) {
        
        Promotion promotion = promotionDAO.createPromotion(promotionDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{promotionId}")
                .buildAndExpand( promotion.getId() ).toUri();

        return ResponseEntity.created( location ).build();
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

    public ResponseEntity createPromotionDefinition(String promotionId, PromotionDefinitionDTO promotionDefinitionDTO) {
        if(null == promotionId || !promotionId.equals( promotionDefinitionDTO.getPromotionId() )) {
            throw new PromotionIdUnspecifiedException();
        }

        PromotionDefinition promotionDefinition = promotionDefinitionDAO.createPromotionDefinition(promotionDefinitionDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{promotionDefinitionId}")
                .buildAndExpand( promotionDefinition.getId() ).toUri();

        return ResponseEntity.created( location ).build();
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

    public ResponseEntity createBundle(String promotionId,  BundleDTO bundleDTO) {
        if(null == promotionId || !promotionId.equals( bundleDTO.getPromotionId() )) {
            throw new PromotionIdUnspecifiedException();
        }

        Bundle bundle = bundleDAO.createBundle( bundleDTO );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{bundleId}")
                .buildAndExpand( bundle.getId() ).toUri();

        return ResponseEntity.created( location ).build();
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
