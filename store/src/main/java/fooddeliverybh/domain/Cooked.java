package fooddeliverybh.domain;

import fooddeliverybh.domain.*;
import fooddeliverybh.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class Cooked extends AbstractEvent {

    private Long id;
    private Long foodId;
    private Long orderId;
    private Long customerId;
    private Long storeId;
    private String address;
    private String options;

    public Cooked(StoreOrder aggregate){
        super(aggregate);
    }
    public Cooked(){
        super();
    }
}
