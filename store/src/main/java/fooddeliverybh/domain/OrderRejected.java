package fooddeliverybh.domain;

import fooddeliverybh.domain.*;
import fooddeliverybh.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderRejected extends AbstractEvent {

    private Long id;
    private Long paymentId;
    private Long orderId;
    private String reason;
    private Long customerId;

    public OrderRejected(StoreOrder aggregate){
        super(aggregate);
    }
    public OrderRejected(){
        super();
    }
}
