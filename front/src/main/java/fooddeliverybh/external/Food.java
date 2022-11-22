package fooddeliverybh.external;

import lombok.Data;

@Data
public class Food {

    private Long id;
    private String name;
    private Integer count;
    private Integer score;
    private Long storeId;
    private Boolean available;
}
