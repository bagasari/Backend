package com.bagasari.sacbagaji.model.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FoodDTO {

    private Integer count;
    private Integer weight;
    private String latitude;
    private String longitude;

}