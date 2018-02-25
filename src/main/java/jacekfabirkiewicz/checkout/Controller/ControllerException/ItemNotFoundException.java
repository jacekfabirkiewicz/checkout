package jacekfabirkiewicz.checkout.Controller.ControllerException;

import jacekfabirkiewicz.checkout.Controller.ControllerException.BaseControllerException.BaseNotFoundException;

public class ItemNotFoundException extends BaseNotFoundException {
    public ItemNotFoundException(String itemId) {
        super( "Item not found " + itemId );
    }
}
