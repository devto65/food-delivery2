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
    @Autowired NotificationLogRepository notificationLogRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderAccepted'")
    public void wheneverOrderAccepted_Katok(@Payload OrderAccepted orderAccepted){

        OrderAccepted event = orderAccepted;
        System.out.println("\n\n##### listener Katok : " + orderAccepted + "\n\n");


        

        // Sample Logic //
        NotificationLog.katok(event);
        

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderRejected'")
    public void wheneverOrderRejected_Katok(@Payload OrderRejected orderRejected){

        OrderRejected event = orderRejected;
        System.out.println("\n\n##### listener Katok : " + orderRejected + "\n\n");


        

        // Sample Logic //
        NotificationLog.katok(event);
        

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='Cooked'")
    public void wheneverCooked_Katok(@Payload Cooked cooked){

        Cooked event = cooked;
        System.out.println("\n\n##### listener Katok : " + cooked + "\n\n");


        

        // Sample Logic //
        NotificationLog.katok(event);
        

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderPaid'")
    public void wheneverOrderPaid_Katok(@Payload OrderPaid orderPaid){

        OrderPaid event = orderPaid;
        System.out.println("\n\n##### listener Katok : " + orderPaid + "\n\n");


        

        // Sample Logic //
        NotificationLog.katok(event);
        

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='OrderPlaced'")
    public void wheneverOrderPlaced_Katok(@Payload OrderPlaced orderPlaced){

        OrderPlaced event = orderPlaced;
        System.out.println("\n\n##### listener Katok : " + orderPlaced + "\n\n");


        

        // Sample Logic //
        NotificationLog.katok(event);
        

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='DeliveryStarted'")
    public void wheneverDeliveryStarted_Katok(@Payload DeliveryStarted deliveryStarted){

        DeliveryStarted event = deliveryStarted;
        System.out.println("\n\n##### listener Katok : " + deliveryStarted + "\n\n");


        

        // Sample Logic //
        NotificationLog.katok(event);
        

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='Delivered'")
    public void wheneverDelivered_Katok(@Payload Delivered delivered){

        Delivered event = delivered;
        System.out.println("\n\n##### listener Katok : " + delivered + "\n\n");


        

        // Sample Logic //
        NotificationLog.katok(event);
        

        

    }
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='CookingStarted'")
    public void wheneverCookingStarted_Katok(@Payload CookingStarted cookingStarted){

        CookingStarted event = cookingStarted;
        System.out.println("\n\n##### listener Katok : " + cookingStarted + "\n\n");


        

        // Sample Logic //
        NotificationLog.katok(event);
        

        

    }

}


