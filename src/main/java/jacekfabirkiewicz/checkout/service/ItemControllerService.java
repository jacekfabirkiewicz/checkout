package jacekfabirkiewicz.checkout.service;

import jacekfabirkiewicz.checkout.entity.Item;
import jacekfabirkiewicz.checkout.exception.ItemNotFoundException;
import jacekfabirkiewicz.checkout.model.ItemDTO;
import jacekfabirkiewicz.checkout.repository.ItemDAO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ItemControllerService {


    private ItemDAO itemDao;

    private DtoService dtoService;


    public Item createItem( ItemDTO itemDTO) {
        
        return itemDao.createItem(itemDTO);
    }

    public Collection<ItemDTO> getItemList() {

        List<Item> itemList = itemDao.getItems();

        if(null != itemList) {
            return itemList.stream().map(
                    cart -> (ItemDTO) dtoService.getItemDTO(cart, new ItemDTO())
            ).collect(Collectors.toList());
        }

        return null;
    }

    public ItemDTO getItem(String itemId) {

        Item item = itemDao.find( itemId );
        
        if (null == item) {
            throw new ItemNotFoundException( itemId );
        }

        return (ItemDTO) dtoService.getItemDTO( item, new ItemDTO() );
    }



}
