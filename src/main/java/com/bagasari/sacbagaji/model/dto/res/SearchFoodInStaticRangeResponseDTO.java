package com.bagasari.sacbagaji.model.dto.res;

import com.bagasari.sacbagaji.model.dto.FoodDTO;
import com.bagasari.sacbagaji.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchFoodInStaticRangeResponseDTO {

    private ProductDTO product;
    private FoodDTO food;

}
