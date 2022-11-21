package fooddeliverybh.domain;

import fooddeliverybh.domain.*;
import fooddeliverybh.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class OrderPaid extends AbstractEvent {

    private Long id;
    private Long orderId;
    private String status;
    private Long customerId;
}


