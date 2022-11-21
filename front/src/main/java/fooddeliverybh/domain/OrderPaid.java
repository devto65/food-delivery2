package fooddeliverybh.domain;

import fooddeliverybh.domain.*;
import fooddeliverybh.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderPaid extends AbstractEvent {

    private Long id;
    private Long orderId;
    private String status;

    public OrderPaid(Payment aggregate){
        super(aggregate);
    }
    public OrderPaid(){
        super();
    }
}
