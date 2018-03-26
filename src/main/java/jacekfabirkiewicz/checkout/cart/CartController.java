package jacekfabirkiewicz.checkout.cart;

import jacekfabirkiewicz.checkout.common.BaseController;
import jacekfabirkiewicz.checkout.domain.Cart;
import jacekfabirkiewicz.checkout.model.CartDTO;
import jacekfabirkiewicz.checkout.model.CheckoutDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CartController extends BaseController {

    private CartControllerService cartControllerService;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity addCart() {
        Cart cart = cartControllerService.addCart();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{cartId}")
                .buildAndExpand( cart.getId() ).toUri();

        return ResponseEntity.created( location ).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<CartDTO> getCartList() {
        return cartControllerService.getCartList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cartId}")
    CartDTO getCart(@PathVariable String cartId) {
        return cartControllerService.getCart(cartId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{cartId}/put_item/{itemId}")
    CartDTO putToCart(@PathVariable String cartId, @PathVariable String itemId) {
        return cartControllerService.putToCart(cartId, itemId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cartId}/checkout")
    CheckoutDTO getCheckout(@PathVariable String cartId) {
        return cartControllerService.getCheckout(cartId);
    }

}
