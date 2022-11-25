package com.orderservice;

import com.orderservice.dto.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableEurekaClient

public class OrderServiceApplication {
/*    private static WebClient.Builder webClientBuilder=null;

    public OrderServiceApplication(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
   /*     int i=1;
        IntStream.range(i,10).parallel().forEach(t->{
            List<String>skuCodes=new ArrayList<>();
            skuCodes.add("iphone 8");
            skuCodes.add("iphone 13");
            Response[]inventoryResponse= webClientBuilder.build().get().uri("http://inventory-service/api/v1/inventory/in-stock", uriBuilder ->
                            uriBuilder.queryParam("skuCode",skuCodes).build())
                    .retrieve().bodyToMono(Response[].class).block();
        });*/
    }

}















