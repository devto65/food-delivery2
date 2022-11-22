package fooddeliverybh.domain;

import fooddeliverybh.domain.FoodAdded;
import fooddeliverybh.StoreApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "Food_table")
@Data

public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private String name;

	private Integer count;

	private Integer score;

	private Long storeId;

	private Boolean available;

	@PostPersist
	public void onPostPersist() {
		FoodAdded foodAdded = new FoodAdded(this);
		foodAdded.publishAfterCommit();
	}

	public static FoodRepository repository() {
		FoodRepository foodRepository = StoreApplication.applicationContext.getBean(FoodRepository.class);
		return foodRepository;
	}

	public static void cancel(OrderCanceled orderCanceled) {
	}

	public static void evalute(OrderEvalutated orderEvalutated) {
		repository().findById(orderEvalutated.getFoodId()).ifPresent(food->{
			food.setCount(food.getCount() + 1);
			food.setScore(food.getScore() + orderEvalutated.getScore());
			repository().save(food);
		});
	}

}
