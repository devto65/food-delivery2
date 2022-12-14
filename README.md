# 
# Table of contents


# 서비스 시나리오

배달의 민족 커버하기 - https://1sung.tistory.com/106

기능적 요구사항
1. 고객이 메뉴를 주문을 한다.. 
2. 주문을 등록하기 전에 메뉴가 주문가능 유무를 검사한다.
3. 결제를 진행한다.
4. 주문 결제과 완료되면 주문 내역이 상점주에게 전달된다
5. 상점주는 주문을 수락하거나 거절할 수 있다
6. 상점주는 요리 시작 시점과 완료 시점에 시스템에 상태를 입력한다
7. 고객은 아직 요리가 시작되지 않은 주문은 취소할 수 있다
8. 요리가 완료되면 고객의 지역 인근의 라이더들에 의해 배송건 조회가 가능하다. 또한 TopFood에 주문 건수를 증가 시킨다. 
9.  라이더가 해당 요리를 pick 한후, pick했다고 앱을 통해 통보한다.
10. 주문이 취소되면 배달이 취소된다.
11. 고객이 주문상태를 중간중간 조회한다
12. 주문상태가 바뀔 때 마다 카톡으로 알림을 보낸다
13. 고객이 요리를 배달 받으면 배송확인 버튼을 탭하여,  모든 거래가 완료된다.
14. 고객이 배송확인 주문에 대하여 평가한다. TopFood에 평가를 반영된다.


비기능적 요구사항
1. 트랜잭션
    1. 주문할 수 없는 메뉴(Food)의 주문건은 아예 거래가 성립되지 않아야 한다  Sync 호출 
2. 장애격리
    1. 상점관리 기능이 수행되지 않더라도 주문은 365일 24시간 받을 수 있어야 한다  Async (event-driven), Eventual Consistency
    2. 주문시에 상점관리(Food) 기능이 수행되지 않아도 주문을 받을 수 있도록 처리한다. Circuit breaker, fallback
3. 성능
    1. 고객이 자주 상점관리에서 확인할 수 있는 배달상태를 주문시스템(프론트엔드)에서 확인할 수 있어야 한다.  CQRS
    2. 배달상태가 바뀔때마다 카톡 등으로 알림을 줄 수 있어야 한다.  Event driven
    3. 고객이 평점 및 주문이 많은 메뉴를 확인할 수 있어야 한다. CQRS



