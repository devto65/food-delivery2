package fooddeliverybh.domain;

import fooddeliverybh.domain.*;
import fooddeliverybh.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class OrderPlaced extends AbstractEvent {

    private Long id;
    private Long foodId;
    private Long customerId;
}


