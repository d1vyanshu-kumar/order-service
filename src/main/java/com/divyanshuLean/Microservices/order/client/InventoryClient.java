package com.divyanshuLean.Microservices.order.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

//@FeignClient(value = "inventory",url = "${inventory.url}") // this is the url where the inventory service is running //no used library

public interface InventoryClient {
    // so what kind of the method this http client will call we are defining here
    //getMethod
    //@RequestMapping(method = RequestMethod.GET, value = "")//no used library
    @GetExchange("/api/inventory") // this is the url where the inventory service is running
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
    // similar like spring data jpa repository in which we have to only extend and use their method in the implemented class
    // now go to the order service class and inject the InventoryClient
}
