package jacekfabirkiewicz.checkout.exception;

import jacekfabirkiewicz.checkout.exception.base.BaseNotFoundException;

public class BundleNotFoundException extends BaseNotFoundException {
    public BundleNotFoundException(String bundleId) {
        super( "Bundle not found " + bundleId );
    }
}
