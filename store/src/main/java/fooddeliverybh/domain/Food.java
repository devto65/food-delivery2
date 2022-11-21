package fooddeliverybh.domain;

import fooddeliverybh.domain.FoodAdded;
import fooddeliverybh.StoreApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name="Food_table")
@Data

public class Food  {

    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String name;
    
    
    
    
    
    private Integer count;
    
    
    
    
    
    private Integer score;
    
    
    
    
    
    private Long storeId;

    @PostPersist
    public void onPostPersist(){


        FoodAdded foodAdded = new FoodAdded(this);
        foodAdded.publishAfterCommit();

    }

    public static FoodRepository repository(){
        FoodRepository foodRepository = StoreApplication.applicationContext.getBean(FoodRepository.class);
        return foodRepository;
    }




    public static void cancel(OrderCanceled orderCanceled){

        /** Example 1:  new item 
        Food food = new Food();
        repository().save(food);

        */

        /** Example 2:  finding and process
        
        repository().findById(orderCanceled.get???()).ifPresent(food->{
            
            food // do something
            repository().save(food);


         });
        */

        
    }
    public static void evalute(OrderEvalutated orderEvalutated){

        /** Example 1:  new item 
        Food food = new Food();
        repository().save(food);

        */

        /** Example 2:  finding and process
        
        repository().findById(orderEvalutated.get???()).ifPresent(food->{
            
            food // do something
            repository().save(food);


         });
        */

        
    }


}
