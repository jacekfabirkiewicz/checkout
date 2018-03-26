package jacekfabirkiewicz.checkout.promotion;

import jacekfabirkiewicz.checkout.common.BaseController;
import jacekfabirkiewicz.checkout.domain.Bundle;
import jacekfabirkiewicz.checkout.domain.Promotion;
import jacekfabirkiewicz.checkout.domain.PromotionDefinition;
import jacekfabirkiewicz.checkout.model.BundleDTO;
import jacekfabirkiewicz.checkout.model.PromotionDTO;
import jacekfabirkiewicz.checkout.model.PromotionDefinitionDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/promotions")
@AllArgsConstructor(onConstructor = @__(@Autowired))
class PromotionController extends BaseController {

    private PromotionControllerService promotionControllerService;

    /*
     * Promotions
     */

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity createPromotion(@RequestBody PromotionDTO promotionDTO) {
        Promotion promotion = promotionControllerService.createPromotion(promotionDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{promotionId}")
                .buildAndExpand( promotion.getId() ).toUri();

        return ResponseEntity.created( location ).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<PromotionDTO> getPromotionList() {
        return promotionControllerService.getPromotionList();
    }

    // Displays information about promotion including definitions and bundles
    @RequestMapping(method = RequestMethod.GET, value = "/{promotionId}")
    PromotionDTO getPromotion(@PathVariable String promotionId) {
        return promotionControllerService.getPromotion(promotionId);
    }

    /*
     * Definitions
     */

    @RequestMapping(method = RequestMethod.POST, value = "/{promotionId}/definition")
    ResponseEntity createPromotionDefinition(@PathVariable String promotionId, @RequestBody PromotionDefinitionDTO promotionDefinitionDTO) {
        PromotionDefinition promotionDefinition = promotionControllerService.createPromotionDefinition(promotionId, promotionDefinitionDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{promotionDefinitionId}")
                .buildAndExpand( promotionDefinition.getId() ).toUri();

        return ResponseEntity.created( location ).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{promotionId}/definition/{promotionDefinitionId}")
    PromotionDefinitionDTO getPromotionDefinition(@PathVariable String promotionId, @PathVariable String promotionDefinitionId) {
        return promotionControllerService.getPromotionDefinition(promotionId, promotionDefinitionId);
    }

    /*
     * Bundles
     */

    @RequestMapping(method = RequestMethod.POST, value = "/{promotionId}/bundle")
    ResponseEntity createBundle(@PathVariable String promotionId, @RequestBody BundleDTO bundleDTO) {
        Bundle bundle = promotionControllerService.createBundle(promotionId, bundleDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{bundleId}")
                .buildAndExpand( bundle.getId() ).toUri();

        return ResponseEntity.created( location ).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{promotionId}/bundle/{bundleId}")
    BundleDTO getBundle(@PathVariable String promotionId, @PathVariable String bundleId) {
        return promotionControllerService.getBundle(promotionId, bundleId);
    }

}
