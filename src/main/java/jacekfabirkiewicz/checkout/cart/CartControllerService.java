package jacekfabirkiewicz.checkout.cart;

import jacekfabirkiewicz.checkout.common.DtoService;
import jacekfabirkiewicz.checkout.domain.Cart;
import jacekfabirkiewicz.checkout.domain.Item;
import jacekfabirkiewicz.checkout.exception.CartNotFoundException;
import jacekfabirkiewicz.checkout.exception.ItemNotFoundException;
import jacekfabirkiewicz.checkout.model.CartDTO;
import jacekfabirkiewicz.checkout.model.CheckoutDTO;
import jacekfabirkiewicz.checkout.repository.CartDAO;
import jacekfabirkiewicz.checkout.repository.ItemDAO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
 class CartControllerService {


    private CartDAO cartDAO;
    private ItemDAO itemDAO;
    private DtoService dtoService;


    Cart addCart() {

        return cartDAO.createCart();
    }

    Collection<CartDTO> getCartList() {

        List<Cart> cartList = cartDAO.getUnpaidCarts();

        if(null != cartList) {
            return cartList.stream().map(
                    cart -> dtoService.getCartDTO(cart)
            ).collect(Collectors.toList());
        }

        return null;
    }

    CartDTO getCart(String cartId) {

        Cart cart = cartDAO.find( cartId );

        if (null == cart) {
            throw new CartNotFoundException(cartId);
        }

        return dtoService.getCartDTO(cart);
    }

    CartDTO putToCart(String cartId, String itemId) {

        Item item = itemDAO.find( itemId );

        if (null == item) {
            throw new ItemNotFoundException( itemId );
        }

        Cart cart = cartDAO.find( cartId );

        if (null == cart) {
            throw new CartNotFoundException(cartId);
        }

        cartDAO.putToCart(cart, item);

        return dtoService.getCartDTO(cart);
    }

    CheckoutDTO getCheckout(String cartId) {

        Cart cart = cartDAO.find( cartId );

        if (null == cart) {
            throw new CartNotFoundException(cartId);
        }

        return dtoService.getCheckoutDTO(cart);
    }

}
