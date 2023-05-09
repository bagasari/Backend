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
    private String productType;

    // 현재 작성중인 가계부를 위한 생성자
    public ProductDTO(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    // 가계부 지출내역 조회를 위한 생성자
    public ProductDTO(String name, Integer price, String city, String productType) {
        this.name = name;
        this.price = price;
        this.city = city;
        this.productType = productType;
    }
}
