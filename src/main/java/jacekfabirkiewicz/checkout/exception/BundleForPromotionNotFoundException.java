package jacekfabirkiewicz.checkout.exception;

import jacekfabirkiewicz.checkout.exception.base.BaseNotFoundException;

public class BundleForPromotionNotFoundException extends BaseNotFoundException {
    public BundleForPromotionNotFoundException(String bundleId, String promotionId) {
        super( "Bundle " + bundleId + " not found for promotion " + promotionId);
    }
}
