package fooddeliverybh.domain;


import fooddeliverybh.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

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

    public OrderCompleted(Order aggregate) {
        super(aggregate);
    }

    public OrderCompleted() {
        super();
    }
}
