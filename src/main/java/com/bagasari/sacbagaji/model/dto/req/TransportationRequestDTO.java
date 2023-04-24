package com.bagasari.sacbagaji.model.dto.req;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.model.dto.TransportationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TransportationRequestDTO {

    private ProductDTO product;
    private TransportationDTO transportation;

}
