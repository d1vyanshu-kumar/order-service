package com.divyanshuLean.Microservices.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "inventory",url = "${inventory.url}") // this is the url where the inventory service is running
public interface InventoryClient {
    // so what kind of the method this http client will call we are defining here
    //getMethod
    @RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
    // similar like spring data jpa repository in which we have to only extend and use their method in the implemented class
    // now go to the order service class and inject the InventoryClient
}
