package com.bagasari.sacbagaji.model.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ProductDTO {

    private Long accountBookId;
    private String name;
    private Integer price;
    private LocalDate purchaseDate;
    private String detail;
    private String country;
    private String city;

}
