package fooddeliverybh.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
    name = "store",
    url = "${api.url.store}",
    fallback = FoodServiceImpl.class
)
public interface FoodService {
    @RequestMapping(method = RequestMethod.GET, path = "/foods/{id}")
    public Food getFood(@PathVariable("id") Long id);
}
