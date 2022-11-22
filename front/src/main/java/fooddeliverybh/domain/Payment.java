package fooddeliverybh.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.Table;

import fooddeliverybh.FrontApplication;
import lombok.Data;

@Entity
@Table(name = "Payment_table")
@Data

public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private Long orderId;

	private String status;

	private Long customerId;

	@PostPersist
	public void onPostPersist() {

		OrderPaid orderPaid = new OrderPaid(this);
		orderPaid.setStatus(getStatus());
		orderPaid.publishAfterCommit();

	}

	public static PaymentRepository repository() {
		PaymentRepository paymentRepository = FrontApplication.applicationContext.getBean(PaymentRepository.class);
		return paymentRepository;
	}

	public static void cancelPayment(OrderRejected orderRejected) {
		repository().findById(orderRejected.getOrderId()).ifPresent(payment -> {
			payment.setStatus("결제 취소됨");
		});
	}

	public static void cancelPayment(OrderCanceled orderCanceled) {
		repository().findById(orderCanceled.getId()).ifPresent(payment -> {
			payment.setStatus("결제 취소됨(User)");
			// or
			// repository().delete(payment);
		});
	}

	public static void pay(OrderPlaced orderPlaced) {
		Payment payment = new Payment();
		payment.setOrderId(orderPlaced.getId());
		payment.setCustomerId(orderPlaced.getCustomerId());
		payment.setStatus("성공");
		repository().save(payment);
	}

}
