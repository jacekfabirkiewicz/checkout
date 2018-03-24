package jacekfabirkiewicz.checkout.exception;

import jacekfabirkiewicz.checkout.exception.base.BaseNotFoundException;

public class PromotionDefinitionNotFoundException extends BaseNotFoundException {
    public PromotionDefinitionNotFoundException(String promotionDefinitionId) {
        super( "PromotionDefinition not found " + promotionDefinitionId );
    }
}
