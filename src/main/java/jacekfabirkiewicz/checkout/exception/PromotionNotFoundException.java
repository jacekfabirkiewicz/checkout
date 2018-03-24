package jacekfabirkiewicz.checkout.exception;

import jacekfabirkiewicz.checkout.exception.base.BaseNotFoundException;

public class PromotionNotFoundException extends BaseNotFoundException {
    public PromotionNotFoundException(String promotionId) {
        super( "Promotion not found " + promotionId );
    }
}
