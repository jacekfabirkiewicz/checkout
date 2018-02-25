package jacekfabirkiewicz.checkout.Controller;

import jacekfabirkiewicz.checkout.DTO.BundleDTO;
import jacekfabirkiewicz.checkout.DTO.PromotionDTO;
import jacekfabirkiewicz.checkout.DTO.PromotionDefinitionDTO;
import jacekfabirkiewicz.checkout.Services.PromotionControllerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/promotions")
@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PromotionController {

    private PromotionControllerService promotionControllerService;

    /*
     * Promotions
     */

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createPromotion(@RequestBody PromotionDTO promotionDTO) {
        return promotionControllerService.createPromotion(promotionDTO);
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
        return promotionControllerService.createPromotionDefinition(promotionId, promotionDefinitionDTO);
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
        return promotionControllerService.createBundle(promotionId, bundleDTO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{promotionId}/bundle/{bundleId}")
    public BundleDTO getBundle(@PathVariable String promotionId, @PathVariable String bundleId) {
        return promotionControllerService.getBundle(promotionId, bundleId);
    }

}
