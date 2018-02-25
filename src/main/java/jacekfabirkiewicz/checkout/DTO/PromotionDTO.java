package jacekfabirkiewicz.checkout.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PromotionDTO {

    private String id;

    private String name;

    private BundleDTO bundleDTO;

    private List<PromotionDefinitionDTO> promotionDefinitionDTOList;

}
