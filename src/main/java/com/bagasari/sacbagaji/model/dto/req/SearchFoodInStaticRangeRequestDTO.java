package com.bagasari.sacbagaji.model.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SearchFoodInStaticRangeRequestDTO {

    private String name;
    private String latitude;
    private String longitude;
}
