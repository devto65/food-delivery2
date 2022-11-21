package fooddeliverybh.infra;

import fooddeliverybh.domain.*;
import fooddeliverybh.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TopFoodViewHandler {

	@Autowired
	private TopFoodRepository topFoodRepository;

	@StreamListener(KafkaProcessor.INPUT)
	public void whenFoodAdded_then_CREATE_1(@Payload FoodAdded foodAdded) {
		try {

			if (!foodAdded.validate())
				return;
			// view 객체 생성
			TopFood topFood = new TopFood();
			// view 객체에 이벤트의 Value 를 set 함
			topFood.setEvalCount(0);
			topFood.setScore(Float.valueOf(0));
			topFood.setOrderCount(0);
			topFood.setId(foodAdded.getId());
			topFood.setName(foodAdded.getName());
			// view 레파지 토리에 save
			topFoodRepository.save(topFood);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenOrderEvalutated_then_UPDATE_1(@Payload OrderEvalutated orderEvalutated) {
		try {
			if (!orderEvalutated.validate())
				return;
			// view 객체 조회
			Optional<TopFood> topFoodOptional = topFoodRepository.findById(orderEvalutated.getFoodId());

			if (topFoodOptional.isPresent()) {
				TopFood topFood = topFoodOptional.get();
				topFood.setEvalCount(topFood.getEvalCount() + 1);
				topFood.setTotalScore(topFood.getTotalScore() + orderEvalutated.getScore());
				topFood.setScore((float) topFood.getTotalScore() / topFood.getEvalCount());
				// view 레파지 토리에 save
				topFoodRepository.save(topFood);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenCooked_then_UPDATE_2(@Payload Cooked cooked) {
		try {
			if (!cooked.validate())
				return;
			// view 객체 조회
			Optional<TopFood> topFoodOptional = topFoodRepository.findById(cooked.getFoodId());

			if (topFoodOptional.isPresent()) {
				TopFood topFood = topFoodOptional.get();
				// view 객체에 이벤트의 eventDirectValue 를 set 함
				topFood.setOrderCount(topFood.getOrderCount() + 1);
				// view 레파지 토리에 save
				topFoodRepository.save(topFood);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
