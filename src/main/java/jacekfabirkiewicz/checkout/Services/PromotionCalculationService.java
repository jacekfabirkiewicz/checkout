package jacekfabirkiewicz.checkout.Services;

import jacekfabirkiewicz.checkout.DAO.PromotionDefinitionDAO;
import jacekfabirkiewicz.checkout.Entity.CartItem;
import jacekfabirkiewicz.checkout.Entity.Promotion;
import jacekfabirkiewicz.checkout.Entity.PromotionDefinition;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PromotionCalculationService {

    private PromotionDefinitionDAO promotionDefinitionDAO;

    /**
     * Matches available promotions for cartItems, modifies cartItemList input on match:
     * 1. Finds simple promotions of type "N*productX" and removes item from cartItemList if matches
     * 2. Finds bundle promotions of type "N1*productX1 + N2*productX2 + ..." and removes items from cartItemList if matches
     *
     * @param cartItemList Initial list of cart items
     * @return Map of found promotions and their quantities
     */
    public Map<Promotion, Integer> calculatePromotions(List<CartItem> cartItemList) {
        Map<Promotion, Integer> promotionsFound = new HashMap<>();

        if (null == cartItemList ) {
            return promotionsFound;
        }

        List<PromotionDefinition> promotionDefinitionList = getPromotionDefinitions(cartItemList);

        findSimplePromotions( cartItemList, promotionsFound, promotionDefinitionList );
        findBundlePromotions( cartItemList, promotionsFound, promotionDefinitionList );

        return promotionsFound;
    }

    private List<PromotionDefinition> getPromotionDefinitions(List<CartItem> cartItemList) {
        List<PromotionDefinition> promotionDefinitionList = promotionDefinitionDAO
                .getPromotionDefinitions( cartItemList.stream().map(
                        cartItem -> cartItem.getItem()
                ).collect(Collectors.toList()) );

        if(null != promotionDefinitionList) {
            // order by item quantity desc
            promotionDefinitionList.sort(Comparator.comparing(PromotionDefinition::getQuantity, (q1, q2) -> -q1.compareTo(q2)));
        }

        return promotionDefinitionList;
    }

    private void findSimplePromotions( List<CartItem> cartItemList,
                                       Map<Promotion, Integer> promotionsFound,
                                       List<PromotionDefinition> promotionDefinitionList ) {

        // for each item, filter promotion defs he is in, check if it is simple promotion, check whether there is enough
        // items in cartItemList. If so, assign promotion to promotionsFound and decrement quantity from cartItemList

        for(CartItem cartItem : cartItemList) {

            promotionDefinitionList.stream().filter(
                    promotionDefinition -> promotionDefinition.getItem().equals( cartItem.getItem() )
                            && promotionDefinitionDAO.getPromotionDefinitions( promotionDefinition.getPromotion() ).size() == 1
                            && null != promotionDefinition.getOverallPriceForQuantity()
                            && promotionDefinition.getQuantity() <= cartItem.getQuantity()
            ).forEach( promotionDefinition -> {
                    cartItem.setQuantity( cartItem.getQuantity() - promotionDefinition.getQuantity() );
                    addPromotionToFoundPromotions( promotionDefinition.getPromotion(), promotionsFound);
            });
        }

    }

    private void findBundlePromotions( List<CartItem> cartItemList,
                                       Map<Promotion, Integer> promotionsFound,
                                       List<PromotionDefinition> promotionDefinitionList ) {

        // for each item, filter promotion defs he is in, check if it is bundle promotion, check if there is enough of
        // other items to satisfy bundle requirements, check whether there is enough
        // items in cartItemList. If so, assign promotion to promotionsFound and decrement quantity from cartItemList

        for(CartItem cartItem : cartItemList) {

            promotionDefinitionList.stream().filter( //  bundle definitions
                    promotionDefinition -> promotionDefinition.getItem().equals( cartItem.getItem() )
                            && promotionDefinitionDAO.getPromotionDefinitions( promotionDefinition.getPromotion() ).size() >= 1
                            && null == promotionDefinition.getOverallPriceForQuantity()
                            && promotionDefinition.getQuantity() <= cartItem.getQuantity()
            ).forEach( promotionDefinition -> {

                List<PromotionDefinition> bundlePromotionDefinitionList = promotionDefinitionDAO
                        .getPromotionDefinitions( promotionDefinition.getPromotion() );

                boolean isEnoughItems = true;
                for( PromotionDefinition bundlePromotionDefinition: bundlePromotionDefinitionList) {
                    // checking if there is enough other items to satisfy bundle requirements
                    if( cartItemList.stream().filter(
                            item -> item.getItem().equals(bundlePromotionDefinition.getItem())
                                    && bundlePromotionDefinition.getQuantity() <= item.getQuantity()
                    ).collect(Collectors.toList()).size() == 0) {
                        isEnoughItems = false;
                        break;
                    }
                }

                if(isEnoughItems) {
                    bundlePromotionDefinitionList.forEach( bundlePromotionDefinition -> {
                        cartItemList.stream().filter(
                                item -> item.getItem().equals(bundlePromotionDefinition.getItem())
                                        && bundlePromotionDefinition.getQuantity() <= item.getQuantity()
                        ).forEach( item ->
                                item.setQuantity( item.getQuantity() - bundlePromotionDefinition.getQuantity() )
                        );
                    });
                    addPromotionToFoundPromotions( promotionDefinition.getPromotion(), promotionsFound);
                }

            });
        }

    }

    private void addPromotionToFoundPromotions( Promotion promotion, Map<Promotion, Integer> promotionsFound) {
        if( promotionsFound.containsKey( promotion )) {
            promotionsFound.put( promotion, promotionsFound.get(promotion) + 1 );
        } else {
            promotionsFound.put( promotion, 1 );
        }
    }


}
