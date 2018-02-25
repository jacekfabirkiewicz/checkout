package jacekfabirkiewicz.checkout.Controller.ControllerException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PromotionIdUnspecifiedException extends RuntimeException {
    public PromotionIdUnspecifiedException() {
        super("Parameter promotionId must match RequestBody promotionId");
    }
}
