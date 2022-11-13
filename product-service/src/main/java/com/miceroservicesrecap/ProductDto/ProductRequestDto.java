package com.miceroservicesrecap.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
@Builder
public class ProductRequestDto {
    String id;
    String description;
    String name;
    Integer price;
}
