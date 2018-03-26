package jacekfabirkiewicz.checkout.item;

import jacekfabirkiewicz.checkout.common.BaseController;
import jacekfabirkiewicz.checkout.domain.Item;
import jacekfabirkiewicz.checkout.model.ItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/items")
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ItemController extends BaseController {

    ItemControllerService itemControllerService;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity createItem(@RequestBody ItemDTO itemDto) {
        Item item = itemControllerService.createItem(itemDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{itemId}")
                .buildAndExpand( item.getId() ).toUri();

        return ResponseEntity.created( location ).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<ItemDTO> getItemList() {
        return itemControllerService.getItemList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{itemId}")
    ItemDTO getItem(@PathVariable String itemId) {
        return itemControllerService.getItem(itemId);
    }

}
