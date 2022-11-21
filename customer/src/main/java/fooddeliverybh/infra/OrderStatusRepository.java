package fooddeliverybh.infra;

import fooddeliverybh.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel="orderStatuses", path="orderStatuses")
public interface OrderStatusRepository extends PagingAndSortingRepository<OrderStatus, Long> {

    

    
}
