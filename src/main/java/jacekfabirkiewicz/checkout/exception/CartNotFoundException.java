package jacekfabirkiewicz.checkout.exception;

import jacekfabirkiewicz.checkout.exception.base.BaseNotFoundException;

public class CartNotFoundException extends BaseNotFoundException {
    public CartNotFoundException(String cartId) {
        super( "Cart not found " + cartId );
    }
}
