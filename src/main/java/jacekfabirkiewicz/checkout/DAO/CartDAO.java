package jacekfabirkiewicz.checkout.DAO;

import jacekfabirkiewicz.checkout.Entity.Cart;
import jacekfabirkiewicz.checkout.Entity.CartItem;
import jacekfabirkiewicz.checkout.Entity.Item;
import jacekfabirkiewicz.checkout.Entity.ItemPrice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Repository
public class CartDAO {

    private MongoOperations mongoOps;

    public Cart createCart() {
        Cart cart = new Cart();
        cart.setIsPaid( false );
        cart.setCreatedAt( new Date() );
        mongoOps.save(cart);

        return cart;
    }

    public List<Cart> getUnpaidCarts() {
        return mongoOps.find( query( where("isPaid").is(false) ), Cart.class);
    }

    public Cart find(String cartId) {

        return mongoOps.findById( cartId, Cart.class );
    }

    public void putToCart(Cart cart, Item item) {
        if(null != cart.getCartItemList() && !cart.getCartItemList().isEmpty()) {
            // there are items in Cart

            CartItem existingCartItem = getCartItemIfExists(cart, item);

            if(null != existingCartItem) {
                // increment number of Item existing in Cart
                existingCartItem.setQuantity( existingCartItem.getQuantity() + 1 );
            } else {
                // add Item to Cart
                CartItem cartItem = createCartItem(item);
                cart.getCartItemList().add(cartItem);
            }

        } else {
            // Cart is empty
            List<CartItem> cartItemList = new ArrayList<>();
            cart.setCartItemList(cartItemList);
            cartItemList.add( createCartItem( item ) );
        }

        mongoOps.save( cart );
    }

    private CartItem getCartItemIfExists(Cart cart, Item item) {

        return cart.getCartItemList().stream().filter(
                cartItem -> cartItem.getItem().equals(item)
        ).findFirst().orElse(null);
    }

    private CartItem createCartItem(Item item) {
        CartItem cartItem = new CartItem();
        cartItem.setItem( item );
        cartItem.setQuantity( 1 );
        return cartItem;
    }


}
