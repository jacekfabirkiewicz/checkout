package jacekfabirkiewicz.checkout.Services;

import jacekfabirkiewicz.checkout.DAO.BundleDAO;
import jacekfabirkiewicz.checkout.DAO.ItemDAO;
import jacekfabirkiewicz.checkout.DAO.PromotionDefinitionDAO;
import jacekfabirkiewicz.checkout.DTO.*;
import jacekfabirkiewicz.checkout.DTO.Interfaces.ItemDTOI;
import jacekfabirkiewicz.checkout.Entity.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DtoService {

    private ItemDAO itemDao;
    private BundleDAO bundleDAO;
    private PromotionDefinitionDAO promotionDefinitionDAO;

    public ItemDTOI getItemDto(Item item, ItemDTOI itemDto) {
        if(null == item) {
            return null;
        }

        itemDto.setId( item.getId() );
        itemDto.setName( item.getName() );
        itemDto.setCode( item.getCode() );
        itemDto.setPrice( itemDao.getItemPrice(item) );

        return itemDto;
    }

    public CartItemDTO getCartItemDto(CartItem cartItem) {
        if(null == cartItem) {
            return null;
        }

        Item item = cartItem.getItem();

        CartItemDTO cartItemDto = (CartItemDTO) getItemDto(item, new CartItemDTO());
        cartItemDto.setId(item.getId());
        cartItemDto.setQuantity( cartItem.getQuantity() );

        return cartItemDto;
    }

    public CartDTO getCartDto (Cart cart) {
        if(null == cart) {
            return null;
        }

        CartDTO cartDto = new CartDTO();
        cartDto.setId( cart.getId());
        cartDto.setIsPaid( cart.getIsPaid() );
        cartDto.setCreatedAt( cart.getCreatedAt() ) ;

        List<CartItem> cartItemList = cart.getCartItemList();
        if(null != cartItemList) {
            cartDto.setCartItemDtoList( cartItemList.stream().map(
                    cartItem -> getCartItemDto( cartItem )
            ).collect(Collectors.toList()) );
        }

        return cartDto;

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
        bundleDTO.setPrice(bundle.getPrice());

        return bundleDTO;
    }

}
