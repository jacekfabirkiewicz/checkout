package jacekfabirkiewicz.checkout.item;

import jacekfabirkiewicz.checkout.common.DtoService;
import jacekfabirkiewicz.checkout.domain.Item;
import jacekfabirkiewicz.checkout.exception.ItemNotFoundException;
import jacekfabirkiewicz.checkout.model.ItemDTO;
import jacekfabirkiewicz.checkout.repository.ItemDAO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
class ItemControllerService {


    private ItemDAO itemDao;

    private DtoService dtoService;


    Item createItem( ItemDTO itemDTO) {
        
        return itemDao.createItem(itemDTO);
    }

    Collection<ItemDTO> getItemList() {

        List<Item> itemList = itemDao.getItems();

        if(null != itemList) {
            return itemList.stream().map(
                    cart -> (ItemDTO) dtoService.getItemDTO(cart, new ItemDTO())
            ).collect(Collectors.toList());
        }

        return null;
    }

    ItemDTO getItem(String itemId) {

        Item item = itemDao.find( itemId );
        
        if (null == item) {
            throw new ItemNotFoundException( itemId );
        }

        return (ItemDTO) dtoService.getItemDTO( item, new ItemDTO() );
    }



}
