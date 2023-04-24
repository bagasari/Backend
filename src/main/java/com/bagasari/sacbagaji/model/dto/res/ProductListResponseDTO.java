package com.bagasari.sacbagaji.model.dto.res;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductListResponseDTO {

    private List<ProductDTO> productList;
}
