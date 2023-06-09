package com.bagasari.sacbagaji.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FoodDTO {

    private Integer count;
    private Integer weight;
    private String latitude;
    private String longitude;

}
