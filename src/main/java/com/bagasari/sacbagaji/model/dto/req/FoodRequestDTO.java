package com.bagasari.sacbagaji.model.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FoodRequestDTO {

    private ProductDTO product;
    private FoodDTO food;

}