package fooddeliverybh.external;

import lombok.Data;

@Data
public class Payment {
    private Long id;
    private Long orderId;
    private String status;
    private Long customerId;
}
