package fooddeliverybh.domain;

import fooddeliverybh.domain.*;
import fooddeliverybh.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderCompleted extends AbstractEvent {

    private Long id;
    private Long foodId;
    private Long customerId;
    private String options;
    private String address;
    private String status;
    private Integer score;
    private Long paymentId;
    private Long storeId;
}
