package jacekfabirkiewicz.checkout.Controller.ControllerException;

import jacekfabirkiewicz.checkout.Controller.ControllerException.BaseControllerException.BaseNotFoundException;

public class PromotionNotFoundException extends BaseNotFoundException {
    public PromotionNotFoundException(String promotionId) {
        super( "Promotion not found " + promotionId );
    }
}
