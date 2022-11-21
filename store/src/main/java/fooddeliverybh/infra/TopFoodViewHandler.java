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
    public void whenFoodAdded_then_CREATE_1 (@Payload FoodAdded foodAdded) {
        try {

            if (!foodAdded.validate()) return;

            // view 객체 생성
            TopFood topFood = new TopFood();
            // view 객체에 이벤트의 Value 를 set 함
            topFood.setCount(0);
            topFood.setTotalStar(0);
            // view 레파지 토리에 save
            topFoodRepository.save(topFood);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderEvalutated_then_UPDATE_1(@Payload OrderEvalutated orderEvalutated) {
        try {
            if (!orderEvalutated.validate()) return;
                // view 객체 조회
            Optional<TopFood> topFoodOptional = topFoodRepository.findById(orderEvalutated.getFoodId());

            if( topFoodOptional.isPresent()) {
                 TopFood topFood = topFoodOptional.get();
            // view 객체에 이벤트의 eventDirectValue 를 set 함
                topFood.setCount(topFood.getCount() + 1);
                topFood.setScore(topFood.getScore() + orderEvalutated.getScore());
                // view 레파지 토리에 save
                 topFoodRepository.save(topFood);
                }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

