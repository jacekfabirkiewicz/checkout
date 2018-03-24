package jacekfabirkiewicz.checkout.controller;

import jacekfabirkiewicz.checkout.controller.base.BaseController;
import jacekfabirkiewicz.checkout.entity.Bundle;
import jacekfabirkiewicz.checkout.entity.Promotion;
import jacekfabirkiewicz.checkout.entity.PromotionDefinition;
import jacekfabirkiewicz.checkout.model.BundleDTO;
import jacekfabirkiewicz.checkout.model.PromotionDTO;
import jacekfabirkiewicz.checkout.model.PromotionDefinitionDTO;
import jacekfabirkiewicz.checkout.service.PromotionControllerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/promotions")
@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PromotionController extends BaseController {

    private PromotionControllerService promotionControllerService;

    /*
     * Promotions
     */

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createPromotion(@RequestBody PromotionDTO promotionDTO) {
        Promotion promotion = promotionControllerService.createPromotion(promotionDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{promotionId}")
                .buildAndExpand( promotion.getId() ).toUri();

        return ResponseEntity.created( location ).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<PromotionDTO> getPromotionList() {
        return promotionControllerService.getPromotionList();
    }

    // Displays information about promotion including definitions and bundles
    @RequestMapping(method = RequestMethod.GET, value = "/{promotionId}")
    public PromotionDTO getPromotion(@PathVariable String promotionId) {
        return promotionControllerService.getPromotion(promotionId);
    }

    /*
     * Definitions
     */

    @RequestMapping(method = RequestMethod.POST, value = "/{promotionId}/definition")
    public ResponseEntity createPromotionDefinition(@PathVariable String promotionId, @RequestBody PromotionDefinitionDTO promotionDefinitionDTO) {
        PromotionDefinition promotionDefinition = promotionControllerService.createPromotionDefinition(promotionId, promotionDefinitionDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{promotionDefinitionId}")
                .buildAndExpand( promotionDefinition.getId() ).toUri();

        return ResponseEntity.created( location ).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{promotionId}/definition/{promotionDefinitionId}")
    public PromotionDefinitionDTO getPromotionDefinition(@PathVariable String promotionId, @PathVariable String promotionDefinitionId) {
        return promotionControllerService.getPromotionDefinition(promotionId, promotionDefinitionId);
    }

    /*
     * Bundles
     */

    @RequestMapping(method = RequestMethod.POST, value = "/{promotionId}/bundle")
    public ResponseEntity createBundle(@PathVariable String promotionId, @RequestBody BundleDTO bundleDTO) {
        Bundle bundle = promotionControllerService.createBundle(promotionId, bundleDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{bundleId}")
                .buildAndExpand( bundle.getId() ).toUri();

        return ResponseEntity.created( location ).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{promotionId}/bundle/{bundleId}")
    public BundleDTO getBundle(@PathVariable String promotionId, @PathVariable String bundleId) {
        return promotionControllerService.getBundle(promotionId, bundleId);
    }

}
