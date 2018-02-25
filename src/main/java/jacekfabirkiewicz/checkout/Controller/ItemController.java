package jacekfabirkiewicz.checkout.Controller;

import jacekfabirkiewicz.checkout.DTO.ItemDTO;
import jacekfabirkiewicz.checkout.Services.ItemControllerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/items")
@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController {

    private ItemControllerService itemControllerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createItem(@RequestBody ItemDTO itemDto) {
        return itemControllerService.createItem(itemDto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<ItemDTO> getItemList() {
        return itemControllerService.getItemList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{itemId}")
    public ItemDTO getItem(@PathVariable String itemId) {
        return itemControllerService.getItem(itemId);
    }

}
