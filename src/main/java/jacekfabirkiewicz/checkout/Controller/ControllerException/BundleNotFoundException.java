package jacekfabirkiewicz.checkout.Controller.ControllerException;

import jacekfabirkiewicz.checkout.Controller.ControllerException.BaseControllerException.BaseNotFoundException;

public class BundleNotFoundException extends BaseNotFoundException {
    public BundleNotFoundException(String bundleId) {
        super( "Bundle not found " + bundleId );
    }
}
