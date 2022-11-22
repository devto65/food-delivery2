# 
# Table of contents


# 서비스 시나리오

배달의 민족 커버하기 - https://1sung.tistory.com/106

기능적 요구사항
1. 고객이 메뉴를 주문을 한다.. 
2. 주문을 등록하기 전에 메뉴가 주문가능 유무를 검사한다.
3. 결제를 진행한다.
4. 주문 결제과 완료되면 주문 내역이 입점상점주인에게 전달된다
5. 상점주는 주문을 수락하거나 거절할 수 있다
6. 상점주는 요리 시작 시점과 완료 시점에 시스템에 상태를 입력한다
7. 고객은 아직 요리가 시작되지 않은 주문은 취소할 수 있다
8. 요리가 완료되면 고객의 지역 인근의 라이더들에 의해 배송건 조회가 가능하다. 또한 TOpMenu에 주건 건수를 증가 시킨다. 
9.  라이더가 해당 요리를 pick 한후, pick했다고 앱을 통해 통보한다.
10. 주문이 취소되면 배달이 취소된다.
11. 고객이 주문상태를 중간중간 조회한다
12. 주문상태가 바뀔 때 마다 카톡으로 알림을 보낸다
13. 고객이 요리를 배달 받으면 배송확인 버튼을 탭하여,  모든 거래가 완료된다.
14. 고객이 배송확인 주문에 대하여 평가한다. TopMenu에 평가가 반영된다.
15. 평가된 주문에 상품에 대하여 별점을 갱신한다. 


비기능적 요구사항
1. 트랜잭션
    1. 주문할 수 없는 음식(Food)의 주문건은 아예 거래가 성립되지 않아야 한다  Sync 호출 
2. 장애격리
    1. 상점관리 기능이 수행되지 않더라도 주문은 365일 24시간 받을 수 있어야 한다  Async (event-driven), Eventual Consistency
    2. 주문시에 상점관리(Food) 기능이 수쟁되지 않아도 주문을 받을 수 있도록 처리한다. Circuit breaker, fallback
3. 성능
    1. 고객이 자주 상점관리에서 확인할 수 있는 배달상태를 주문시스템(프론트엔드)에서 확인할 수 있어야 한다.  CQRS
    2. 배달상태가 바뀔때마다 카톡 등으로 알림을 줄 수 있어야 한다.  Event driven
    3. 고객이 평점 및 주문이 많은 메뉴를 확인할 수 있어야 한다. CQRS



# Model


# 체크포인트
## Saga (Pub / Sub)
1. 주문의 결제가 완료되면 OrderCompleted 이벤트를 발생 시킨다.
1. OrderCompleted 이벤트에 입점 상점에 주문정보들 등록한다.
1. 점주가 주문을 거부하면 OrderRejected 이벤트를 발생시킨다.
1. OrderRejected 이벤트에 주문 상태를 
 
 ```java
 	public static void updateStatus(OrderPaid orderPaid) {
		repository().findById(orderPaid.getOrderId()).ifPresent(order-> {
			order.setStatus("결제완료");
			repository().save(order);
			
		});
	}
```    


## CQRS

## Compensation / Correlation

## Request / Response

## Circuit Breaker

## Gateway / Ingress



## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd kafka
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- front
- store
- delivery
- customer


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- front
```
 http :8088/orders id="id" foodId="foodId" customerId="customerId" options="options" address="address" status="status" score="score" paymentId="paymentId" storeId="storeId" 
 http :8088/payments id="id" orderId="orderId" status="status" customerId="customerId" 
```
- store
```
 http :8088/storeOrders id="id" foodId="foodId" orderId="orderId" status="status" paymentId="paymentId" address="address" options="options" storeId="storeId" customerId="customerId" 
 http :8088/foods id="id" name="name" count="count" score="score" storeId="storeId" 
```
- delivery
```
 http :8088/deliveries id="id" orderId="orderId" address="address" customerId="customerId" foodId="foodId" storeId="storeId" status="status" 
```
- customer
```
 http :8088/notificationLogs id="id" customerId="customerId" message="message" 
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```

