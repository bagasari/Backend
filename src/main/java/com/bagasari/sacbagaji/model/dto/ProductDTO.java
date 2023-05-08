package com.bagasari.sacbagaji.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ProductDTO {

    private Long accountBookId;
    private String name;
    private Integer price;
    private LocalDate purchaseDate;
    private String detail;
    private String country;
    private String city;

    public ProductDTO(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
