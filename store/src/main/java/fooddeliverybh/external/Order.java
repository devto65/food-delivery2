package fooddeliverybh.external;

import lombok.Data;
import java.util.Date;
@Data
public class Order {

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


