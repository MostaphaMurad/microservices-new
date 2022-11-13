package com.inventoryservice.Services;

import com.inventoryservice.Models.Inventory;
import com.inventoryservice.dto.Request;
import com.inventoryservice.dto.Response;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface InventoryServices {
    List<Response> inStock(List<String> skuCode);

    Inventory addProduct(Request request);
}
