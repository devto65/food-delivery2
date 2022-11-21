package fooddeliverybh.domain;

import fooddeliverybh.infra.AbstractEvent;
import lombok.Data;
import java.util.*;

@Data
public class FoodAdded extends AbstractEvent {

    private Long id;
    private String name;
    private Long storeId;
}
