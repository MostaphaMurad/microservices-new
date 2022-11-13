package com.orderservice.Services;

import com.orderservice.Models.Order;
import com.orderservice.dto.OrderRequest;
import com.orderservice.dto.OrderResponse;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface OrderServices {
    Order addOrder(OrderRequest orderRequest);
}
