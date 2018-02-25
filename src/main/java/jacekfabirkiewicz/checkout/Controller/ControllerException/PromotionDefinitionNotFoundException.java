package jacekfabirkiewicz.checkout.Controller.ControllerException;

import jacekfabirkiewicz.checkout.Controller.ControllerException.BaseControllerException.BaseNotFoundException;

public class PromotionDefinitionNotFoundException extends BaseNotFoundException {
    public PromotionDefinitionNotFoundException(String promotionDefinitionId) {
        super( "PromotionDefinition not found " + promotionDefinitionId );
    }
}
