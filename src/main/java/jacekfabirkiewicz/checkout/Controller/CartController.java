package jacekfabirkiewicz.checkout.Controller;

import jacekfabirkiewicz.checkout.DTO.CartDTO;
import jacekfabirkiewicz.checkout.DTO.CheckoutDTO;
import jacekfabirkiewicz.checkout.Services.CartControllerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/carts")
@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartController {

    private CartControllerService cartControllerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addCart() {
        return cartControllerService.addCart();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<CartDTO> getCartList() {
        return cartControllerService.getCartList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cartId}")
    public CartDTO getCart(@PathVariable String cartId) {
        return cartControllerService.getCart(cartId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{cartId}/put_item/{itemId}")
    public CartDTO putToCart(@PathVariable String cartId, @PathVariable String itemId) {
        return cartControllerService.putToCart(cartId, itemId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cartId}/checkout")
    public CheckoutDTO getCheckout(@PathVariable String cartId) {
        return cartControllerService.getCheckout(cartId);
    }

}
