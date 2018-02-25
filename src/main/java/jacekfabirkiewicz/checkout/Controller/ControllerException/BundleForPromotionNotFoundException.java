package jacekfabirkiewicz.checkout.Controller.ControllerException;

import jacekfabirkiewicz.checkout.Controller.ControllerException.BaseControllerException.BaseNotFoundException;

public class BundleForPromotionNotFoundException extends BaseNotFoundException {
    public BundleForPromotionNotFoundException(String bundleId, String promotionId) {
        super( "Bundle " + bundleId + " not found for promotion " + promotionId);
    }
}
