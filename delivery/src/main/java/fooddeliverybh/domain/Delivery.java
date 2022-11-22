package fooddeliverybh.domain;

import fooddeliverybh.domain.Delivered;
import fooddeliverybh.domain.DeliveryStarted;
import fooddeliverybh.DeliveryApplication;
import javax.persistence.*;
import javax.servlet.http.Cookie;

import java.util.List;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "Delivery_table")
@Data

public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private Long orderId;

	private String address;

	private Long customerId;

	private Long foodId;

	private Long storeId;

	private String status;

	@PostPersist
	public void onPostPersist() {


	}

	public static DeliveryRepository repository() {
		DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext
				.getBean(DeliveryRepository.class);
		return deliveryRepository;
	}

	public void confirm() {
		setStatus("배달완료");
		Delivered delivered = new Delivered(this);
		delivered.publishAfterCommit();		
	}
	
	public void accept() {
		setStatus("배달중");
		DeliveryStarted deliveryStarted = new DeliveryStarted(this);
		deliveryStarted.publishAfterCommit();
	}
	
	public static void addDevery(Cooked cooked) {
		Delivery delivery = new Delivery();
		delivery.setAddress(cooked.getAddress());
		delivery.setCustomerId(cooked.getCustomerId());
		delivery.setFoodId(cooked.getId());
		delivery.setOrderId(cooked.getOrderId());
		delivery.setStoreId(cooked.getStoreId());
		delivery.setStatus("대기중");
		repository().save(delivery);
	}
}
