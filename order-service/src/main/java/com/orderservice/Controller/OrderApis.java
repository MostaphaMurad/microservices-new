package com.orderservice.Controller;

import com.orderservice.Models.Order;
import com.orderservice.Services.Imp.OrderServicesImp;
import com.orderservice.dto.OrderRequest;
import com.orderservice.dto.OrderResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderApis {
    private final OrderServicesImp orderServicesImp;
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
  //  @Bulkhead(name = "inventory",fallbackMethod = "bulkHeadFallBack")
    @PostMapping(value = "add-order",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Retry(name = "inventory")
    public CompletableFuture<String>addOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(()->orderServicesImp.addOrder(orderRequest));
    }
    public CompletableFuture<String>fallbackMethod(@RequestBody OrderRequest orderRequest,RuntimeException exception){
        return CompletableFuture.supplyAsync(()->"opps try to order later");
    }
  /*  public CompletableFuture<String>bulkHeadFallBack(@RequestBody OrderRequest orderRequest,RuntimeException exception){
        return CompletableFuture.supplyAsync(()->"opps try to order later");
    }*/
}
