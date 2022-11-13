package com.miceroservicesrecap.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class ProductResponseDto {
    String id;
    String description;
    String name;
    Integer price;
}
