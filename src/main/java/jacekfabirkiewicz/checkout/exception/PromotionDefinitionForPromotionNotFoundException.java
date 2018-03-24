package jacekfabirkiewicz.checkout.exception;

import jacekfabirkiewicz.checkout.exception.base.BaseNotFoundException;

public class PromotionDefinitionForPromotionNotFoundException extends BaseNotFoundException {
    public PromotionDefinitionForPromotionNotFoundException(String promotionDefinitionId, String promotionId) {
        super( "PromotionDefinition " + promotionDefinitionId + " not found for promotion " + promotionId);
    }
}
