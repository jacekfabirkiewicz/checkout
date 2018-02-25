package jacekfabirkiewicz.checkout.Controller.ControllerException;

import jacekfabirkiewicz.checkout.Controller.ControllerException.BaseControllerException.BaseNotFoundException;

public class PromotionDefinitionForPromotionNotFoundException extends BaseNotFoundException {
    public PromotionDefinitionForPromotionNotFoundException(String promotionDefinitionId, String promotionId) {
        super( "PromotionDefinition " + promotionDefinitionId + " not found for promotion " + promotionId);
    }
}
