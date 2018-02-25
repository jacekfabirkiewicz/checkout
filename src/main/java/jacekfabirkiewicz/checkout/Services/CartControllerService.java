package jacekfabirkiewicz.checkout.Services;

import jacekfabirkiewicz.checkout.Controller.ControllerException.CartNotFoundException;
import jacekfabirkiewicz.checkout.Controller.ControllerException.ItemNotFoundException;
import jacekfabirkiewicz.checkout.DAO.CartDAO;
import jacekfabirkiewicz.checkout.DAO.ItemDAO;
import jacekfabirkiewicz.checkout.DTO.CartDTO;
import jacekfabirkiewicz.checkout.Entity.Cart;
import jacekfabirkiewicz.checkout.Entity.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CartControllerService {


    private CartDAO cartDAO;
    private ItemDAO itemDAO;
    private DtoService dtoService;


    public ResponseEntity addCart() {
        
        Cart cart = cartDAO.createCart();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{cartId}")
                .buildAndExpand( cart.getId() ).toUri();

        return ResponseEntity.created( location ).build();
    }

    public Collection<CartDTO> getCartList() {

        List<Cart> cartList = cartDAO.getUnpaidCarts();

        if(null != cartList) {
            return cartList.stream().map(
                    cart -> dtoService.getCartDto(cart)
            ).collect(Collectors.toList());
        }

        return null;
    }

    public CartDTO getCart(@PathVariable String cartId) {

        Cart cart = cartDAO.find( cartId );
        
        if (null == cart) {
            throw new CartNotFoundException(cartId);
        }

        return dtoService.getCartDto(cart);
    }

    public CartDTO putToCart(String cartId, String itemId) {

        Item item = itemDAO.find( itemId );

        if (null == item) {
            throw new ItemNotFoundException( itemId );
        }

        Cart cart = cartDAO.find( cartId );

        if (null == cart) {
            throw new CartNotFoundException(cartId);
        }

        cartDAO.putToCart(cart, item);

        return dtoService.getCartDto(cart);
    }

}
