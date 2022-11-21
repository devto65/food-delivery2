package fooddeliverybh.domain;

import fooddeliverybh.domain.*;
import fooddeliverybh.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class OrderEvalutated extends AbstractEvent {

    private Long id;
    private Long foodId;
    private Integer score;
    private Long customerId;

    public OrderEvalutated(Order aggregate){
        super(aggregate);
    }
    public OrderEvalutated(){
        super();
    }
}
