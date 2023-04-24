package com.bagasari.sacbagaji.model.dto.req;

import com.bagasari.sacbagaji.model.dto.FoodDTO;
import com.bagasari.sacbagaji.model.dto.ProductDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FoodRequestDTO {

    private ProductDTO product;
    private FoodDTO food;

}