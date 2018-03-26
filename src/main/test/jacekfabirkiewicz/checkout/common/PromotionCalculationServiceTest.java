package jacekfabirkiewicz.checkout.common;

import jacekfabirkiewicz.checkout.domain.CartItem;
import jacekfabirkiewicz.checkout.domain.Item;
import jacekfabirkiewicz.checkout.domain.Promotion;
import jacekfabirkiewicz.checkout.domain.PromotionDefinition;
import jacekfabirkiewicz.checkout.repository.PromotionDefinitionDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;

public class PromotionCalculationServiceTest {

    @Mock
    private PromotionDefinitionDAO promotionDefinitionDAO;

    @InjectMocks
    private PromotionCalculationService promotionCalculationService;

    private Item itemA, itemB, itemC;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        itemA = new Item("1", "A", "A");
        itemB = new Item("2", "B", "B");
        itemC = new Item("3", "C", "C");

    }

    @Test
    public void testCalculatePromotions_locateSimplePromotion() {

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add( new CartItem(itemA, 1));
        cartItemList.add( new CartItem(itemB, 3));

        Promotion promotion = new Promotion("1", "2B for 40");

        List<PromotionDefinition> promotionDefinitionList = new ArrayList<>();
        promotionDefinitionList.add(new PromotionDefinition("1", promotion, itemB, 2, BigDecimal.valueOf(40L), null, null));
        Mockito.when(promotionDefinitionDAO.getPromotionDefinitions(anyList())).thenReturn( promotionDefinitionList );
        Mockito.when(promotionDefinitionDAO.getPromotionDefinitions(any(Promotion.class))).thenReturn(promotionDefinitionList );

        Map<Promotion, Integer> calculatedPromotionList = promotionCalculationService.calculatePromotions(cartItemList);
        assertEquals( Integer.valueOf(1), cartItemList.get(1).getQuantity());
        assertEquals( Integer.valueOf(1), calculatedPromotionList.get(promotion));
    }

    @Test
    public void testCalculatePromotions_locateBundlePromotion() {

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add( new CartItem(itemA, 1));
        cartItemList.add( new CartItem(itemB, 4));
        cartItemList.add( new CartItem(itemC, 2));

        Promotion promotionBundle = new Promotion("2", "1A + 2B for 40");
        List<PromotionDefinition> promotionDefinitionListBundle = new ArrayList<>();
        promotionDefinitionListBundle.add(new PromotionDefinition("1", promotionBundle, itemA, 1, null, null, null));
        promotionDefinitionListBundle.add(new PromotionDefinition("2", promotionBundle, itemB, 2, null, null, null));

        Promotion promotion = new Promotion("1", "2B for 40");
        List<PromotionDefinition> promotionDefinitionList = new ArrayList<>();
        promotionDefinitionList.add(new PromotionDefinition("3", promotion, itemB, 2, BigDecimal.valueOf(40L), null, null));

        List<PromotionDefinition> promotionDefinitionListAll = new ArrayList<>();
        promotionDefinitionListAll.addAll(promotionDefinitionList);
        promotionDefinitionListAll.addAll(promotionDefinitionListBundle);

        Mockito.when(promotionDefinitionDAO.getPromotionDefinitions(anyList())).thenReturn( promotionDefinitionListAll );
        Mockito.when(promotionDefinitionDAO.getPromotionDefinitions(promotionBundle)).thenReturn( promotionDefinitionListBundle );
        Mockito.when(promotionDefinitionDAO.getPromotionDefinitions(promotion)).thenReturn( promotionDefinitionList );

        Map<Promotion, Integer> calculatedPromotionList = promotionCalculationService.calculatePromotions(cartItemList);
        assertEquals( Integer.valueOf(0), cartItemList.get(0).getQuantity());
        assertEquals( Integer.valueOf(0), cartItemList.get(1).getQuantity());
        assertEquals( Integer.valueOf(1), calculatedPromotionList.get(promotion));
        assertEquals( Integer.valueOf(1), calculatedPromotionList.get(promotionBundle));
    }
}