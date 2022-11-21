package fooddeliverybh.domain;

import fooddeliverybh.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class OrderEvalutated extends AbstractEvent {

    private Long id;
    private Long foodId;
    private Integer score;
    private Long customerId;
}
