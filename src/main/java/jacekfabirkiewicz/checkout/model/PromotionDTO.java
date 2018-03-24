package jacekfabirkiewicz.checkout.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PromotionDTO {

    private String id;

    private String name;

    private BundleDTO bundleDTO;

    private List<PromotionDefinitionDTO> promotionDefinitionDTOList;

}