# Model
![model](https://user-images.githubusercontent.com/118491332/203220858-aeb3f741-0e8e-4dfb-bf38-b98e2eea4b9a.png)

# 체크포인트
1. Saga (Pub / Sub)
2. CQRS
3. Compensation / Correlation
4. Request / Response
5. Circuit Breaker
6. Gateway / Ingress

## Saga (Pub / Sub)
1. 주문의 결제가 완료되면 OrderCompleted 이벤트를 발생 시킨다(front).
 ```java
    public static void updateStatus(OrderPaid orderPaid) {
        repository().findById(orderPaid.getOrderId()).ifPresent(order-> {
            order.setStatus("결제완료");
            repository().save(order);
			OrderCompleted orderCompleted = new OrderCompleted(order);
			orderCompleted.publishAfterCommit();		
        });
    }
```    
2. OrderCompleted 이벤트에 입점 상점에 주문정보들 등록한다(store).
```java
    // 주문완료 이벤트 처리
    public void wheneverOrderCompleted_AddToStoreOrder(
        @Payload OrderCompleted orderCompleted
    ) {
        OrderCompleted event = orderCompleted;
        StoreOrder.addToStoreOrder(event);
    }

    // 점주에 주문 등록
    public static void addToStoreOrder(OrderCompleted orderCompleted) {
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setAddress(orderCompleted.getAddress());
        storeOrder.setCustomerId(orderCompleted.getCustomerId());
        storeOrder.setOrderId(orderCompleted.getId());
        storeOrder.setFoodId(orderCompleted.getFoodId());
        storeOrder.setOptions(orderCompleted.getOptions());
        storeOrder.setStoreId(orderCompleted.getStoreId());
        storeOrder.setStatus("대기중");
        repository().save(storeOrder);
    }
```   

## CQRS
1. 메뉴가 등록 이벤트(FoodAdded) 발생 시에  TopFood에 메뉴를 등록한다.
```java
    // 메뉴가 등록됨
    @StreamListener(KafkaProcessor.INPUT)
    public void whenFoodAdded_then_CREATE_1(@Payload FoodAdded foodAdded) {
        try {

            if (!foodAdded.validate())
                return;
            // view 객체 생성
            TopFood topFood = new TopFood();
            // view 객체에 이벤트의 Value 를 set 함
            topFood.setEvalCount(0);
            topFood.setScore(Float.valueOf(0));
            topFood.setTotalScore(0);
            topFood.setOrderCount(0);
            topFood.setId(foodAdded.getId());
            topFood.setName(foodAdded.getName());
            // view 레파지 토리에 save
            topFoodRepository.save(topFood);

        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
```  
2. 주문에 평가 이벤트(OrderEvalutated) 시에 TopFood의 점수를 갱신한다.
```java
    // 주문(메뉴) 평가
	@StreamListener(KafkaProcessor.INPUT)
	public void whenOrderEvalutated_then_UPDATE_1(@Payload OrderEvalutated orderEvalutated) {
		try {
			if (!orderEvalutated.validate())
				return;
			// view 객체 조회
			Optional<TopFood> topFoodOptional = topFoodRepository.findById(orderEvalutated.getFoodId());

			if (topFoodOptional.isPresent()) {
				TopFood topFood = topFoodOptional.get();
				topFood.setEvalCount(topFood.getEvalCount() + 1);
				topFood.setTotalScore(topFood.getTotalScore() + orderEvalutated.getScore());
				topFood.setScore((float) topFood.getTotalScore() / topFood.getEvalCount());
				// view 레파지 토리에 save
				topFoodRepository.save(topFood);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    // TopFood 조회 API
    @RepositoryRestResource(collectionResourceRel="topFoods", path="topFoods")
    public interface TopFoodRepository extends PagingAndSortingRepository<TopFood, Long> {

    }   
```


## Compensation / Correlation
1. 상점주가 주문을 거부하면 OrderRejected 이벤트를 발생시킨다(key는 주문 ID).
```java
     public void reject() {
        OrderRejected orderRejected = new OrderRejected(this);
        setStatus("거부됨");
        orderRejected.publishAfterCommit();
    }
```

2. OrderRejected 이벤트에 주문 상태를 갱신한다(key는 주문 ID).
```java 
	public static void cancel(OrderRejected orderRejected) {
		repository().findById(orderRejected.getOrderId()).ifPresent(order-> {
			order.setStatus("주문거부됨");
			repository().save(order);
		});
	}
```
## Request / Response
1. 고객 주문 시에 상점에서 메뉴를 조회하여 주문 가능유무를 검사한다.
```java
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
```

## Circuit Breaker
1. 메뉴 조회 서비를 서킷브레이커로 구현하고 실패 시에 주문 가능하도록 반환한다.

설정정보
```yml
feign:
  hystrix:
    enabled: true
hystrix:
  command:
    default:
      execution.isolation.thread.timeoutInMilliseconds: 100
```
부하
```
siege -c50 -t20S  -v  'http://localhost:8082/foods/1'

HTTP/1.1 200     0.26 secs:     248 bytes ==> GET  /foods/1
HTTP/1.1 200     0.26 secs:     248 bytes ==> GET  /foods/1
HTTP/1.1 200     0.26 secs:     248 bytes ==> GET  /foods/1

```

구현
```java
    // 
    @FeignClient(
        name = "store",
        url = "${api.url.store}",
        fallback = FoodServiceImpl.class
    )
    public interface FoodService {
        @RequestMapping(method = RequestMethod.GET, path = "/foods/{id}")
        public Food getFood(@PathVariable("id") Long id);
    }

    // fallback
    @Service
    public class FoodServiceImpl implements FoodService {

        /**
         * Fallback
         */
        public Food getFood(Long id) {
            System.out.println("###  Circuit Breaker  fallback ####");
            Food food = new Food();
            food.setId(id);
            food.setAvailable(true);
            return food;
        }
    }

    // 주문코드
    	@PrePersist
	public void onPrePersist() {
	    // Get request from Food
		long start = System.currentTimeMillis();
       fooddeliverybh.external.Food food =
    		   FrontApplication.applicationContext.getBean(fooddeliverybh.external.FoodService.class)
    		   	.getFood(getFoodId());
		if (!food.getAvailable()) {
			throw new RuntimeException("현재 주문 불가능한 메뉴입니다.");
		}
		System.out.println("주문이 완료됨: elapse:" + (System.currentTimeMillis() - start));
	}
```

주문
```
gitpod /workspace/food-delivery2 (main) $ http :8081/orders foodId=1 storeId=1 customerId=1 options=None address=Seoul
HTTP/1.1 201 
Connection: keep-alive
```

서비로그
```
2022-11-22 07:16:45.361 DEBUG [front,1934bf92ef444512,0df894254ef90a93,true] 51650 --- [hystrix-store-1] o.s.c.s.i.w.c.feign.TracingFeignClient   : Handled receive of RealSpan(1934bf92ef444512/9003b8d23b094de2)
###  Circuit Breaker  fallback ####
주문이 완료됨: elapse:372
```

  
## Gateway / Ingress
1. API Gateway 설정
```yml
    spring:
    profiles: default
    cloud:
        gateway:
        routes:
            - id: front
            uri: http://localhost:8081
            predicates:
                - Path=/orders/**, /payments/**, 
            - id: store
            uri: http://localhost:8082
            predicates:
                - Path=/storeOrders/**, /foods/**, /topFoods/**
            - id: delivery
            uri: http://localhost:8083
            predicates:
                - Path=/deliveries/**, 
            - id: customer
            uri: http://localhost:8084
            predicates:
                - Path=/notificationLogs/**, /orderStatuses/**
            - id: frontend
            uri: http://localhost:8080
            predicates:
                - Path=/**
        globalcors:
            corsConfigurations:
            '[/**]':
                allowedOrigins:
                - "*"
                allowedMethods:
                - "*"
                allowedHeaders:
                - "*"
                allowCredentials: true


```
2. API Gateway 테스트
```
D:\cloud-l2\edu\food-delivery2>http https://8088-devto65-fooddelivery2-8bm9gh4fk8u.ws-us77.gitpod.io/foods
HTTP/1.1 200 OK
Content-Type: application/hal+json
Date: Tue, 22 Nov 2022 07:42:47 GMT
Transfer-Encoding: chunked
Vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers

{
    "_embedded": {
        "foods": [
            {
                "_links": {
                    "food": {
                        "href": "http://localhost:8082/foods/1"
                    },
                    "self": {
                        "href": "http://localhost:8082/foods/1"
                    }
                },
                "available": true,
                "count": 0,
                "name": "Pizza",
                "score": 0,
                "storeId": 1
            }
        ]
    },
 ...
}
```


