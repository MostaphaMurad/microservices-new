package com.miceroservicesrecap.Services.Imp;

import com.miceroservicesrecap.Models.Product;
import com.miceroservicesrecap.ProductDto.ProductRequestDto;
import com.miceroservicesrecap.ProductDto.ProductResponseDto;
import com.miceroservicesrecap.Repository.ProductRepository;
import com.miceroservicesrecap.Services.ProductServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductServiceImp implements ProductServices {
    private final ProductRepository productRepository;
    @Override
    @Cacheable
    public List<Product> getAllProducts() {
       return productRepository.findAll();
    }

    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {

        ProductResponseDto responseDto;
        try{
            responseDto=new ProductResponseDto();
            Product product= Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name(productRequestDto.getName())
                    .description(productRequestDto.getDescription())
                    .price(productRequestDto.getPrice())
                    .build();
            product=productRepository.save(product);
            if(product.getId()!=null){
                responseDto.setName(product.getName());
                responseDto.setDescription(product.getDescription());
                responseDto.setPrice(product.getPrice());
                responseDto.setId(product.getId());
                log.info("Product saved with id {} ",product.getId());
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to save product ",e);
        }
        return responseDto;
    }
}
