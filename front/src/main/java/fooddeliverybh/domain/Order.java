package fooddeliverybh.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import fooddeliverybh.FrontApplication;
import lombok.Data;

@Entity
@Table(name = "Order_table")
@Data

public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private Long foodId;

	private Long customerId;

	private String options;

	private String address;

	private String status;

	private Integer score = 0;

	private Long paymentId = 0L;

	private Long storeId;

	@PostPersist
	public void onPostPersist() {
        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();
	}

	@PostRemove
	public void onPostRemove() {
		OrderCanceled orderCanceled = new OrderCanceled(this);
		orderCanceled.publishAfterCommit();
	}

	@PrePersist
	public void onPrePersist() {
	    // Get request from Food
       fooddeliverybh.external.Food food =
    		   FrontApplication.applicationContext.getBean(fooddeliverybh.external.FoodService.class)
    		   	.getFood(getFoodId());
		if (!food.getAvailable()) {
			throw new RuntimeException("현재 주문 불가능한 메뉴입니다.");
		}
	}

	@PreRemove
	public void onPreRemove() {
		if ("요리중".equals(getStatus())
				|| "배달대기".equals(getStatus())
				|| "배달중".equals(getStatus())
				|| "배달완료".equals(getStatus())) {
			throw new RuntimeException("주문을 취소할 수 없습니다.");
		}
	}

	public static OrderRepository repository() {
		OrderRepository orderRepository = FrontApplication.applicationContext.getBean(OrderRepository.class);
		return orderRepository;
	}

	public void evaluate(EvaluateCommand evaluateCommand) {
		setScore(evaluateCommand.getScore());
		OrderEvalutated orderEvalutated = new OrderEvalutated(this);
		orderEvalutated.publishAfterCommit();		
	}

	public static void cancel(OrderRejected orderRejected) {
		repository().findById(orderRejected.getOrderId()).ifPresent(order-> {
			order.setStatus("주문거부됨");
			repository().save(order);
		});
	}

	public static void updateStatus(OrderRejected orderRejected) {
		repository().findById(orderRejected.getOrderId()).ifPresent(order-> {
			order.setStatus("주문거부됨");
			repository().save(order);
		});
	}
	public static void updateStatus(OrderAccepted orderAccepted) {
		repository().findById(orderAccepted.getOrderId()).ifPresent(order-> {
			order.setStatus("주문접수됨");
			repository().save(order);
		});
	}

	public static void updateStatus(OrderPaid orderPaid) {
		repository().findById(orderPaid.getOrderId()).ifPresent(order-> {
			order.setStatus("결제완료");
			repository().save(order);
			OrderCompleted orderCompleted = new OrderCompleted(order);
			orderCompleted.publishAfterCommit();			
		});
	}

	public static void updateStatus(Delivered delivered) {
		repository().findById(delivered.getOrderId()).ifPresent(order-> {
			order.setStatus("배달완료");
			repository().save(order);
			
		});
	}

    public static void updateStatus(CookingStarted cookingStarted) {
		repository().findById(cookingStarted.getOrderId()).ifPresent(order-> {
			order.setStatus("요리중");
			repository().save(order);
		});
    }
}
