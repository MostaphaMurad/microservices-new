package com.orderservice.Controller;

import com.orderservice.Models.Order;
import com.orderservice.Services.Imp.OrderServicesImp;
import com.orderservice.dto.OrderRequest;
import com.orderservice.dto.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderApis {
    private final OrderServicesImp orderServicesImp;
    @PostMapping(value = "add-order",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order>addOrder(@RequestBody OrderRequest orderRequest){
        return new ResponseEntity<>(orderServicesImp.addOrder(orderRequest), HttpStatus.CREATED);
    }
}
