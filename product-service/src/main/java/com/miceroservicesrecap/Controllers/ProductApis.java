package com.miceroservicesrecap.Controllers;

import com.miceroservicesrecap.Models.Product;
import com.miceroservicesrecap.ProductDto.ProductRequestDto;
import com.miceroservicesrecap.ProductDto.ProductResponseDto;
import com.miceroservicesrecap.Services.Imp.ProductServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductApis {
    private final ProductServiceImp productServiceImp;
    @GetMapping(value = "/all-products",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>>getAllProducts(){
        return new ResponseEntity<>(productServiceImp.getAllProducts(), HttpStatus.OK);
    }
    @PostMapping(value = "/add-product",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDto>addProduct(@RequestBody ProductRequestDto productRequestDto){
        return new ResponseEntity<>(productServiceImp.addProduct(productRequestDto),HttpStatus.CREATED);
    }
}
