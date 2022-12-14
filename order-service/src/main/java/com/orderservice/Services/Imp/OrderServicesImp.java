package com.orderservice.Services.Imp;

import com.orderservice.Models.Order;
import com.orderservice.Models.OrderLineItems;
import com.orderservice.Repository.OrderRepository;
import com.orderservice.Services.OrderServices;
import com.orderservice.dto.OrderLineItemsDTO;
import com.orderservice.dto.OrderRequest;
import com.orderservice.dto.OrderResponse;
import com.orderservice.dto.Response;
import com.orderservice.event.OrderPlacedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class OrderServicesImp implements OrderServices {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private final KafkaTemplate<String, OrderPlacedEvent>kafkaTemplate;
    @Override
    public String addOrder(OrderRequest orderRequest) {
        Order order=new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            List<OrderLineItems>orderLineItemsList=orderRequest.getOrderLineItemsDTOS()
                    .stream().map(orderListItemDto->mapToDto(orderListItemDto))
                    .collect(Collectors.toList());
            order.setOrderLineItemsList(orderLineItemsList);
            List<String>skuCodes=order.getOrderLineItemsList().stream()
                            .map(orderLineItems -> orderLineItems.getSkuCode()).collect(Collectors.toList());
        Span inventoryServiceLookUp = tracer.nextSpan().name("inventoryServiceLookUp");
        try(Tracer.SpanInScope spanInScope=tracer.withSpan(inventoryServiceLookUp.start())){
            Response []inventoryResponse= webClientBuilder.build().get().uri("http://inventory-service/api/v1/inventory/in-stock", uriBuilder ->
                            uriBuilder.queryParam("skuCode",skuCodes).build())
                    .retrieve().bodyToMono(Response[].class).block();
            boolean allProductsInStock=Arrays.stream(inventoryResponse).allMatch(Response::getIsInStock);
            if(allProductsInStock){
                try{
                    orderRepository.save(order);
                    kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
                    log.info("order added with id {} ",order.getId());
                    return "order Placed Successfully";
                }
                catch (Exception e){
                    throw new RuntimeException("error adding order",e);
                }
            }
            else{
                return "Failed to order";
            }
        }finally {
            inventoryServiceLookUp.end();
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
        return orderLineItems;
    }
}
