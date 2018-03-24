package jacekfabirkiewicz.checkout.controller;

import jacekfabirkiewicz.checkout.controller.base.BaseController;
import jacekfabirkiewicz.checkout.entity.Item;
import jacekfabirkiewicz.checkout.model.ItemDTO;
import jacekfabirkiewicz.checkout.service.ItemControllerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/items")
@NoArgsConstructor(force = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController extends BaseController {

    private ItemControllerService itemControllerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createItem(@RequestBody ItemDTO itemDto) {
        Item item = itemControllerService.createItem(itemDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{itemId}")
                .buildAndExpand( item.getId() ).toUri();

        return ResponseEntity.created( location ).build();
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
