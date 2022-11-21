package fooddeliverybh.domain;

import fooddeliverybh.domain.*;
import fooddeliverybh.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderAccepted extends AbstractEvent {

    private Long id;
    private String foodId;
    private Long orderId;
    private Long customerId;

    public OrderAccepted(StoreOrder aggregate){
        super(aggregate);
    }
    public OrderAccepted(){
        super();
    }
}
