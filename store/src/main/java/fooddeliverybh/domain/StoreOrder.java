package fooddeliverybh.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.Table;

import fooddeliverybh.StoreApplication;
import lombok.Data;

@Entity
@Table(name = "StoreOrder_table")
@Data

public class StoreOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private Long foodId;

	private Long orderId;

	private String status;

	private Long paymentId;

	private String address;

	private String options;

	private Long storeId;

	private Long customerId;

	@PostPersist
	public void onPostPersist() {

	}

	public static StoreOrderRepository repository() {
		StoreOrderRepository storeOrderRepository = StoreApplication.applicationContext
				.getBean(StoreOrderRepository.class);
		return storeOrderRepository;
	}
	
	public void finishCook() {
		Cooked cooked = new Cooked(this);
		setStatus("요리완료");
		cooked.publishAfterCommit();
	}

	public void accept() {
		OrderAccepted orderAccepted = new OrderAccepted(this);
		setStatus("접수됨");
		orderAccepted.publishAfterCommit();
	}

	public void reject() {
		OrderRejected orderRejected = new OrderRejected(this);
		setStatus("거부됨");
		orderRejected.publishAfterCommit();
	}

	public void startCook() {
		CookingStarted cookingStarted = new CookingStarted(this);
		setStatus("요리중");
		cookingStarted.publishAfterCommit();		
	}

	public static void addToStoreOrder(OrderCompleted orderCompleted) {
		StoreOrder storeOrder = new StoreOrder();
		storeOrder.setAddress(orderCompleted.getAddress());
		storeOrder.setCustomerId(orderCompleted.getCustomerId());
		storeOrder.setOrderId(orderCompleted.getId());
		repository().save(storeOrder);
	}

	public static void cancel(OrderCanceled orderCanceled) {
		// 주문이 취소됨.
		repository().findByOrderId(orderCanceled.getId())
			.ifPresent(storeOrder -> {
				repository().delete(storeOrder);
			});
	}

}
