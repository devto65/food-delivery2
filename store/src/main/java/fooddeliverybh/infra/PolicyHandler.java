package fooddeliverybh.infra;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import fooddeliverybh.config.kafka.KafkaProcessor;
import fooddeliverybh.domain.Food;
import fooddeliverybh.domain.FoodRepository;
import fooddeliverybh.domain.OrderCanceled;
import fooddeliverybh.domain.OrderCompleted;
import fooddeliverybh.domain.OrderEvalutated;
import fooddeliverybh.domain.StoreOrder;
import fooddeliverybh.domain.StoreOrderRepository;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    StoreOrderRepository storeOrderRepository;

    @Autowired
    FoodRepository foodRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderCompleted'"
    )
    public void wheneverOrderCompleted_AddToStoreOrder(
        @Payload OrderCompleted orderCompleted
    ) {
        OrderCompleted event = orderCompleted;
        System.out.println(
            "\n\n##### listener AddToStoreOrder : " + orderCompleted + "\n\n"
        );

        // Sample Logic //
        StoreOrder.addToStoreOrder(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderCanceled'"
    )
    public void wheneverOrderCanceled_Cancel(
        @Payload OrderCanceled orderCanceled
    ) {
        OrderCanceled event = orderCanceled;
        System.out.println(
            "\n\n##### listener Cancel : " + orderCanceled + "\n\n"
        );

        // Sample Logic //
        StoreOrder.cancel(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderEvalutated'"
    )
    public void wheneverOrderEvalutated_Evalute(
        @Payload OrderEvalutated orderEvalutated
    ) {
        OrderEvalutated event = orderEvalutated;
        System.out.println(
            "\n\n##### listener Evalute : " + orderEvalutated + "\n\n"
        );

        // Sample Logic //
        Food.evalute(event);
    }
}
