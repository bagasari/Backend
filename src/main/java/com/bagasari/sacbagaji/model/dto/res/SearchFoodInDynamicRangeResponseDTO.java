package com.bagasari.sacbagaji.model.dto.res;

import com.bagasari.sacbagaji.model.dto.FoodDTO;
import com.bagasari.sacbagaji.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SearchFoodInDynamicRangeResponseDTO {

    private ProductDTO product;
    private FoodDTO food;

}
