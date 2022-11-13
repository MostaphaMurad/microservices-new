package com.miceroservicesrecap.Services;

import com.miceroservicesrecap.Models.Product;
import com.miceroservicesrecap.ProductDto.ProductRequestDto;
import com.miceroservicesrecap.ProductDto.ProductResponseDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ProductServices {
    List<Product>getAllProducts();

    ProductResponseDto addProduct(ProductRequestDto productRequestDto);
}
