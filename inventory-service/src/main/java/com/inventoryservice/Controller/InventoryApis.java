package com.inventoryservice.Controller;

import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Services.Imp.InventoryServiceImp;
import com.inventoryservice.dto.Request;
import com.inventoryservice.dto.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
@AllArgsConstructor
public class InventoryApis {
    private final InventoryServiceImp inventoryServiceImp;
    @PostMapping("/add-product")
    public ResponseEntity<Inventory>addToInventory(@RequestBody Request request){
        return new ResponseEntity<>(inventoryServiceImp.addProduct(request),HttpStatus.CREATED);
    }
    @GetMapping("/in-stock")
    public ResponseEntity<List<Response>>inStock(@RequestParam List<String> skuCode){
        return new ResponseEntity<>(inventoryServiceImp.inStock(skuCode), HttpStatus.OK);
    }
}
