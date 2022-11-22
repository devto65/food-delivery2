package fooddeliverybh.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import fooddeliverybh.CustomerApplication;
import lombok.Data;

@Entity
@Table(name = "NotificationLog_table")
@Data

public class NotificationLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private Long customerId;
	private Long orderId;
	private String message;

	public static NotificationLogRepository repository() {
		NotificationLogRepository notificationLogRepository = CustomerApplication.applicationContext
				.getBean(NotificationLogRepository.class);
		return notificationLogRepository;
	}

	public static void katok(OrderAccepted orderAccepted) {
		NotificationLog notificationLog = new NotificationLog();
		notificationLog.setOrderId(orderAccepted.getOrderId());
		notificationLog.setCustomerId(orderAccepted.getCustomerId());
		notificationLog.setMessage("주문접수됨");
		repository().save(notificationLog);
	}

	public static void katok(OrderRejected orderRejected) {
		NotificationLog notificationLog = new NotificationLog();
		notificationLog.setOrderId(orderRejected.getOrderId());
		notificationLog.setCustomerId(orderRejected.getCustomerId());
		notificationLog.setMessage("주문거부됨-" + orderRejected.getReason());
		repository().save(notificationLog);
	}

	public static void katok(Cooked cooked) {
		NotificationLog notificationLog = new NotificationLog();
		notificationLog.setOrderId(cooked.getOrderId());
		notificationLog.setCustomerId(cooked.getCustomerId());
		notificationLog.setMessage("요리완료");
		repository().save(notificationLog);

	}

	public static void katok(OrderPaid orderPaid) {
		NotificationLog notificationLog = new NotificationLog();
		notificationLog.setOrderId(orderPaid.getOrderId());
		notificationLog.setCustomerId(orderPaid.getCustomerId());
		notificationLog.setMessage("결제완료");
		repository().save(notificationLog);
	}

	public static void katok(OrderPlaced orderPlaced) {
		NotificationLog notificationLog = new NotificationLog();
		notificationLog.setOrderId(orderPlaced.getId());
		notificationLog.setCustomerId(orderPlaced.getCustomerId());
		notificationLog.setMessage("결제완료");
		repository().save(notificationLog);		
	}

	public static void katok(DeliveryStarted deliveryStarted) {
		NotificationLog notificationLog = new NotificationLog();
		notificationLog.setOrderId(deliveryStarted.getOrderId());
		notificationLog.setCustomerId(deliveryStarted.getCustomerId());
		notificationLog.setMessage("배달시작");
		repository().save(notificationLog);
	}

	public static void katok(Delivered delivered) {
		NotificationLog notificationLog = new NotificationLog();
		notificationLog.setOrderId(delivered.getOrderId());
		notificationLog.setCustomerId(delivered.getCustomerId());
		notificationLog.setMessage("배달완료");
		repository().save(notificationLog);
	}

	public static void katok(CookingStarted cookingStarted) {
		NotificationLog notificationLog = new NotificationLog();
		notificationLog.setOrderId(cookingStarted.getOrderId());
		notificationLog.setCustomerId(cookingStarted.getCustomerId());
		notificationLog.setMessage("요리시작");
		repository().save(notificationLog);
	}
    public static void katok(OrderCompleted orderCompleted) {
		NotificationLog notificationLog = new NotificationLog();
		notificationLog.setOrderId(orderCompleted.getId());
		notificationLog.setCustomerId(orderCompleted.getCustomerId());
		notificationLog.setMessage("주문완료");
		repository().save(notificationLog);
    }
}
