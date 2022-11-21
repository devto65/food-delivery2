package fooddeliverybh.infra;

import javax.naming.NameParser;

import javax.naming.NameParser;
import javax.transaction.Transactional;

import fooddeliverybh.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import fooddeliverybh.domain.*;


@Service
@Transactional
public class PolicyHandler{
    @Autowired StoreOrderRepository storeOrderRepository;
    @Autowired FoodRepository foodRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @Autowired
    fooddeliverybh.external.OrderService orderService;

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderPaid'")
    public void wheneverOrderPaid_AddToStoreOrder(@Payload OrderPaid orderPaid){

        OrderPaid event = orderPaid;
        System.out.println("\n\n##### listener AddToStoreOrder : " + orderPaid + "\n\n");

        // REST Request Sample
        
        // orderService.getOrder(/** mapping value needed */);


        

        // Sample Logic //
        StoreOrder.addToStoreOrder(event);
        

        

    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderCanceled'")
    public void wheneverOrderCanceled_Cancel(@Payload OrderCanceled orderCanceled){

        OrderCanceled event = orderCanceled;
        System.out.println("\n\n##### listener Cancel : " + orderCanceled + "\n\n");


        

        // Sample Logic //
        StoreOrder.cancel(event);
        
        Food.cancel(event);
        

        

    }

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderEvalutated'")
    public void wheneverOrderEvalutated_Evalute(@Payload OrderEvalutated orderEvalutated){

        OrderEvalutated event = orderEvalutated;
        System.out.println("\n\n##### listener Evalute : " + orderEvalutated + "\n\n");


        

        // Sample Logic //
        Food.evalute(event);
        

        

    }

}


