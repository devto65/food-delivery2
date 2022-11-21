package fooddeliverybh.domain;

import fooddeliverybh.domain.*;
import fooddeliverybh.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class CookingStarted extends AbstractEvent {

    private Long id;

    public CookingStarted(StoreOrder aggregate){
        super(aggregate);
    }
    public CookingStarted(){
        super();
    }
}
