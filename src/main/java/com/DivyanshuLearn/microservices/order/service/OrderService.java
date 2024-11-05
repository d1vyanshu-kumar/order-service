package com.DivyanshuLearn.microservices.order.service;

import com.DivyanshuLearn.microservices.order.client.InventoryClient;
import com.DivyanshuLearn.microservices.order.dto.OrderRequest;
import com.DivyanshuLearn.microservices.order.event.OrderPlacedEvent;
import com.DivyanshuLearn.microservices.order.model.Order;
import com.DivyanshuLearn.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private  final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    // this method is taking a type parameter of  type Order Request
    public void placeOrder(OrderRequest orderRequest) {

//        if (orderRequest.userDetails() == null || orderRequest.userDetails().email() == null
//                || orderRequest.userDetails().firstName() == null || orderRequest.userDetails().lastName() == null) {
//            throw new IllegalArgumentException("User details are incomplete");
//        }

        //1. mockito what it do is that it will mock out the interaction to this method so instead of directly calling this isInStock method from the inventory client it will just provide  a mock response and then we can we can use this mock response to run our test.
        //2. wiremock(A better method)is a library that will help us to mock out the http request and response so that we can test our http client
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (isProductInStock){
            // map order request to order object
            // first create the order object
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString()); // create a unique order number
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            // save order to order repository
            orderRepository.save(order);
            // send the message to kafka topic
            //order number and email

            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(orderRequest.userDetails().email());
            orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
            orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());
            // send the message to kafka topic by using kafka template
            log.info("Start - Sending OrderPlacedEvent {} to Kafka topic order-placed",orderPlacedEvent);
            kafkaTemplate.send("order-placed",orderPlacedEvent);
            log.info("End - Sending OrderPlacedEvent {} to Kafka topic order-placed",orderPlacedEvent);

        }else {
            throw new RuntimeException("Product with the skewCode"+orderRequest.skuCode()+" is not in stock");
        }
        // map order request to order object
        // first create the order object

    }

}
