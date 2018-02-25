package jacekfabirkiewicz.checkout.Services;

import jacekfabirkiewicz.checkout.DAO.BundleDAO;
import jacekfabirkiewicz.checkout.DAO.ItemDAO;
import jacekfabirkiewicz.checkout.DAO.PromotionDAO;
import jacekfabirkiewicz.checkout.DAO.PromotionDefinitionDAO;
import jacekfabirkiewicz.checkout.DTO.*;
import jacekfabirkiewicz.checkout.DTO.Interfaces.ItemDTOI;
import jacekfabirkiewicz.checkout.Entity.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DtoService {

    private ItemDAO itemDao;
    private BundleDAO bundleDAO;
    private PromotionDAO promotionDAO;
    private PromotionDefinitionDAO promotionDefinitionDAO;
    private PromotionCalculationService promotionCalculationService;

    public ItemDTOI getItemDTO(Item item, ItemDTOI itemDTO) {
        if(null == item) {
            return null;
        }

        itemDTO.setId( item.getId() );
        itemDTO.setName( item.getName() );
        itemDTO.setCode( item.getCode() );
        itemDTO.setPrice( itemDao.getItemPrice(item) );

        return itemDTO;
    }

    public CartItemDTO getCartItemDTO(CartItem cartItem) {
        if(null == cartItem || cartItem.getQuantity().equals(0)) {
            return null;
        }

        Item item = cartItem.getItem();

        CartItemDTO cartItemDTO = (CartItemDTO) getItemDTO(item, new CartItemDTO());
        cartItemDTO.setId(item.getId());
        cartItemDTO.setQuantity( cartItem.getQuantity() );

        return cartItemDTO;
    }

    public CartDTO getCartDTO(Cart cart) {
        if(null == cart) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId( cart.getId());
        cartDTO.setIsPaid( cart.getIsPaid() );
        cartDTO.setCreatedAt( cart.getCreatedAt() ) ;

        List<CartItem> cartItemList = cart.getCartItemList();
        if(null != cartItemList) {
            cartDTO.setCartItemDtoList( cartItemList.stream().map(
                    cartItem -> getCartItemDTO( cartItem )
            ).collect(Collectors.toList()) );
        }

        return cartDTO;

    }

    public PromotionDTO getPromotionDTO(Promotion promotion) {
        if(null == promotion) {
            return null;
        }

        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setId(promotion.getId());
        promotionDTO.setName(promotion.getName());

        Bundle bundle = bundleDAO.findByPromotion( promotion );
        if( null != bundle) {
            promotionDTO.setBundleDTO( getBundleDTO( bundle ) );
        }

        List<PromotionDefinition> promotionDefinitionList = promotionDefinitionDAO.getPromotionDefinitions(promotion);
        if( null != promotionDefinitionList ) {
            promotionDTO.setPromotionDefinitionDTOList( promotionDefinitionList.stream()
                    .map( promotionDefinition -> getPromotionDefinitionDTO( promotionDefinition) )
                    .collect(Collectors.toList()) );
        }

        return promotionDTO;
    }

    public PromotionDefinitionDTO getPromotionDefinitionDTO(PromotionDefinition promotionDefinition) {
        if(null == promotionDefinition) {
            return null;
        }

        PromotionDefinitionDTO promotionDefinitionDTO = new PromotionDefinitionDTO();
        promotionDefinitionDTO.setId( promotionDefinition.getId() );
        promotionDefinitionDTO.setItemId( promotionDefinition.getItem().getId() );
        promotionDefinitionDTO.setPromotionId( promotionDefinition.getPromotion().getId() );
        promotionDefinitionDTO.setQuantity( promotionDefinition.getQuantity() );
        promotionDefinitionDTO.setOverallPriceForQuantity( promotionDefinition.getOverallPriceForQuantity() );
        promotionDefinitionDTO.setDateFrom( promotionDefinition.getDateFrom() );
        promotionDefinitionDTO.setDateTo( promotionDefinition.getDateTo() );

        return promotionDefinitionDTO;
    }

    public BundleDTO getBundleDTO(Bundle bundle) {
        if(null == bundle) {
            return null;
        }

        BundleDTO bundleDTO = new BundleDTO();
        bundleDTO.setId(bundle.getId());
        bundleDTO.setPromotionId( bundle.getPromotion().getId() );
        bundleDTO.setPrice(bundle.getPrice());

        return bundleDTO;
    }

    public CheckoutDTO getCheckoutDTO (Cart cart) {
        if(null == cart) {
            return null;
        }

        CheckoutDTO checkoutDTO = new CheckoutDTO();
        checkoutDTO.setIsPaid( cart.getIsPaid() );
        checkoutDTO.setCreatedAt( cart.getCreatedAt() ) ;

        List<CartItem> cartItemList = cart.getCartItemList();

        Map<Promotion, Integer> calculatedPromotionList = promotionCalculationService.calculatePromotions( cartItemList );

        if(null != cartItemList) {
            checkoutDTO.setCartItemDtoList(
                    cartItemList.stream().map( cartItem -> getCartItemDTO( cartItem ) )
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
            );
        }

        if(null != calculatedPromotionList) {
            checkoutDTO.setCartPromotionDtoList( calculatedPromotionList.entrySet().stream().map(
                    calculatedPromotion -> getCartPromotionDTO( calculatedPromotion.getKey(),
                            calculatedPromotion.getValue() )
            ).collect(Collectors.toList()) );
        }

        return checkoutDTO;

    }

    private CartPromotionDTO getCartPromotionDTO(Promotion promotion, Integer quantity) {

        CartPromotionDTO cartPromotionDTO = new CartPromotionDTO();
        cartPromotionDTO.setPromotionId( promotion.getId() );
        cartPromotionDTO.setName( promotion.getName() );
        cartPromotionDTO.setPrice( promotionDAO.getPrice( promotion ));
        cartPromotionDTO.setQuantity( quantity );
        cartPromotionDTO.setCartItemDtoList( promotionDefinitionDAO.getPromotionDefinitions( promotion )
                .stream().map( promotionDefinition -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setItem( promotionDefinition.getItem() );
                    cartItem.setQuantity( promotionDefinition.getQuantity() );
                    return getCartItemDTO( cartItem );
                }
        ).collect( Collectors.toList() ) );

        return cartPromotionDTO;
    }
}
