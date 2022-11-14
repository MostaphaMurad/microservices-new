package com.inventoryservice.Services.Imp;

import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repository.InventoryRepository;
import com.inventoryservice.Services.InventoryServices;
import com.inventoryservice.dto.Request;
import com.inventoryservice.dto.Response;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryServiceImp  implements InventoryServices {
    private final InventoryRepository inventoryRepository;
    @Override
    @Transactional(readOnly = true)
    @SneakyThrows //not used in production
    public List<Response> inStock(List<String> skuCode) {
        log.info("wait started");
        Thread.sleep(20000);
        log.info("wait ended");
        try {
            List<Response> inventory = inventoryRepository.findBySkuCodeIn(skuCode).stream()
                    .map(inven ->
                            Response.builder()
                                    .skuCode(inven.getSkuCode())
                                    .quantity(inven.getQuantity())
                                    .isInStock(inven.getQuantity() > 0)
                                    .build()
                    ).collect(Collectors.toList());
            return inventory;
        }catch (Exception e){
            throw new IllegalArgumentException("not found product in stock ",e);
        }
    }

    @Override
    public Inventory addProduct(Request request) {
        Inventory inventory=Inventory.builder()
                .quantity(request.getQuantity())
                .skuCode(request.getSkuCode()).build();
        try{
            Inventory saved=inventoryRepository.save(inventory);
            return saved;
        }
        catch (Exception e){
            throw new IllegalArgumentException("cant add product in inventory ",e);
        }
    }
}
