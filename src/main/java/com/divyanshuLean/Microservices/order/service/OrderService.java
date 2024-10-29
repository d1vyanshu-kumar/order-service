package com.divyanshuLean.Microservices.order.service;

import com.divyanshuLean.Microservices.order.client.InventoryClient;
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
    private final InventoryClient inventoryClient;

    // this method is taking a type parameter of  type Order Request
    public void placeOrder(OrderRequest orderRequest) {
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
        }else {
            throw new RuntimeException("Product with the skewCode"+orderRequest.skuCode()+" is not in stock");
        }
        // map order request to order object
        // first create the order object

    }

}
