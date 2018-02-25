package jacekfabirkiewicz.checkout.DAO;

import jacekfabirkiewicz.checkout.Entity.Cart;
import jacekfabirkiewicz.checkout.Entity.Item;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CartDAOTest {

    @Mock
    private MongoOperations mongoOpsMock;

    @InjectMocks
    private CartDAO dao;

    private Cart cart;
    private Item item1, item2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        doNothing().when(mongoOpsMock).save(any(Object.class));

        cart = new Cart();

        item1 = new Item();
        item1.setId("1");
        item1.setName("name1");
        item1.setCode("code1");

        item2 = new Item();
        item2.setId("2");
        item2.setName("name2");
        item2.setCode("code2");
    }

    @Test
    public void testPutToCart_quantityAfterAddingItems() {

        dao.putToCart(cart, item1);
        assertEquals(Integer.valueOf(1), cart.getCartItemList().get(0).getQuantity());

        dao.putToCart(cart, item1);
        assertEquals(Integer.valueOf(2), cart.getCartItemList().get(0).getQuantity());

        dao.putToCart(cart, item2);
        assertEquals(Integer.valueOf(1), cart.getCartItemList().get(1).getQuantity());
    }
}