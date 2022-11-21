package fooddeliverybh.domain;

import fooddeliverybh.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class Cooked extends AbstractEvent {

    private Long id;
    private String foodId;
    private Long orderId;
    private Long cutomerId;
    private Long storeId;
    private String address;
    private String options;
}
