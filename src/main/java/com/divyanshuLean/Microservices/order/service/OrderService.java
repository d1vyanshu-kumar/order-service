package com.divyanshuLean.Microservices.order.service;

import com.divyanshuLean.Microservices.order.dto.OrderRequest;
import com.divyanshuLean.Microservices.order.model.Order;
import com.divyanshuLean.Microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // this method is taking a type parameter of  type Order Request
    public void placeOrder(OrderRequest orderRequest) {
        // map order request to order object
        // first create the order object
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString()); // create a unique order number
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        // save order to order repository
        orderRepository.save(order);
    }
}
