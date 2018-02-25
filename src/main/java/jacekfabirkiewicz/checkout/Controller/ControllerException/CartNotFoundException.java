package jacekfabirkiewicz.checkout.Controller.ControllerException;

import jacekfabirkiewicz.checkout.Controller.ControllerException.BaseControllerException.BaseNotFoundException;

public class CartNotFoundException extends BaseNotFoundException {
    public CartNotFoundException(String cartId) {
        super( "Cart not found " + cartId );
    }
}
