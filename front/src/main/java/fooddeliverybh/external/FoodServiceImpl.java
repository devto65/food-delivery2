package fooddeliverybh.external;

import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService {

    /**
     * Fallback
     */
    public Food getFood(Long id) {
        Food food = new Food();
        food.setId(id);
        food.setAvailable(true);
        return food;
    }
}
