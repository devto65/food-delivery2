package fooddeliverybh.domain;

import fooddeliverybh.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CookingStarted extends AbstractEvent {

    private Long id;
    private Long orderId;
    private Long customerId;
}
