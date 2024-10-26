package com.divyanshuLean.Microservices.order.repository;

import com.divyanshuLean.Microservices.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
