package jacekfabirkiewicz.checkout.exception;

import jacekfabirkiewicz.checkout.exception.base.BaseNotFoundException;

public class ItemNotFoundException extends BaseNotFoundException {
    public ItemNotFoundException(String itemId) {
        super( "Item not found " + itemId );
    }
}
