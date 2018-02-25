package jacekfabirkiewicz.checkout.Services;

import jacekfabirkiewicz.checkout.Controller.ControllerException.ItemNotFoundException;
import jacekfabirkiewicz.checkout.DAO.ItemDAO;
import jacekfabirkiewicz.checkout.DTO.ItemDTO;
import jacekfabirkiewicz.checkout.Entity.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ItemControllerService {


    private ItemDAO itemDao;

    private DtoService dtoService;


    public ResponseEntity createItem( ItemDTO itemDTO) {
        
        Item item = itemDao.createItem(itemDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{itemId}")
                .buildAndExpand( item.getId() ).toUri();

        return ResponseEntity.created( location ).build();
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
