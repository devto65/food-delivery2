package fooddeliverybh.domain;

import fooddeliverybh.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class Delivered extends AbstractEvent {

    private Long id;
    private Long orderId;
}
