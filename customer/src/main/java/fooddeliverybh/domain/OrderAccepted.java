package fooddeliverybh.domain;

import fooddeliverybh.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class OrderAccepted extends AbstractEvent {

    private Long id;
    private String foodId;
    private Long orderId;
    private Long customerId;
}
